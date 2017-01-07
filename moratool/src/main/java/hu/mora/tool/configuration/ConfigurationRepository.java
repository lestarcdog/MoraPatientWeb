package hu.mora.tool.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
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
    private AutoSaveConfig autoSaveConfig;


    static {
        MAPPER.findAndRegisterModules();
    }

    @PostConstruct
    public void init() {
        InputStream configStream = ConfigurationRepository.class.getResourceAsStream(CONFIG_FILE_PATH);
        try {
            LOG.info("Reading autoSaveConfig file {}", CONFIG_FILE_PATH);
            autoSaveConfig = MAPPER.readValue(configStream, AutoSaveConfig.class);
            autoSaveConfig.setSaveConfig(this::save);
            LOG.info("AutoSaveConfig loaded {}", autoSaveConfig);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            Throwables.propagate(e);
        }
    }

    @Bean
    @Lazy
    public Config config() {
        return autoSaveConfig;
    }


    private void save() {
        Objects.requireNonNull(autoSaveConfig, "AutoSaveConfig be created before save");
        try {
            MAPPER.writeValue(new File(CONFIG_FILE_PATH), autoSaveConfig);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            Throwables.propagate(e);
        }
    }
}
