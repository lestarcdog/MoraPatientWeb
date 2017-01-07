package hu.mora.tool.configuration;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Map;

public interface Config {

    /**
     * Mora patient this app home directory
     *
     * @return as java path
     */
    Path getMoraPatientHomeDirPath();

    void setMoraPatientHomeDir(String moraPatientHomeDir);

    /**
     * Nova db home directory
     *
     * @return as path
     */
    Path getNovaDbHomeDirPath();

    void setNovaDbHomeDir(String novaDbHomeDir);

    /**
     * Last time this tool was upgraded
     *
     * @return last update time
     */
    LocalDateTime getToolLastUpdateTime();

    void setToolLastUpdateTime(LocalDateTime toolLastUpdateTime);

    /**
     * Tools version
     *
     * @return tool version
     */
    Integer getToolVersion();

    void setToolVersion(Integer toolVersion);

    /**
     * The web application last update time.
     *
     * @return last update time
     */
    LocalDateTime getWebLastUpdateTime();

    void setWebLastUpdateTime(LocalDateTime webLastUpdateTime);

    /**
     * Web components tool
     *
     * @return web version
     */
    Integer getWebVersion();

    void setWebVersion(Integer webVersion);

    Map<String, String> getExtraProperties();

    void setExtraProperties(Map<String, String> extraProperties);

    String getWildflyManagementAddress();

    void setWildflyManagementAddress(String wildflyManagementAddress);
}
