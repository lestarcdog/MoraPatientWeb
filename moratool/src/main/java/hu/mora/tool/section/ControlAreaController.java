package hu.mora.tool.section;

import hu.mora.tool.PropertyHolder;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ControlAreaController implements Initializable {

    @FXML
    private GridPane controlAreaRoot;

    private BooleanProperty disabled = new SimpleBooleanProperty(false);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        disabled.addListener((observable, oldValue, newValue) -> {
            controlAreaRoot.setDisable(newValue);
            System.out.println("Changed " + newValue);
        });
        PropertyHolder.L.add(disabled);
    }

    public BooleanProperty disabledProperty() {
        return disabled;
    }
}
