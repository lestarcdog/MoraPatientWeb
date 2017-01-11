package hu.mora.web.service.novadb;

import hu.mora.web.dao.ConfigDao;
import hu.mora.web.model.novadb.NovaPatient;
import hu.mora.web.model.novadb.beap.Beap;
import hu.mora.web.model.novadb.beap.Benutzer;
import hu.mora.web.model.novadb.beap.Person;
import hu.mora.web.service.config.Configs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Singleton
public class NovaBeapService {

    private static final Logger LOG = LoggerFactory.getLogger(NovaBeapService.class);

    @Inject
    ConfigDao configDao;

    private String beapFailReason;
    private Path beapXml;
    Beap beap;

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

    private void reloadBeapXml() {
        try {
            beap = (Beap) unmarshaller.unmarshal(beapXml.toFile());
        } catch (JAXBException e) {
            beap = null;
            LOG.error(e.getMessage(), e);
            beapFailReason = "A nova db fájl nem nyitható meg: " + beapXml.toString();
        }
    }


    public List<NovaPatient> findPatientByName(String name) {

        return null;
    }


}
