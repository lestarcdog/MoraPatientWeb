package hu.mora.tool.section;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private Pane rootPane;

    @FXML
    private ControlAreaController controlAreaController;

    @FXML
    private HeaderController headerController;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controlAreaController.disabledProperty().bind(headerController.validPathProperty());
    }


}
