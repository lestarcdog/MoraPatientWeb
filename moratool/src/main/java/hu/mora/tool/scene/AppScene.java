package hu.mora.tool.scene;

public enum AppScene {
    ERROR_HOME_DIR("content/invalidHomeDir", "Érvénytelen főkönyvtár"),
    DATABASE("content/database", "Adatbázis műveletek"),
    HOME_DIR_PAGE("content/homedirectory", "Alapértelmezett főkönyvtár"),
    WELCOME("content/welcome", "Welcome"),
    MORA_PATIENT_SERVER("content/server", "MoraPatient szerver");

    private static final String PATH_PREFIX = "/fxml/";
    private static final String PATH_POSTFIX = ".fxml";

    private final String fxmlPath;
    private final String sceneTitle;

    AppScene(String fxmlPath, String sceneTitle) {
        this.fxmlPath = fxmlPath;
        this.sceneTitle = sceneTitle;
    }

    public String getSceneTitle() {
        return sceneTitle;
    }

    public String getFxmlPath() {
        return PATH_PREFIX + fxmlPath + PATH_POSTFIX;
    }
}