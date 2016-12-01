package hu.mora.tool.scene.springloader;

import com.google.common.base.Throwables;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class SpringFxmlLoader implements ApplicationContextAware {
    private ApplicationContext ctx;

    public Parent load(InputStream inputStream) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setControllerFactory(ctx::getBean);
            return loader.load(inputStream);
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }
}
