package org.example.booksales_inventory_system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.StageStyle;

import java.io.IOException;

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


    public void logInButton() throws IOException{
       Stage currentStage = (Stage) logInButton.getScene().getWindow();
        currentStage.close();

       Stage primaryStage = new Stage();
       Parent root = FXMLLoader.load(getClass().getResource("2_LoginInterface.fxml"));
       primaryStage.initStyle(StageStyle.UNDECORATED);
       primaryStage.setScene(new Scene(root, 1000, 600));
       primaryStage.show();

    }

    public void signUPButton() throws IOException{
        Stage signUpStage = (Stage) signUPButton.getScene().getWindow();
        signUpStage .close();

        Stage secondryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("3_SignUpInterface.fxml"));
        secondryStage.initStyle(StageStyle.UNDECORATED);
        secondryStage.setScene(new Scene(root, 1000, 600));
        secondryStage.show();
    }

    public void closeMainButton(ActionEvent e){
        Stage stage = (Stage) closeMainButton.getScene().getWindow();
        stage.close();
    }

    public void closeLogInButton(ActionEvent e){
        Stage stage = (Stage) closeLogInButton.getScene().getWindow();
        stage.close();
    }

    public void closeSignUpButton(ActionEvent e){
        Stage stage = (Stage) closeSignUpButton.getScene().getWindow();
        stage.close();
    }



}