package hu.mora.tool.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@Repository
public class ConfigurationRepository {

    private static final Logger LOG = LoggerFactory.getLogger(ConfigurationRepository.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();
    public static final String CONFIG_FILE_PATH = "/config.json";
    private Config config;


    static {
        MAPPER.findAndRegisterModules();
    }

    @PostConstruct
    public void init() {
        InputStream configStream = ConfigurationRepository.class.getResourceAsStream(CONFIG_FILE_PATH);
        try {
            LOG.info("Reading config file {}", CONFIG_FILE_PATH);
            config = MAPPER.readValue(configStream, Config.class);
            LOG.info("Config loaded {}", config);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            Throwables.propagate(e);
        }
    }

    public String homeDirectory() {
        return config.getHomeDirectory();
    }

    public void setNewHomeDirectory(File newHomeDirectory) {
        config.setHomeDirectory(newHomeDirectory.getAbsolutePath());
    }


    private void save() {
        Objects.requireNonNull(config, "Config be created before save");
        try {
            MAPPER.writeValue(new File(CONFIG_FILE_PATH), config);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            Throwables.propagate(e);
        }
    }
}
