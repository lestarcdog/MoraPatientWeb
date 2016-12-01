package hu.mora.tool;

import hu.mora.tool.scene.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MoraTool extends Application {

    private static final Logger LOG = LoggerFactory.getLogger(MoraTool.class);
    private static final ApplicationContext CTX = new ClassPathXmlApplicationContext("classpath:/context.xml");

    @Override
    public void start(Stage primaryStage) throws Exception {
        SceneManager sceneManager = CTX.getBean(SceneManager.class);

        Thread.setDefaultUncaughtExceptionHandler((t, exception) -> {
            LOG.error(exception.getMessage(), exception);
            sceneManager.showError("A művelet nem sikerült. Hiba történt: " + exception.getMessage());
        });
        sceneManager.setupStage(primaryStage);

    }


    public static void main(String[] args) {
        launch(args);
    }
}
