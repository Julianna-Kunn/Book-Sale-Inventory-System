package com.example.system4;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class LoginController {

    @FXML
    private Button closeLogInButton;
    @FXML
    private TextField userIDTextField;
    @FXML
    private PasswordField passPasswordField;

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    @FXML
    public void BackButtonOnAction(ActionEvent event) {
        URL fxmlResource = getClass().getResource("MainInterface.fxml");

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
            logger.severe("Error loading MainInterface.fxml: " + e.getMessage());
        }
    }

    @FXML
    public void validateLogin() {
        String url = "jdbc:mysql://127.0.0.1:3306/signup_schema";
        String user = "root";
        String dbPassword = "119904100297";

        String adminId = "1";
        String adminPassword = "adminpass";
        String idStr = userIDTextField.getText();
        String password = passPasswordField.getText();

        try (Connection connection = DriverManager.getConnection(url, user, dbPassword)) {

            if (userIDTextField.getText().isEmpty() || password.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Please fill out user credentials.");
                alert.showAndWait();
            }

            int id;
            try {
                id = Integer.parseInt(idStr);
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Please enter a valid ID number.");
                alert.showAndWait();
                return;
            }

            if (idStr.equals(adminId) && password.equals(adminPassword)) {
                openAdminHomePage("1", "Admin User");
                return;
            }

            String query = "SELECT * FROM users WHERE userid = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String idstr = String.valueOf(id);
                String userType = resultSet.getString("user_type");
                String userName = resultSet.getString("name");

                switch (userType) {
                    case "Admin":
                        openAdminHomePage(idstr, userName);
                        break;
                    case "Staff":
                        openStaffHomePage(idstr, userName);
                        break;
                    case "Customer":
                        openCustomerHomePage(idstr, userName);
                        break;
                    default:
                        showAlert("Unknown user type.");
                        break;
                }
            } else {
                showAlert("Invalid ID or password. Please try again.");
            }
        } catch (SQLException | NumberFormatException e) {
            logger.severe("Error occurred during login: " + e.getMessage());
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    private void openAdminHomePage(String idstr, String userName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("adminHomePage.fxml"));
            Parent root = loader.load();

            // Get the controller instance
            adminHomePageController controller = loader.getController();

            // Call a method in the AdminHomePageController to set user data
            controller.setUserData(idstr, userName);

            Stage stage = (Stage) closeLogInButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            logger.severe("Error occurred during opening adminHomePage " + e.getMessage());
        }
    }

    private void openStaffHomePage(String idstr, String userName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("staffHomePage.fxml"));
            Parent root = loader.load();

            staffHomePageController controller = loader.getController();

            controller.setUserData(idstr, userName);

            Stage stage = (Stage) closeLogInButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            logger.severe("Error occurred during opening staffHomePage" + e.getMessage());
        }
    }

    private void openCustomerHomePage(String idstr, String userName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("customerHomePage.fxml"));
            Parent root = loader.load();

            customerHomePageController controller = loader.getController();

            controller.setUserData(idstr, userName);

            Stage stage = (Stage) closeLogInButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            logger.severe("Error occurred during opening customerHomePage " + e.getMessage());
        }
    }

    @FXML
    public void closeLogInButton() {
        Stage stage = (Stage) this.closeLogInButton.getScene().getWindow();
        stage.close();
    }
}
