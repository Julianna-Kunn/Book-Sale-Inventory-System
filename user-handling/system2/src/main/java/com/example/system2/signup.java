package com.example.system2;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class signup {

    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField contactNumberField;
    @FXML
    private PasswordField passwordField;

    @FXML
    public void registerUser() {
        String name = nameField.getText();
        String email = emailField.getText();
        String contactNumberStr = contactNumberField.getText();
        String password = passwordField.getText();

        // Validate input fields
        if (name.isEmpty() || email.isEmpty() || contactNumberStr.isEmpty() || password.isEmpty()) {
            showAlert("Please fill out all fields.");
            return;
        }

        try {
            int contactNumber = Integer.parseInt(contactNumberStr);

            // Generate unique user ID
            int userId = generateUserId();

            // Insert user data into the database
            insertUserData(userId, name, email, contactNumber, password);

        } catch (NumberFormatException e) {
            showAlert("Invalid contact number format.");
        }
    }

    private void insertUserData(int userId, String name, String email, int contactNumber, String password) {
        String url = "jdbc:mysql://127.0.0.1:3306/signup_schema";
        String user = "root";
        String dbPassword = "119904100297";

        try (Connection connection = DriverManager.getConnection(url, user, dbPassword)) {
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
            statement.setInt(5, contactNumber);
            statement.setString(6, password);
            statement.setString(7, formattedDateTime);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                showAlert("User registered successfully!");
            } else {
                showAlert("Failed to register user.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error occurred during user registration.");
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

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

