package com.example.system4;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainInterfaceController {
    @FXML
    private Button closeMainButton;

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public void logInButton(ActionEvent event) {
        URL fxmlResource = getClass().getResource("Log-in.fxml");

        if (fxmlResource == null) {
            logger.severe("FXML resource 'Log-in.fxml' not found");
            return;
        }
        try {
            Parent root = FXMLLoader.load(fxmlResource);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            logger.severe("Error loading Log-in.fxml: " + e.getMessage());
        }
    }

    public void signUpButton(ActionEvent event) {
        URL fxmlResource = getClass().getResource("CustomerSignUp.fxml");

        if (fxmlResource == null) {
            logger.severe("FXML resource 'CustomerSignUp.fxml' not found");
            return;
        }
        try {
            Parent root = FXMLLoader.load(fxmlResource);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            logger.severe("Error loading CustomerSignUp.fxml: " + e.getMessage());
        }
    }

    public void closeMainButton() {
        Stage stage = (Stage) this.closeMainButton.getScene().getWindow();
        stage.close();
    }
}
