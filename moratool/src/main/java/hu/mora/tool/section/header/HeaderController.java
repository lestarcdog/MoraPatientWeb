package hu.mora.tool.section.header;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.springframework.stereotype.Controller;

@Controller
public class HeaderController {

    private BooleanProperty validPath = new SimpleBooleanProperty(false);

    public BooleanProperty validPathProperty() {
        return validPath;
    }
}
