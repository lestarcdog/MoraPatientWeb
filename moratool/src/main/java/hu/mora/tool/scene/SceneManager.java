package hu.mora.tool.scene;

import com.google.common.base.Joiner;
import hu.mora.tool.scene.springloader.SpringFxmlLoader;
import hu.mora.tool.section.FooterController;
import hu.mora.tool.section.content.ControlAreaController;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.requireNonNull;

@Service
public class SceneManager {

    private static final Logger LOG = LoggerFactory.getLogger(SceneManager.class);

    private Stage mainStage;

    @Autowired
    private SpringFxmlLoader fxmlLoader;

    @Autowired
    private ControlAreaController controlAreaController;

    @Autowired
    private FooterController footerController;


    public void setupStage(Stage main) {
        if (mainStage == null) {
            mainStage = requireNonNull(main, "main");

            //set up main scene
            Parent mainSceneNode = fxmlLoader.load(SceneManager.class.getResourceAsStream("/fxml/main.fxml"));
            main.setScene(new Scene(mainSceneNode));
            main.getIcons().add(new Image("graphics/mora_icon.jpg"));

            List<String> errorMessages = preFlightCheck();
            if (errorMessages.isEmpty()) {
                showScene(AppScene.WELCOME);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR,
                        Joiner.on("\n").join(errorMessages),
                        new ButtonType("Kilépés", ButtonBar.ButtonData.CANCEL_CLOSE));
                alert.setHeaderText("Végzetes hiba történt!");
                alert.showAndWait();
                Platform.exit();
            }
            main.show();

        } else {
            LOG.warn("Main stage is already set");
        }
    }

    private List<String> preFlightCheck() {
        return Collections.emptyList();
    }

    public Stage getMainStage() {
        return mainStage;
    }

    public void showScene(AppScene appScene) {
        requireNonNull(appScene);
        requireNonNull(mainStage, "main stage is not set");

        LOG.debug("Loading scene {}", appScene.getSceneTitle());
        InputStream sceneFxml = SceneManager.class.getResourceAsStream(appScene.getFxmlPath());
        Parent parent = fxmlLoader.load(sceneFxml);

        mainStage.setTitle(appScene.getSceneTitle());
        controlAreaController.showMainContent(parent);

        if (!mainStage.isShowing()) {
            mainStage.show();
        }

    }

    public void showStatusMessage(String message) {
        footerController.setStatus(message);
    }


    public void showError(String errorMessage, Exception e) {
        LOG.error(e.getMessage(), e);
        showError(errorMessage);

    }

    public void showError(String errorMessage) {
        footerController.setStatus("");
        Alert alert = new Alert(Alert.AlertType.WARNING, errorMessage, ButtonType.OK);
        alert.setHeaderText("Hiba történt");
        alert.showAndWait();
    }

    public void showSuccess(String successMessage) {
        footerController.setSuccessMessage(successMessage);
    }
}
