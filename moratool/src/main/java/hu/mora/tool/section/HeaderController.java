package hu.mora.tool.section;

import hu.mora.tool.MainStageHolder;
import hu.mora.tool.graphics.GraphicsPath;
import hu.mora.tool.path.MoraHomeVerifier;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HeaderController implements Initializable {

    @FXML
    private TextField moraPatientHomeDirectory;

    @FXML
    private ImageView pathStatusIcon;


    private BooleanProperty validPath = new SimpleBooleanProperty(false);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public void openHomeDirectoryChooser(ActionEvent actionEvent) throws IOException {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("MoraPatient könyvtár");
        File selectedFile = chooser.showDialog(MainStageHolder.getMainStage());
        if (selectedFile != null) {
            moraPatientHomeDirectory.setText(selectedFile.getCanonicalPath());
            boolean isHomeDirectory = MoraHomeVerifier.isHomeDirectory(selectedFile);
            validPath.set(isHomeDirectory);
            if (isHomeDirectory) {
                pathStatusIcon.setImage(new Image(GraphicsPath.okIcon()));
            } else {
                pathStatusIcon.setImage(new Image(GraphicsPath.errorIcon()));
            }
        }
    }


    public BooleanProperty validPathProperty() {
        return validPath;
    }
}
