package com.example.system4;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private Button backButton;
    @FXML
    private Button closeLogInButton;
    @FXML
    private TextField userIDTextField;
    @FXML
    private PasswordField passPasswordField;

    @FXML
    public void BackButtonOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainInterface.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 1000, 600));
            stage.show();

            Stage loginStage = (Stage) closeLogInButton.getScene().getWindow();
            loginStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void validateLogin(ActionEvent event) {
        String url = "jdbc:mysql://127.0.0.1:3306/signup_schema";
        String user = "root";
        String dbPassword = "119904100297";

        try (Connection connection = DriverManager.getConnection(url, user, dbPassword)) {
            String idStr = userIDTextField.getText();
            String password = passPasswordField.getText();

            if (userIDTextField.getText().isEmpty() || password.isEmpty()) {
                showAlert("Please fill out user credentials.");
                return;
            }

            int id;
            try {
                id = Integer.parseInt(idStr);
            } catch (NumberFormatException e) {
                showAlert("Please enter a valid ID number.");
                return;
            }

            String query = "SELECT * FROM users WHERE userid = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String userType = resultSet.getString("user_type");
                String userName = resultSet.getString("name");

                switch (userType) {
                    case "Admin":
                        openAdminHomePage(userType, userName);
                        break;
                    case "Staff":
                        openStaffHomePage(userType, userName);
                        break;
                    case "Customer":
                        openCustomerHomePage(userType, userName);
                        break;
                    default:
                        showAlert("Unknown user type.");
                        break;
                }
            } else {
                showAlert("Invalid ID or password. Please try again.");
            }
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            showAlert("Error occurred during login.");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void openAdminHomePage(String userType, String userName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("adminHomePage.fxml"));
            Parent root = loader.load();

            // Get the controller instance
            adminHomePageController controller = loader.getController();

            // Call a method in the AdminHomePageController to set user data
            controller.setUserData(userType, userName);

            Stage stage = new Stage();
            stage.setScene(new Scene(root,1000,600));
            stage.show();

            // Close the login stage
            Stage loginStage = (Stage) closeLogInButton.getScene().getWindow();
            loginStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openStaffHomePage(String userType, String userName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("staffHomePage.fxml"));
            Parent root = loader.load();

            // Get the controller instance
            staffHomePageController controller = loader.getController();

            // Call a method in the AdminHomePageController to set user data
            controller.setUserData(userType, userName);

            Stage stage = new Stage();
            stage.setScene(new Scene(root,1000, 600));
            stage.show();

            // Close the login stage
            Stage loginStage = (Stage) closeLogInButton.getScene().getWindow();
            loginStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openCustomerHomePage(String userType, String userName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("customerHomePage.fxml"));
            Parent root = loader.load();

            // Get the controller instance
            customerHomePageController controller = loader.getController();

            // Call a method in the AdminHomePageController to set user data
            controller.setUserData(userType, userName);

            Stage stage = new Stage();
            stage.setScene(new Scene(root,1000,600));
            stage.show();

            // Close the login stage
            Stage loginStage = (Stage) closeLogInButton.getScene().getWindow();
            loginStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void closeLogInButton(ActionEvent e) {
        Stage stage = (Stage) this.closeLogInButton.getScene().getWindow();
        stage.close();
    }
}
