package hu.mora.tool;

import javafx.stage.Stage;

import java.util.Objects;

public class MainStageHolder {
    private static Stage MAIN_STAGE;

    static void setMainStage(Stage stage) {
        MAIN_STAGE = stage;
    }

    public static Stage getMainStage() {
        return Objects.requireNonNull(MAIN_STAGE);
    }
}
