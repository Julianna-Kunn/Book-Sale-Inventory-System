package com.example.system4;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.logging.Logger;

public class adminHomePageController {
    @FXML
    private Label userIdLabel;
    @FXML
    private Label userNameLabel;
    @FXML
    private Button closeAdminHome;
    @FXML
    private Button userLog;

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public void setUserData(String idstr, String userName) {
        userIdLabel.setText(idstr);
        userNameLabel.setText(userName);
    }

    public void Logout() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure you want to log-out?");

        ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.NO);

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == buttonTypeYes) {
            URL fxmlResource = getClass().getResource("MainInterface.fxml");

            if (fxmlResource == null) {
                logger.severe("FXML resource 'MainInterface.fxml' not found");
                return;
            }
            try {
                Parent root = FXMLLoader.load(fxmlResource);

                Stage stage = (Stage) closeAdminHome.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                logger.severe("Error loading MainInterface.fxml: " + e.getMessage());
            }
        }
    }

    public void userLogAccounts(ActionEvent event){
        URL fxmlResource = getClass().getResource("adminSubUserLog.fxml");

        if (fxmlResource == null) {
            logger.severe("FXML resource 'adminSubUserLog.fxml' not found");
            return;
        }
        try {
            Parent root = FXMLLoader.load(fxmlResource);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            logger.severe("Error occurred during opening adminSubUserLog: " + e.getMessage());
        }
    }

    public void closeAdminHome() {
        Stage stage = (Stage) this.closeAdminHome.getScene().getWindow();
        stage.close();
    }
}







