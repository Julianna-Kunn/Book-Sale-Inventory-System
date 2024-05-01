package com.example.system4;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.util.Duration;

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


public class CustomerSignUpController {

    @FXML
    private Button closeSignUpButton;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField contactNumberTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private TextField confiPasswordTextField;
    @FXML
    private Label dateLabel;

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    @FXML
    public void BackButtonOnAction(ActionEvent event) {
        URL fxmlResource = getClass().getResource("MainInterface.fxml");

        if (fxmlResource == null) {
            // Handle the case where the FXML resource is not found
            logger.severe("FXML resource 'MainInterface.fxml' not found");
            return;
        }
        try {
            // Load the FXML resource using the URL
            Parent root = FXMLLoader.load(fxmlResource);

            // Get the stage from the event source (the button)
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the scene with the loaded FXML root
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            logger.severe("Error loading MainInterface.fxml: " + e.getMessage());
        }
    }

    @FXML
    public void signUpButton() {
        String url = "jdbc:mysql://127.0.0.1:3306/signup_schema";
        String user = "root";
        String dbPassword = "119904100297";

        try (Connection connection = DriverManager.getConnection(url, user, dbPassword)) {
            String name = nameTextField.getText();
            String email = emailTextField.getText();
            String contactNumberStr = contactNumberTextField.getText();
            String password = passwordTextField.getText();
            String confirm = confiPasswordTextField.getText();

            if (!password.equals(confirm)) {
                showAlert("Passwords do not match. Please re-enter.");
            }

            if (name.isEmpty() || email.isEmpty() || contactNumberStr.isEmpty() || password.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Please fill out user credentials.");
                alert.showAndWait();
            }
            long contact_number;
            try {
                contact_number = Long.parseLong(contactNumberStr);
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Please enter a valid contact number.");
                alert.showAndWait();
                return;
            }

            int userId = generateUserId();
            String userType = "Customer";

            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formattedDateTime = currentDateTime.format(formatter);

            String insertSQL = "INSERT INTO users (userid, user_type, name, email, contact_number, password, date_time) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(insertSQL);
            statement.setInt(1, userId);
            statement.setString(2, userType);
            statement.setString(3, name);
            statement.setString(4, email);
            statement.setLong(5, contact_number);
            statement.setString(6, password);
            statement.setString(7, formattedDateTime);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                String userInfo = "User ID: " + userId + "\n"
                        + "Name: " + name + "\n"
                        + "Email: " + email + "\n"
                        + "Contact number: " + contact_number;
                alert.setContentText(userInfo);
                alert.showAndWait();
            } else {
                showAlert("Failed to register user.");
            }
        } catch (SQLException e) {
            logger.severe("Error Signing Up an Account " + e.getMessage());
        }
    }

    private int generateUserId() {
        int maxId = getMaxUserId() + 1;

        while (isUserIdExists(maxId)) {
            maxId++;
        }
        return maxId;
    }

    private int getMaxUserId() {
        int maxId = 0;
        String url = "jdbc:mysql://127.0.0.1:3306/signup_schema";
        String user = "root";
        String dbPassword = "119904100297";

        try (Connection connection = DriverManager.getConnection(url, user, dbPassword)) {
            String sql = "SELECT MAX(userid) FROM users";
            PreparedStatement selectionStatement = connection.prepareStatement(sql);
            ResultSet result = selectionStatement.executeQuery();

            if (result.next()) {
                maxId = result.getInt(1);
            }
        } catch (SQLException e) {
            logger.severe("Error Database " + e.getMessage());
        }
        return maxId;
    }

    private boolean isUserIdExists(int userId) {
        String url = "jdbc:mysql://127.0.0.1:3306/signup_schema";
        String user = "root";
        String dbPassword = "119904100297";

        try (Connection connection = DriverManager.getConnection(url, user, dbPassword)) {
            String sql = "SELECT COUNT(userid) FROM users WHERE userid = ?";
            PreparedStatement selectionStatement = connection.prepareStatement(sql);
            selectionStatement.setInt(1, userId);
            ResultSet result = selectionStatement.executeQuery();

            if (result.next()) {
                int count = result.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            logger.severe("Error Database " + e.getMessage());
        }
        return false;
    }
    @FXML
    private void initialize() {
        updateDateLabel();
    }

    public void updateDateLabel() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), _ -> {
                    LocalDateTime currentDateTime = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                    String formattedDateTime = currentDateTime.format(formatter);
                    dateLabel.setText(formattedDateTime);
                })
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void closeSignUpButton() {
        Stage stage = (Stage) closeSignUpButton.getScene().getWindow();
        stage.close();
    }
}
