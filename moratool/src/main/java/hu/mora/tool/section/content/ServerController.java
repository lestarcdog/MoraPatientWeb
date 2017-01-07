package hu.mora.tool.section.content;

import hu.mora.tool.commands.wildfly.WildflyCommands;
import hu.mora.tool.exception.MoraException;
import hu.mora.tool.scene.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class ServerController implements Initializable {

    @FXML
    public Label status;

    @FXML
    public Button stopButton;

    @FXML
    public Button startButton;

    @Autowired
    private SceneManager sceneManager;

    @Autowired
    private WildflyCommands commands;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refresh();
    }

    private void refresh() {
        try {
            boolean running = commands.isWildflyRunning();
            if (running) {
                startButton.setDisable(true);
                stopButton.setDisable(false);
                status.setText(" fut.");
                status.getStyleClass().clear();
                status.getStyleClass().add("successText");
            } else {
                startButton.setDisable(false);
                stopButton.setDisable(true);
                status.setText(" nem fut.");
                status.getStyleClass().clear();
                status.getStyleClass().add("errorText");
            }
        } catch (MoraException e) {
            sceneManager.showError(e.getMessage(), e);
            status.setText("nem elérhető.");
        }
    }

    public void startWildfly(ActionEvent actionEvent) {
        sceneManager.showStatusMessage("Szerver indítása folyamatban.");
        try {
            if (!commands.isWildflyRunning()) {
                commands.startWildfly();
            }
            sceneManager.showSuccess("A szerver sikeresen elindult.");
            refresh();
        } catch (MoraException e) {
            sceneManager.showError(e.getMessage(), e);
        }
    }

    public void stopWildfly(ActionEvent actionEvent) {
        sceneManager.showStatusMessage("Szerver leállítása folyamatban.");
        try {
            if (commands.isWildflyRunning()) {
                commands.stopWildfly();
            }
            sceneManager.showSuccess("A szerver sikeresen leállt.");
            refresh();
        } catch (MoraException e) {
            sceneManager.showError(e.getMessage(), e);
        }
    }
}
