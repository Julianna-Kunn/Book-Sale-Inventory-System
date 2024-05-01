package org.example.system2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.StageStyle;

import java.io.IOException;


public class HelloController{
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

    @FXML
    private Button backLogInButton;

    @FXML
    private Button backSignUpButton;

    @FXML
    private Button backBuyBooksButton;

    @FXML
    private Button closeBuyBooksButton;

    @FXML
    private Button buyBookButton;

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

    public void backLogInButton() throws IOException{
        Stage backLoginStage = (Stage) backLogInButton.getScene().getWindow();
        backLoginStage .close();

        Stage backLoginButtonStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("1_MainInterface.fxml"));
        backLoginButtonStage.initStyle(StageStyle.UNDECORATED);
        backLoginButtonStage.setScene(new Scene(root, 1000, 600));
        backLoginButtonStage.show();
    }

    public void backSignUpButton() throws IOException{
        Stage backSignUpStage = (Stage) backSignUpButton.getScene().getWindow();
        backSignUpStage.close();

        Stage backSignUpButtonStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("1_MainInterface.fxml"));
        backSignUpButtonStage.initStyle(StageStyle.UNDECORATED);
        backSignUpButtonStage.setScene(new Scene(root, 1000, 600));
        backSignUpButtonStage.show();
    }

    public void backBuyBooksButton() throws IOException{
        Stage backBuyBooksStage = (Stage) backBuyBooksButton.getScene().getWindow();
        backBuyBooksStage.close();

        Stage backBuyBooksButtonStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("1_MainInterface.fxml"));
        backBuyBooksButtonStage.initStyle(StageStyle.UNDECORATED);
        backBuyBooksButtonStage.setScene(new Scene(root, 1000, 600));
        backBuyBooksButtonStage.show();
    }

    public void closeBuyBooksButton(ActionEvent e){
        Stage stage = (Stage) closeBuyBooksButton.getScene().getWindow();
        stage.close();
    }

    public void buyBookButton() throws IOException{
        Stage buyBookStage = (Stage) buyBookButton.getScene().getWindow();
        buyBookStage.close();

        Stage buyBookButtonStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("10_BuyBooks.fxml"));
        buyBookButtonStage.initStyle(StageStyle.UNDECORATED);
        buyBookButtonStage.setScene(new Scene(root, 1000, 600));
        buyBookButtonStage.show();
    }

}