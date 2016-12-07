package hu.mora.tool.configuration;

import com.google.common.base.MoreObjects;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * MoraTool configuration object representation
 */
class Config {

    private String homeDirectory;

    private LocalDateTime toolLastUpdateTime;

    private Integer toolVersion;

    private LocalDateTime webLastUpdateTime;

    private Integer webVersion;

    private Map<String, String> extraProperties;


    public Map<String, String> getExtraProperties() {
        return extraProperties;
    }

    public void setExtraProperties(Map<String, String> extraProperties) {
        this.extraProperties = extraProperties;
    }

    public String getHomeDirectory() {
        return homeDirectory;
    }

    public void setHomeDirectory(String homeDirectory) {
        this.homeDirectory = homeDirectory;
    }

    public LocalDateTime getToolLastUpdateTime() {
        return toolLastUpdateTime;
    }

    public void setToolLastUpdateTime(LocalDateTime toolLastUpdateTime) {
        this.toolLastUpdateTime = toolLastUpdateTime;
    }

    public Integer getToolVersion() {
        return toolVersion;
    }

    public void setToolVersion(Integer toolVersion) {
        this.toolVersion = toolVersion;
    }

    public LocalDateTime getWebLastUpdateTime() {
        return webLastUpdateTime;
    }

    public void setWebLastUpdateTime(LocalDateTime webLastUpdateTime) {
        this.webLastUpdateTime = webLastUpdateTime;
    }

    public Integer getWebVersion() {
        return webVersion;
    }

    public void setWebVersion(Integer webVersion) {
        this.webVersion = webVersion;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("homeDirectory", homeDirectory)
                .add("toolLastUpdateTime", toolLastUpdateTime)
                .add("toolVersion", toolVersion)
                .add("webLastUpdateTime", webLastUpdateTime)
                .add("webVersion", webVersion)
                .add("extraProperties", extraProperties)
                .toString();
    }
}
