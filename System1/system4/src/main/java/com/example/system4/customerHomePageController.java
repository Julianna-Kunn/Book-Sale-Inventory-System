package com.example.system4;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class customerHomePageController {

    @FXML
    private Label userTypeLabel;

    @FXML
    private Label userNameLabel;

    // Method to set user data
    public void setUserData(String userType, String userName) {
        userTypeLabel.setText("User Type: " + userType);
        userNameLabel.setText("User Name: " + userName);
    }

    // Other controller methods...

}
