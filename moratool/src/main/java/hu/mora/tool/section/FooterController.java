package hu.mora.tool.section;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.springframework.stereotype.Controller;

@Controller
public class FooterController {
    public Label statusLine;

    public HBox messageBox;


    public void setSuccessMessage(String message) {
        statusLine.setText(message);
        messageBox.getStyleClass().clear();
        messageBox.getStyleClass().add("successMessageBox");
    }

    public void setStatus(String message) {
        statusLine.setText(message);
        messageBox.getStyleClass().clear();
    }
}
