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

public class adminHomePage {
    @FXML
    private Label userIdLabel;
    @FXML
    private Button closeAdminHome;

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public void setUserData(String idstr) {
        userIdLabel.setText(idstr);
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

    public void signUp(){
        String idstr = userIdLabel.getText();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("adminSignUp.fxml"));
            Parent root = loader.load();

            adminSignUp controller = loader.getController();

            controller.setUserData(idstr);

            Stage stage = (Stage) closeAdminHome.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            logger.severe("Error occurred during opening adminSignUp " + e.getMessage());
        }
    }

    public void adminInfo(){
        String idstr = userIdLabel.getText();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("userInfo.fxml"));
            Parent root = loader.load();

            userInfo controller = loader.getController();

            controller.setUserData(idstr);

            Stage stage = (Stage) closeAdminHome.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            logger.severe("Error occurred during opening adminSubPage " + e.getMessage());
        }
    }

    public void userLogAccounts(){
        String idstr = userIdLabel.getText();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("adminSubUserLog.fxml"));
            Parent root = loader.load();

            adminSubUserLog controller = loader.getController();

            controller.setUserData(idstr);

            Stage stage = (Stage) closeAdminHome.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            logger.severe("Error occurred during opening adminSubPage " + e.getMessage());
        }
    }

    public void closeAdminHome() {
        Stage stage = (Stage) this.closeAdminHome.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void addBooksButton(ActionEvent event) {
        URL fxmlResource = getClass().getResource("AddBooks.fxml");

        if (fxmlResource == null) {
            logger.severe("FXML resource 'MainInterface.fxml' not found");
            return;
        }
        try {
            Parent root = FXMLLoader.load(fxmlResource);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            logger.severe("Error loading adminHomePage.fxml: " + e.getMessage());
        }
    }

    @FXML
    public void viewBooksButton(ActionEvent event) {
        URL fxmlResource = getClass().getResource("ViewBooks.fxml");

        if (fxmlResource == null) {
            logger.severe("FXML resource 'adminHomePage.fxml' not found");
            return;
        }
        try {
            Parent root = FXMLLoader.load(fxmlResource);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            logger.severe("Error loading adminHomePage.fxml: " + e.getMessage());
        }
    }

}







