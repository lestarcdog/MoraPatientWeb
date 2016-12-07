package hu.mora.web.openshift;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.util.Properties;

@Singleton
@Startup
public class DbCreator {

    private static Logger LOG = LoggerFactory.getLogger(DbCreator.class);

    @PostConstruct
    public void init() {
        Flyway f = new Flyway();
        Properties prop = new Properties();
        prop.setProperty("flyway.url", "jdbc:h2:mem:MoraPatientDB;DB_CLOSE_DELAY=-1");
        prop.setProperty("flyway.user", "sa");
        prop.setProperty("flyway.password", "sa");
        prop.setProperty("flyway.locations", "classpath:/sql");
        f.configure(prop);


        try {
            LOG.info("Clean");
            f.clean();
            LOG.info("Migrate");
            f.migrate();
            LOG.info("Success");
        } catch (FlywayException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
