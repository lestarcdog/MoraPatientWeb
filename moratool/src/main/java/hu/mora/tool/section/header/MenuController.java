package hu.mora.tool.section.header;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class MenuController {
    public void log(ActionEvent actionEvent) {
        System.out.println("AAAA");
    }

    public void onExit(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Biztos ki szeretne lépni?", ButtonType.OK, ButtonType.CANCEL);
        Optional<ButtonType> buttonType = alert.showAndWait();
        buttonType.filter(x -> x.equals(ButtonType.OK)).ifPresent(x -> Platform.exit());
    }
}
