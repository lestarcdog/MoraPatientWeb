package hu.mora.tool.section.content;

import hu.mora.tool.configuration.Config;
import hu.mora.tool.configuration.ConfigurationRepository;
import hu.mora.tool.configuration.MoraPaths;
import hu.mora.tool.graphics.GraphicsPath;
import hu.mora.tool.scene.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class HomeDirectoryController implements Initializable {

    // Mora patient
    @FXML
    private TextField moraPatientHomeDirectory;

    @FXML
    private ImageView moraPatientPathStatusIcon;

    @FXML
    private Pane invalidMoraPatientHomeDir;

    //Nova Db
    @FXML
    private TextField novaDbHomeDirectory;

    @FXML
    private ImageView novaDbPathStatusIcon;

    @FXML
    private Pane invalidNovaDbHomeDir;

    @Autowired
    private SceneManager manager;

    @Autowired
    private MoraPaths moraPaths;

    @Autowired
    private ConfigurationRepository configurationRepository;

    private Config config;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        config = configurationRepository.config();
        validateMoraPatientHomeDirTextField(config.getMoraPatientHomeDirPath().toString(), false);
        validateNovaDbHomeDirTextField(config.getNovaDbHomeDirPath().toString(), false);
    }

    @FXML
    public void openMoraPatientHomeDirectoryChooser(ActionEvent actionEvent) throws IOException {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("MoraPatient könyvtár");
        File selectedFile = chooser.showDialog(manager.getMainStage());
        if (selectedFile != null) {
            validateMoraPatientHomeDirTextField(selectedFile.getCanonicalPath(), true);
        }
    }


    private void validateMoraPatientHomeDirTextField(String filePath, boolean save) {
        moraPatientHomeDirectory.setText(filePath);
        boolean isHomeDirectory = moraPaths.isMoraPatientHomeDir(filePath);
        invalidMoraPatientHomeDir.setVisible(!isHomeDirectory);
        if (isHomeDirectory) {
            moraPatientPathStatusIcon.setImage(new Image(GraphicsPath.okIcon()));
            if (save) {
                config.setMoraPatientHomeDir(filePath);
            }
        } else {
            moraPatientPathStatusIcon.setImage(new Image(GraphicsPath.errorIcon()));
        }
    }

    public void openNovaDbHomeDirectoryChooser(ActionEvent actionEvent) throws IOException {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("NovaDB könyvtár");
        File selectedFile = chooser.showDialog(manager.getMainStage());
        if (selectedFile != null) {
            validateNovaDbHomeDirTextField(selectedFile.getCanonicalPath(), true);
        }
    }

    private void validateNovaDbHomeDirTextField(String canonicalPath, boolean save) {
        novaDbHomeDirectory.setText(canonicalPath);
        boolean isHomeDirectory = moraPaths.isNovaDbHomeDir(canonicalPath);
        invalidNovaDbHomeDir.setVisible(!isHomeDirectory);
        if (isHomeDirectory) {
            novaDbPathStatusIcon.setImage(new Image(GraphicsPath.okIcon()));
            if (save) {
                config.setNovaDbHomeDir(canonicalPath);
            }
        } else {
            novaDbPathStatusIcon.setImage(new Image(GraphicsPath.errorIcon()));
        }
    }
}
