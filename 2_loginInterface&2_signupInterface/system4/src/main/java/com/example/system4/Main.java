package com.example.system4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

public class Main extends Application {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    @Override
    public void start(Stage primaryStage){
        URL fxmlResource = getClass().getResource("MainInterface.fxml");

        if (fxmlResource == null) {
            logger.severe("FXML resource 'MainInterface.fxml' not found");
            return;
        }

        try {
            Parent root = FXMLLoader.load(fxmlResource);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.setResizable(false);
        } catch (IOException e) {
            logger.severe("Error opening MainInterface.fxml");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
