package hu.mora.web.service.novadb;

import hu.mora.web.dao.ConfigDao;
import hu.mora.web.model.novadb.beap.Beap;
import hu.mora.web.model.novadb.beap.Benutzer;
import hu.mora.web.model.novadb.beap.Ergebnis;
import hu.mora.web.model.novadb.beap.Person;
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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@Singleton
public class NovaBeapService {

    private static final Logger LOG = LoggerFactory.getLogger(NovaBeapService.class);
    private static final Comparator<Benutzer> BENUTZER_COMPARATOR = comparing(b -> b.getPerson().getUserId());

    @Inject
    ConfigDao configDao;

    private String beapFailReason;
    private Path beapXml;
    private Beap beap;

    // cached elements
    private List<Person> cachedPersonsSortedByName;

    private JAXBContext jaxbContext;
    private Unmarshaller unmarshaller;

    @PostConstruct
    public void setUp() {
        String novaHome = configDao.getValue(Configs.NOVA_DB_HOME);
        beapXml = Paths.get(novaHome).resolve("BEAP.XML");

        try {
            jaxbContext = JAXBContext.newInstance(Beap.class, Benutzer.class, Person.class);
            unmarshaller = jaxbContext.createUnmarshaller();

            reloadBeapXml();
        } catch (JAXBException e) {
            LOG.error(e.getMessage(), e);
            beapFailReason = "A nova db fájl nem nyitható meg: " + beapXml.toString();
        }

    }

    private void populateCachedPersons() {
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

    public List<Person> allPatients() {
        if (cachedPersonsSortedByName == null) populateCachedPersons();
        return cachedPersonsSortedByName;
    }

    public List<Ergebnis> resultsOfPatient(@NotNull Integer userId) {
        Benutzer key = new Benutzer();
        key.setPerson(new Person(userId));

        int i = Collections.binarySearch(beap.getBenutzer(), key, BENUTZER_COMPARATOR);
        return i < 0 ? Collections.emptyList() : beap.getBenutzer().get(i).getErgebnisse();
    }


}
