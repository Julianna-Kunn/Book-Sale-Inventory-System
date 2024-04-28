package com.example.system2;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.sql.*;

public class login {

    @FXML
    private TextField idField;

    @FXML
    private TextField passwordField;

    @FXML
    public void login() {
        String id = idField.getText();
        String password = passwordField.getText();
        String url = "jdbc:mysql://127.0.0.1:3306/signup_schema";
        String user = "root";
        String dbPassword = "119904100297";

        try (Connection connection = DriverManager.getConnection(url, user, dbPassword)){

            String query = "SELECT * FROM users WHERE userid = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, id);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String userType = resultSet.getString("user_type");
                String userName = resultSet.getString("name");

                // Handle login based on user type
                switch (userType) {
                    case "Admin":
                        adminPage(id, userType, userName);
                        break;
                    case "Staff":
                        staffPage(id, userType, userName);
                        break;
                    case "Customer":
                        customerPage(id, userType, userName);
                        break;
                    default:
                        System.out.println("Unknown user type.");
                        break;
                }

                System.out.println("Login successful!");
            } else {
                showAlert("Invalid ID or password. Please try again.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error occurred during login.");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void adminPage(String id, String userType, String userName) {
        // Implement admin page navigation or logic
        System.out.println("Welcome, Admin " + userName);
    }

    private void staffPage(String id, String userType, String userName) {
        // Implement staff page navigation or logic
        System.out.println("Welcome, Staff " + userName);
    }

    private void customerPage(String id, String userType, String userName) {
        // Implement customer page navigation or logic
        System.out.println("Welcome, Customer " + userName);
    }
}

