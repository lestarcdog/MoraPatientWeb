package hu.mora.tool.section.content;

import hu.mora.tool.commands.database.DatabaseCommands;
import hu.mora.tool.commands.wildfly.WildflyCommands;
import hu.mora.tool.exception.MoraException;
import hu.mora.tool.scene.SceneManager;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class DatabaseController {

    @Autowired
    WildflyCommands wildflyCommands;

    @Autowired
    DatabaseCommands databaseCommands;

    @Autowired
    SceneManager sceneManager;

    public void saveCurrentDatabase(ActionEvent actionEvent) throws MoraException {
        Optional<String> drive = askUserWhereToSaveDb();
        if (!drive.isPresent()) {
            return;
        }

        if (wildflyCommands.isWildflyRunning()) {
            if (shouldStopTheServer()) {
                wildflyCommands.stopWildfly();
            } else {
                return;
            }
        }

        databaseCommands.saveDatabaseToPath(drive.get());
    }

    private Optional<String> askUserWhereToSaveDb() {
        List<String> files = Arrays.stream(File.listRoots())
                .map(File::toString)
                .filter(s -> !s.toLowerCase().startsWith("c"))
                .collect(Collectors.toList());
        if (files.isEmpty()) {
            sceneManager.showError("Csatlakoztasson egy külső meghajtót a számítógéphez.");
        }
        List<String> otherElements = files.size() > 1 ? files.subList(1, files.size()) : Collections.emptyList();
        ChoiceDialog<String> drivesDialog = new ChoiceDialog<>(files.get(0), otherElements);
        drivesDialog.setHeaderText("Meghajtó választás");
        return drivesDialog.showAndWait();
    }

    private boolean shouldStopTheServer() {
        ButtonType serverStopButton = ButtonType.OK;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "A szerver jelenleg fut a mentéshez a leállítás szükséges. Leállítás most?", serverStopButton, ButtonType.CANCEL);
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
