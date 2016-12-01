package hu.mora.tool.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.io.File;

@Repository
public class ConfigurationRepository {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final Config config;

    public ConfigurationRepository() {
        //TODO load up config
        config = new Config();
    }

    public String homeDirectory() {
        return "C:/MoraPatient/";
    }

    public void setNewHomeDirectory(File newHomeDirectory) {

    }
}
