package hu.mora.tool.section.content;

import hu.mora.tool.property.PropertyHolder;
import hu.mora.tool.scene.AppScene;
import hu.mora.tool.scene.SceneManager;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class ControlAreaController implements Initializable {

    @FXML
    private StackPane contentArea;

    private BooleanProperty disabled = new SimpleBooleanProperty(false);

    @Autowired
    SceneManager sceneManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        disabled.addListener((observable, oldValue, newValue) -> showInvalidHomeDirectorySection());
        PropertyHolder.L.add(disabled);
    }

    public BooleanProperty disabledProperty() {
        return disabled;
    }

    public void showMainContent(Parent parent) {
        contentArea.getChildren().clear();
        contentArea.getChildren().add(parent);
    }

    private void showInvalidHomeDirectorySection() {
        sceneManager.showScene(AppScene.ERROR_HOME_DIR);
    }
}
