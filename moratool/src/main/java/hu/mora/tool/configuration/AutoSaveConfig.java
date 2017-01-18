package hu.mora.tool.configuration;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * MoraTool configuration object representation. Json deserialization should use field direct not the setter.
 */
@JsonAutoDetect(setterVisibility = JsonAutoDetect.Visibility.ANY)
public class AutoSaveConfig implements Config {

    private String moraPatientHomeDir;

    private String novaDbHomeDir;

    private LocalDateTime toolLastUpdateTime;

    private Integer toolVersion;

    private LocalDateTime webLastUpdateTime;

    private Integer webVersion;

    private String wildflyManagementAddress;

    private Map<String, String> extraProperties;


    /**
     * Runnable to be called when a property changes.
     * Cheap man's callback handler on every setter method.
     * Default is noop save action when jackson is loading the object.
     */
    private Runnable saveConfig = () -> {
    };

    /**
     * Must be called as soon as created;
     *
     * @param saveConfig save action
     */
    void setSaveConfig(Runnable saveConfig) {
        this.saveConfig = saveConfig;
    }

    @Override
    public Map<String, String> getExtraProperties() {
        return extraProperties;
    }

    @Override
    public void setExtraProperties(Map<String, String> extraProperties) {
        this.extraProperties = extraProperties;
        saveConfig.run();
    }

    @Override
    public void setMoraPatientHomeDir(String moraPatientHomeDir) {
        this.moraPatientHomeDir = moraPatientHomeDir;
        saveConfig.run();
    }

    @Override
    public LocalDateTime getToolLastUpdateTime() {
        return toolLastUpdateTime;
    }

    @Override
    public void setToolLastUpdateTime(LocalDateTime toolLastUpdateTime) {
        this.toolLastUpdateTime = toolLastUpdateTime;
        saveConfig.run();
    }

    @Override
    public Integer getToolVersion() {
        return toolVersion;
    }

    @Override
    public void setToolVersion(Integer toolVersion) {
        this.toolVersion = toolVersion;
        saveConfig.run();
    }

    @Override
    public LocalDateTime getWebLastUpdateTime() {
        return webLastUpdateTime;
    }

    @Override
    public void setWebLastUpdateTime(LocalDateTime webLastUpdateTime) {
        this.webLastUpdateTime = webLastUpdateTime;
        saveConfig.run();
    }

    @Override
    public Integer getWebVersion() {
        return webVersion;
    }

    @Override
    public void setWebVersion(Integer webVersion) {
        this.webVersion = webVersion;
        saveConfig.run();
    }

    public String getMoraPatientHomeDir() {
        return moraPatientHomeDir;
    }

    public String getNovaDbHomeDir() {
        return novaDbHomeDir;
    }

    @JsonIgnore
    @Override
    public Path getMoraPatientHomeDirPath() {
        return Paths.get(this.moraPatientHomeDir);
    }

    @JsonIgnore
    @Override
    public Path getNovaDbHomeDirPath() {
        return Paths.get(this.novaDbHomeDir);
    }

    @Override
    public void setNovaDbHomeDir(String novaDbHomeDir) {
        this.novaDbHomeDir = novaDbHomeDir;
        saveConfig.run();
    }

    @Override
    public String getWildflyManagementAddress() {
        return wildflyManagementAddress;
    }

    @Override
    public void setWildflyManagementAddress(String wildflyManagementAddress) {
        this.wildflyManagementAddress = wildflyManagementAddress;
    }

    @Override
    public String toString() {
        return "AutoSaveConfig{" +
                "moraPatientHomeDir='" + moraPatientHomeDir + '\'' +
                ", novaDbHomeDir='" + novaDbHomeDir + '\'' +
                ", toolLastUpdateTime=" + toolLastUpdateTime +
                ", toolVersion=" + toolVersion +
                ", webLastUpdateTime=" + webLastUpdateTime +
                ", webVersion=" + webVersion +
                ", extraProperties=" + extraProperties +
                '}';
    }

}
