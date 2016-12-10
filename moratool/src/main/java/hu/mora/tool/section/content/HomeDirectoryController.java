package hu.mora.tool.section.content;

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

    @FXML
    private TextField moraPatientHomeDirectory;

    @FXML
    private ImageView pathStatusIcon;

    @FXML
    private Pane invalidHomeDirectory;

    @Autowired
    private SceneManager manager;

    @Autowired
    MoraPaths moraPaths;

    @Autowired
    ConfigurationRepository configurationRepository;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String initHome = configurationRepository.homeDirectory();
        validateHomeDirTextField(initHome);
    }

    @FXML
    public void openHomeDirectoryChooser(ActionEvent actionEvent) throws IOException {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("MoraPatient könyvtár");
        File selectedFile = chooser.showDialog(manager.getMainStage());
        if (selectedFile != null) {
            validateHomeDirTextField(selectedFile.getCanonicalPath());
        }
    }


    private void validateHomeDirTextField(String filePath) {
        moraPatientHomeDirectory.setText(filePath);
        boolean isHomeDirectory = moraPaths.isHomeDirectory(filePath);
        invalidHomeDirectory.setVisible(!isHomeDirectory);
        if (isHomeDirectory) {
            pathStatusIcon.setImage(new Image(GraphicsPath.okIcon()));
        } else {
            pathStatusIcon.setImage(new Image(GraphicsPath.errorIcon()));
        }
    }
}
