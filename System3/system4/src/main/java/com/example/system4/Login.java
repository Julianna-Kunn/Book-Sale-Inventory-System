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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

public class Login {

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

        String adminId = "A-1";
        String adminPassword = "adminpass";
        String idstr = userIDTextField.getText();
        String password = passPasswordField.getText();

        try (Connection connection = DriverManager.getConnection(url, user, dbPassword)) {
            if (idstr.isEmpty() || password.isEmpty()) {
                showAlert("Please fill out user credentials.");
                return;
            }

            if (idstr.equals(adminId) && password.equals(adminPassword)) {
                openAdminHomePage(adminId);
                return;
            }

            String query = "SELECT * FROM users WHERE userid = ? AND password = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, idstr);
                statement.setString(2, password);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        String userType = resultSet.getString("user_type");

                        switch (userType) {
                            case "Admin":
                                openAdminHomePage(idstr);
                                break;
                            case "Staff":
                                openStaffHomePage(idstr);
                                break;
                            case "Customer":
                                openCustomerHomePage(idstr);
                                break;
                            default:
                                showAlert("Unknown user type.");
                        }
                    } else {
                        showAlert("Invalid ID or password. Please try again.");
                    }
                }catch(SQLException e){
                    logger.severe("Error occurred during login: " + e.getMessage());
                }
            }
        } catch (SQLException e) {
            logger.severe("Error occurred during login: " + e.getMessage());
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    private void openAdminHomePage(String idstr) {
        String url = "jdbc:mysql://127.0.0.1:3306/signup_schema";
        String user = "root";
        String dbPassword = "119904100297";

        try(Connection connection = DriverManager.getConnection(url, user, dbPassword)){
            String selectUserQuery = "SELECT * FROM users WHERE userid = ?";
            PreparedStatement selectStatement = connection.prepareStatement(selectUserQuery);
            selectStatement.setString(1, idstr);
            ResultSet resultSet = selectStatement.executeQuery();

            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a");
            String formattedDateTime = currentDateTime.format(formatter);
            String type = "Admin";

            String insert = "INSERT INTO user_log.user_log (userid, user_type, date_time) " +
                    "VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(insert);

            while(resultSet.next()){
                statement.setString(1, idstr);
                statement.setString(2, type);
                statement.setString(3, formattedDateTime);
            }
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("adminHomePage.fxml"));
                    Parent root = loader.load();

                    // Get the controller instance
                    adminHomePage controller = loader.getController();

                    // Call a method in the AdminHomePageController to set user data
                    controller.setUserData(idstr);

                    Stage stage = (Stage) closeLogInButton.getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    logger.severe("Error occurred during opening adminHomePage " + e.getMessage());
                }
            } else {
                showAlert("Failed.");
            }
        }catch (SQLException e){
            logger.severe("Error occurred during recording userlog " + e.getMessage());
        }
    }

    private void openStaffHomePage(String idstr) {
        String url = "jdbc:mysql://127.0.0.1:3306/signup_schema";
        String user = "root";
        String dbPassword = "119904100297";

        try(Connection connection = DriverManager.getConnection(url, user, dbPassword)){
            String selectUserQuery = "SELECT * FROM users WHERE userid = ?";
            PreparedStatement selectStatement = connection.prepareStatement(selectUserQuery);
            selectStatement.setString(1, idstr);
            ResultSet resultSet = selectStatement.executeQuery();

            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a");
            String formattedDateTime = currentDateTime.format(formatter);
            String type = "Staff";

            String insert = "INSERT INTO user_log.user_log (userid, user_type, date_time) " +
                    "VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(insert);

            while(resultSet.next()){
                statement.setString(1, idstr);
                statement.setString(2, type);
                statement.setString(3, formattedDateTime);
            }
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("staffHomePage.fxml"));
                    Parent root = loader.load();

                    staffHomePage controller = loader.getController();

                    controller.setUserData(idstr);

                    Stage stage = (Stage) closeLogInButton.getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    logger.severe("Error occurred during opening staffHomePage" + e.getMessage());
                }
            } else {
                showAlert("Failed.");
            }
        }catch (SQLException e){
            logger.severe("Error occurred during recording userlog " + e.getMessage());
        }
    }

    private void openCustomerHomePage(String idstr) {
        String url = "jdbc:mysql://127.0.0.1:3306/signup_schema";
        String user = "root";
        String dbPassword = "119904100297";

        try(Connection connection = DriverManager.getConnection(url, user, dbPassword)){
            String selectUserQuery = "SELECT * FROM users WHERE userid = ?";
            PreparedStatement selectStatement = connection.prepareStatement(selectUserQuery);
            selectStatement.setString(1, idstr);
            ResultSet resultSet = selectStatement.executeQuery();

            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a");
            String formattedDateTime = currentDateTime.format(formatter);
            String type = "Customer";

            String insert = "INSERT INTO user_log.user_log (userid, user_type, date_time) " +
                    "VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(insert);

            while(resultSet.next()){
                statement.setString(1, idstr);
                statement.setString(2, type);
                statement.setString(3, formattedDateTime);
            }
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("customerHomePage.fxml"));
                    Parent root = loader.load();

                    customerHomePage controller = loader.getController();

                    controller.setUserData(idstr);

                    Stage stage = (Stage) closeLogInButton.getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    logger.severe("Error occurred during opening customerHomePage " + e.getMessage());
                }
            } else {
                showAlert("Failed.");
            }
        }catch (SQLException e){
            logger.severe("Error occurred during recording userlog " + e.getMessage());
        }
    }

    @FXML
    public void closeLogInButton() {
        Stage stage = (Stage) this.closeLogInButton.getScene().getWindow();
        stage.close();
    }
}
