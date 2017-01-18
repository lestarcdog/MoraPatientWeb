package hu.mora.tool.configuration;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;

@Repository
public class ConfigurationRepository {

    private static final Logger LOG = LoggerFactory.getLogger(ConfigurationRepository.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String CONFIG_FILE_PATH = "/config.json";
    private AutoSaveConfig autoSaveConfig;


    static {
        MAPPER.findAndRegisterModules();
        MAPPER.setDefaultPrettyPrinter(new DefaultPrettyPrinter());
    }

    private URL configFileResource;

    @PostConstruct
    public void init() throws IOException {
        try {
            configFileResource = ConfigurationRepository.class.getResource(CONFIG_FILE_PATH);
            InputStream configStream = configFileResource.openStream();
            LOG.info("Reading autoSaveConfig file {}", CONFIG_FILE_PATH);
            autoSaveConfig = MAPPER.readValue(configStream, AutoSaveConfig.class);
            autoSaveConfig.setSaveConfig(this::save);
            LOG.info("AutoSaveConfig loaded {}", autoSaveConfig);
            configStream.close();
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
            LOG.info("Saving config file to {}. Content {}", CONFIG_FILE_PATH, autoSaveConfig);
            try (FileOutputStream out = new FileOutputStream(configFileResource.toURI().getPath())) {
                MAPPER.writerWithDefaultPrettyPrinter().writeValue(out, autoSaveConfig);
            }
        } catch (URISyntaxException | IOException e) {
            LOG.error(e.getMessage(), e);
            Throwables.propagate(e);
        }
    }
}
