package hu.mora.tool;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.URL;

public class MoraTool extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL mainFxml = MoraTool.class.getResource("/fxml/main.fxml");
        Parent mainParent = FXMLLoader.load(mainFxml);

        primaryStage.getIcons().add(new Image("graphics/mora_icon.jpg"));
        primaryStage.setScene(new Scene(mainParent));

        MainStageHolder.setMainStage(primaryStage);

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
