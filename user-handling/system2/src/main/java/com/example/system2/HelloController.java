//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.system2;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class HelloController {
    @FXML
    private Button logInButton;
    @FXML
    private Button signUPButton;
    @FXML
    private Button closeMainButton;
    @FXML
    private Button closeLogInButton;
    @FXML
    private Button closeSignUpButton;

    public HelloController() {
    }

    public void logInButton() throws IOException {
        Stage currentStage = (Stage)this.logInButton.getScene().getWindow();
        currentStage.close();
        Stage primaryStage = new Stage();
        Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("2_LoginInterface.fxml"));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(root, 1000.0, 600.0));
        primaryStage.show();
    }

    public void signUPButton() throws IOException {
        Stage signUpStage = (Stage)this.signUPButton.getScene().getWindow();
        signUpStage.close();
        Stage secondryStage = new Stage();
        Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("3_SignUpInterface.fxml"));
        secondryStage.initStyle(StageStyle.UNDECORATED);
        secondryStage.setScene(new Scene(root, 1000.0, 600.0));
        secondryStage.show();
    }

    public void closeMainButton(ActionEvent e) {
        Stage stage = (Stage)this.closeMainButton.getScene().getWindow();
        stage.close();
    }

    public void closeLogInButton(ActionEvent e) {
        Stage stage = (Stage)this.closeLogInButton.getScene().getWindow();
        stage.close();
    }

    public void closeSignUpButton(ActionEvent e) {
        Stage stage = (Stage)this.closeSignUpButton.getScene().getWindow();
        stage.close();
    }
}
