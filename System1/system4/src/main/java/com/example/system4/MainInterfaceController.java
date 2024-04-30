package com.example.system4;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainInterfaceController {
    @FXML
    private Button logInButton;
    @FXML
    private Button signUpButton;
    @FXML
    private Button closeMainButton;

    public MainInterfaceController() {
    }

    public void logInButton() throws IOException {
        Stage currentStage = (Stage) logInButton.getScene().getWindow();
        currentStage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Log-in.fxml"));
        Parent root = fxmlLoader.load();
        Stage primaryStage = new Stage();
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(root, 1000.0, 600.0));
        primaryStage.show();
    }

    public void signUpButton() throws IOException{
        Stage currentStage = (Stage) signUpButton.getScene().getWindow();
        currentStage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CustomerSignUp.fxml"));
        Parent root = fxmlLoader.load();
        Stage primaryStage = new Stage();
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(root, 1000.0, 600.0));
        primaryStage.show();
    }

    public void closeMainButton(ActionEvent e) {
        Stage stage = (Stage)this.closeMainButton.getScene().getWindow();
        stage.close();
    }
}
