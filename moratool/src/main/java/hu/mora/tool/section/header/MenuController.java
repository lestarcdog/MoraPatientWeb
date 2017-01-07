package hu.mora.tool.section.header;

import hu.mora.tool.scene.AppScene;
import hu.mora.tool.scene.SceneManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class MenuController {

    @Autowired
    SceneManager sceneManager;


    public void onExit(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Biztos ki szeretne lépni?", ButtonType.OK, ButtonType.CANCEL);
        alert.setHeaderText("Kilépés");
        Optional<ButtonType> buttonType = alert.showAndWait();
        buttonType.filter(x -> x.equals(ButtonType.OK)).ifPresent(x -> Platform.exit());
    }

    public void goToDatabasePage(ActionEvent actionEvent) {
        sceneManager.showScene(AppScene.DATABASE);

    }

    public void goToHomeDirPathsPage(ActionEvent actionEvent) {
        sceneManager.showScene(AppScene.HOME_DIR_PAGE);
    }

    public void goToMoraPatientServer(ActionEvent actionEvent) {
        sceneManager.showScene(AppScene.MORA_PATIENT_SERVER);
    }
}
