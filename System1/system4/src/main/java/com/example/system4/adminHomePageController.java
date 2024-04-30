package com.example.system4;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;


public class adminHomePageController {

    @FXML
    private Label userTypeLabel;

    @FXML
    private Label userNameLabel;

        public void setUserData(String userType, String userName) {
            userTypeLabel.setText("User Type: " + userType);
            userNameLabel.setText("Name: " + userName);
        }
}






