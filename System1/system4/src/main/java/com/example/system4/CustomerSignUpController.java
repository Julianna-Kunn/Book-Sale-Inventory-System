package com.example.system4;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class CustomerSignUpController {

    @FXML
    private Button closeSignUpButton;
    @FXML
    private Button backButton;
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
    private Button signUpButton;
    @FXML
    private Label dateLabel;
    @FXML
    private Timeline timeline;

    @FXML
    public void BackButtonOnAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainInterface.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 1000, 600));
            stage.show();

            Stage loginStage = (Stage) closeSignUpButton.getScene().getWindow();
            loginStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void signUpButton(ActionEvent event) {
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
                return;
            }

            if (name.isEmpty() || email.isEmpty() || contactNumberStr.isEmpty() || password.isEmpty()) {
                showAlert("Please fill out user credentials.");
                return;
            }

            int contact_number;
            try {
                contact_number = Integer.parseInt(contactNumberStr);
            } catch (NumberFormatException e) {
                showAlert("Please enter a valid contact number.");
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
            statement.setInt(5, contact_number);
            statement.setString(6, password);
            statement.setString(7, formattedDateTime);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                showInformation("User registered successfully!");
            } else {
                showAlert("Failed to register user.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database connection error.");
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return false;
    }
    @FXML
    private void initialize() {
        updateDateLabel();
    }

    public void updateDateLabel() {
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
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

    private void showInformation(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void closeSignUpButton(ActionEvent event) {
        Stage stage = (Stage) closeSignUpButton.getScene().getWindow();
        stage.close();
    }

}
