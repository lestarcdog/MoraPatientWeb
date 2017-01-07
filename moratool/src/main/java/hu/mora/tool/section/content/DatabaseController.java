package hu.mora.tool.section.content;

import hu.mora.tool.commands.database.DatabaseCommands;
import hu.mora.tool.commands.wildfly.WildflyCommands;
import hu.mora.tool.exception.MoraException;
import hu.mora.tool.scene.SceneManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class DatabaseController {

    private static final Logger LOG = LoggerFactory.getLogger(DatabaseController.class);

    @FXML
    public Button databaseSaveButton;

    @FXML
    public Button databaseReloadButton;

    @Autowired
    private WildflyCommands wildflyCommands;

    @Autowired
    private DatabaseCommands databaseCommands;

    @Autowired
    private SceneManager sceneManager;

    @Autowired
    private ThreadPoolTaskExecutor executor;

    public void saveCurrentDatabase(ActionEvent actionEvent) {
        Optional<String> drive = askUserWhereToSaveDb();
        //cancelled or invalid
        if (!drive.isPresent()) {
            return;
        }
        databaseSaveButton.setDisable(true);
        sceneManager.showSuccess("Az adatbázis mentés megkezdődött");
        executor.execute(() -> {
            try {
                //TODO run this in another thread
                databaseCommands.saveDatabaseToPath(drive.get());
                Platform.runLater(() -> {
                    Alert success = new Alert(Alert.AlertType.INFORMATION, "Az adatbázis mentés sikeres volt", ButtonType.OK);
                    success.setHeaderText("Sikeres adatbázis mentés");
                    success.showAndWait();
                });

            } catch (MoraException e) {
                Platform.runLater(() -> sceneManager.showError(e.getMessage(), e));
            } finally {
                Platform.runLater(() -> databaseSaveButton.setDisable(false));
            }
        });


    }

    private Optional<String> askUserWhereToSaveDb() {
        List<String> files = Arrays.stream(File.listRoots())
                .map(File::toString)
                .filter(s -> !s.toLowerCase().startsWith("c"))
                .collect(Collectors.toList());
        if (files.isEmpty()) {
            sceneManager.showError("Csatlakoztasson egy külső meghajtót a számítógéphez.");
            return Optional.empty();
        }
        List<String> otherElements = files.size() > 1 ? files.subList(1, files.size()) : Collections.emptyList();
        ChoiceDialog<String> drivesDialog = new ChoiceDialog<>(files.get(0), otherElements);
        drivesDialog.setHeaderText("Meghajtó választás");
        return drivesDialog.showAndWait();
    }

    private boolean shouldStopTheServer() {
        ButtonType serverStopButton = ButtonType.OK;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "A szerver jelenleg fut a mentéshez a leállítás szükséges. " +
                        "A nem mentett változtatások elvesznek. Leállítás most?",
                serverStopButton, ButtonType.CANCEL);
        alert.setHeaderText("A szerver jelenleg fut.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            ButtonType serverStopResult = result.get();
            if (serverStopResult.equals(serverStopButton)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

}
