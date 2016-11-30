package example;

import javafx.beans.property.SimpleDoubleProperty;
import org.junit.Test;

public class BindTest {

    @Test
    public void bindMe() throws Exception {
        SimpleDoubleProperty d1 = new SimpleDoubleProperty(5);
        SimpleDoubleProperty d2 = new SimpleDoubleProperty();
        d2.bind(d1);

    }
}
