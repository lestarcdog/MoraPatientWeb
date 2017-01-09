package hu.mora.tool.section.content;

import com.google.common.collect.ImmutableList;
import hu.mora.tool.commands.database.DatabaseCommands;
import hu.mora.tool.commands.wildfly.WildflyCommands;
import hu.mora.tool.configuration.MoraPaths;
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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.util.stream.Collectors.toList;

@Controller
public class DatabaseController {

    private static final Logger LOG = LoggerFactory.getLogger(DatabaseController.class);

    private static final List<String> DATABASES = ImmutableList.of("MoraPatient", "NovaDB");

    @FXML
    public Button databaseSaveButton;

    @FXML
    public Button databaseRestoreButton;

    @Autowired
    private WildflyCommands wildflyCommands;

    @Autowired
    private DatabaseCommands databaseCommands;

    @Autowired
    private SceneManager sceneManager;

    @Autowired
    private MoraPaths moraPaths;

    @Autowired
    private ThreadPoolTaskExecutor executor;

    public void saveCurrentDatabase(ActionEvent actionEvent) {
        Optional<String> drive = askUserToSelectExternalDrive();
        //cancelled or invalid
        if (!drive.isPresent()) {
            return;
        }
        databaseSaveButton.setDisable(true);
        sceneManager.showSuccess("Az adatbázis mentés megkezdődött");
        executor.execute(() -> {
            try {
                databaseCommands.saveDatabaseToPath(drive.get());
                Platform.runLater(() -> {
                    Alert success = new Alert(Alert.AlertType.INFORMATION, "Az adatbázis mentés sikeres volt", ButtonType.OK);
                    success.setHeaderText("Sikeres adatbázis mentés");
                    success.showAndWait();
                });
            } catch (MoraException e) {
                sceneManager.showError(e.getMessage(), e);
            } finally {
                databaseSaveButton.setDisable(false);
            }
        });


    }

    public void restorePreviousDatabase(ActionEvent actionEvent) {
        Optional<String> drive = askUserToSelectExternalDrive();
        //cancelled or invalid
        if (!drive.isPresent()) {
            return;
        }

        // user select database
        Optional<String> database = whichDatabaseReload();
        if (!database.isPresent()) {
            return;
        }

        // select backup path
        Path backupPath;
        boolean restoreMoraPatient = true;
        if (database.get().equals(DATABASES.get(0))) {
            backupPath = moraPaths.database.backupMoraDbPath(drive.get());
            restoreMoraPatient = true;
        } else {
            backupPath = moraPaths.database.backupNovaDbPath(drive.get());
            restoreMoraPatient = false;
        }

        //check backup path exists
        if (!Files.exists(backupPath)) {
            sceneManager.showError("A visszaállítási útvonal nem elérhető. " + backupPath.toString());
            return;
        }

        //list backup versions
        List<Path> restorePaths;
        try {
            restorePaths = Files.walk(backupPath, 1).collect(toList());
            if (restorePaths.isEmpty()) {
                sceneManager.showError("Nem található egyetlen egy mentés sem az útvonalon: " + backupPath.toString());
                return;
            }
        } catch (IOException e) {
            sceneManager.showError("Nem megnyitható a fájl: " + backupPath.toString());
            return;
        }
        Optional<Integer> selectedRestorePathIdx = listAllBackedUpVersions(restorePaths, restoreMoraPatient);
        if (!selectedRestorePathIdx.isPresent()) {
            return;
        }

        Path restorePath = restorePaths.get(selectedRestorePathIdx.get());
        if (restoreMoraPatient) {

        } else {

        }


        databaseRestoreButton.setDisable(true);
        databaseRestoreButton.setDisable(false);
    }

    private Optional<String> askUserToSelectExternalDrive() {
        List<String> files = Arrays.stream(File.listRoots())
                .map(File::toString)
                .filter(s -> !s.toLowerCase().startsWith("c"))
                .collect(toList());
        if (files.isEmpty()) {
            sceneManager.showError("Csatlakoztasson egy külső meghajtót a számítógéphez.");
            return Optional.empty();
        }
        List<String> otherElements = files.size() > 1 ? files.subList(1, files.size()) : Collections.emptyList();
        ChoiceDialog<String> drivesDialog = new ChoiceDialog<>(files.get(0), otherElements);
        drivesDialog.setHeaderText("Meghajtó választás");
        return drivesDialog.showAndWait();
    }

    private Optional<String> whichDatabaseReload() {
        ChoiceDialog<String> choiceDialog = new ChoiceDialog<>(DATABASES.get(0), DATABASES);
        choiceDialog.setHeaderText("Adatbázis visszatöltése");
        return choiceDialog.showAndWait();
    }

    private boolean shouldStopTheServer() {
        ButtonType serverStopButton = ButtonType.OK;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "A szerver jelenleg fut a visszaállításhoz a leállítás szükséges. " +
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

    private static final DateTimeFormatter RESTORE_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");

    private Optional<Integer> listAllBackedUpVersions(List<Path> restorePaths, boolean restoreMoraPatient) {
        //all files in path
        List<String> restoreDates = restorePaths.stream()
                .map(p -> p.getFileName().toString())
                .map(s -> moraPaths.database.extractSavedDateFromPath(s, restoreMoraPatient))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .sorted(Comparator.reverseOrder())
                .map(RESTORE_FORMATTER::format)
                .collect(toList());
        ChoiceDialog<String> restoreOptions = new ChoiceDialog<>(restoreDates.get(0), restoreDates);
        restoreOptions.setHeaderText("Visszatöltendő állapot");
        Optional<String> userChoice = restoreOptions.showAndWait();
        if (userChoice.isPresent()) {
            return Optional.of(restoreDates.indexOf(userChoice.get()));
        } else {
            return Optional.empty();
        }
    }

}
