package hu.mora.web.service.novadb;

import hu.mora.web.dao.ConfigDao;
import hu.mora.web.exception.MoraException;
import hu.mora.web.model.novadb.beap.Beap;
import hu.mora.web.model.novadb.beap.Benutzer;
import hu.mora.web.model.novadb.beap.Ergebnis;
import hu.mora.web.model.novadb.beap.Person;
import hu.mora.web.model.novadb.nrf.NrfResult;
import hu.mora.web.model.novadb.nrf.NrfRow;
import hu.mora.web.service.config.Configs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@Singleton
public class NovaDbConnector {

    private static final Logger LOG = LoggerFactory.getLogger(NovaDbConnector.class);
    private static final Comparator<Benutzer> BENUTZER_COMPARATOR = comparing(b -> b.getPerson().getUserId());

    private static final String NRF_PATH = "NRF";

    @Inject
    ConfigDao configDao;

    private String beapFailReason;
    private Path beapXml;
    private Beap beap;

    // cached elements
    private List<Person> cachedPersonsSortedByName;

    private JAXBContext jaxbContext;
    private Unmarshaller unmarshaller;

    private String novaHome;

    @PostConstruct
    public void setUp() {
        novaHome = configDao.getValue(Configs.NOVA_DB_HOME);
        beapXml = Paths.get(novaHome).resolve("BEAP.XML");

        try {
            jaxbContext = JAXBContext.newInstance(Beap.class, Benutzer.class, Person.class);
            LOG.info("Unmarshall nova db {}", beapXml.toString());
            unmarshaller = jaxbContext.createUnmarshaller();

            reloadBeapXml();
        } catch (JAXBException e) {
            LOG.error(e.getMessage(), e);
            beapFailReason = "A nova db fájl nem nyitható meg: " + beapXml.toString();
        }

    }

    private void populateCachedPersons() {
        LOG.info("Reload patients data from Nova BEAP.XML");
        cachedPersonsSortedByName = beap.getBenutzer().stream()
                .map(Benutzer::getPerson)
                .sorted(comparing(Person::fullHungarianName))
                .collect(toList());
    }

    private void reloadBeapXml() {
        try {
            beap = (Beap) unmarshaller.unmarshal(beapXml.toFile());

            beap.getBenutzer().sort(BENUTZER_COMPARATOR);
            cachedPersonsSortedByName = null;
        } catch (JAXBException e) {
            beap = null;
            LOG.error(e.getMessage(), e);
            beapFailReason = "A nova db fájl nem nyitható meg: " + beapXml.toString();
        }
    }

    public List<Person> allPatients(boolean refresh) {
        if (cachedPersonsSortedByName == null || refresh) populateCachedPersons();
        return cachedPersonsSortedByName;
    }

    public List<Ergebnis> patientResults(@NotNull Integer userId) {
        Optional<Benutzer> benutzer = findBenutzer(userId);
        return benutzer.orElseThrow(() -> new MoraException("A Nova beteg nem található.")).getErgebnisse();
    }

    private Optional<Benutzer> findBenutzer(@NotNull Integer userId) {
        Benutzer key = new Benutzer();
        key.setPerson(new Person(userId));

        int i = Collections.binarySearch(beap.getBenutzer(), key, BENUTZER_COMPARATOR);
        return i < 0 ? Optional.empty() : Optional.of(beap.getBenutzer().get(i));

    }

    public NrfResult readUserResult(@NotNull Integer userId, @NotNull Integer resultId) {
        String fileName = userId + "_" + resultId + ".NRF";
        Path nrfFile = Paths.get(novaHome).resolve(NRF_PATH).resolve(fileName);
        if (Files.exists(nrfFile)) {
            try {
                LOG.info("Reading NOVA result file {}", nrfFile.toString());
                return readNrfFile(nrfFile);
            } catch (IOException e) {
                throw new MoraException("A beteghez tartozó mérési eredmény nem nyitható meg.", e);
            } catch (BufferUnderflowException e) {
                throw new MoraException("A mérési eredmény fájl olvasása során hiba lépett fel.");
            }
        } else {
            LOG.warn("File not found {}", nrfFile.toAbsolutePath());
            throw new MoraException("A beteghez tartozó mérési eredmény nem létezik.");
        }
    }

    private NrfResult readNrfFile(Path nrfFile) throws IOException {
        NrfResult result = new NrfResult();
        ByteBuffer buffer = ByteBuffer.allocate(24);
        buffer.order(ByteOrder.LITTLE_ENDIAN);

        try (FileChannel channel = FileChannel.open(nrfFile)) {
            // skip the useless header
            channel.position(16);

            // start reading the data
            // results are 24 bytes
            while (channel.read(buffer) > 0) {
                buffer.flip();
                result.add(parseNrfRow(nrfFile, buffer));
                buffer.clear();
            }

            //should have one last result
            result.add(parseNrfRow(nrfFile, buffer));
        }
        return result;
    }

    private NrfRow parseNrfRow(Path nrfFile, ByteBuffer buffer) {
        NrfRow row = new NrfRow();
        // 1 - 4
        row.setSubstanceId(buffer.getInt());
        // 5
        row.setJn(buffer.get());
        // 6
        row.setModus(buffer.get());
        // 7 - 8
        row.setPotenz(buffer.getShort());
        // 9
        row.setKanal(buffer.get());
        // 10
        row.setAusgesetzt(buffer.get());
        // 11 - 12
        row.setWert(buffer.getShort());
        // 13
        row.setWabe(buffer.get());
        // 14 - 17
        row.setVerstearkung(buffer.getInt());

        return row;
    }

    private void checkBufferLimit(Path nrfFile, Buffer b, int expected) {
        if (b.limit() < expected) {
            throw new MoraException("Érvénytelen mérési eredmény fájl. Nem olvasható " + nrfFile.toString());
        }
    }
}
