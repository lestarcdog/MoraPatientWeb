package hu.mora.tool.section;

import hu.mora.tool.MainStageHolder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

public class MainController {

    @FXML
    private Pane rootPane;

    @FXML
    private TextField moraPatientHomeDirectory;

    @FXML
    private Label pathStatusIcon;

    public void openHomeDirectoryChooser(ActionEvent actionEvent) {
        FileChooser chooser = new FileChooser();
        chooser.showOpenDialog(MainStageHolder.getMainStage());
    }
}
