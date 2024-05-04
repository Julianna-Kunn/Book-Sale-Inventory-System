package com.example.system4;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

public class adminSignUp {
    @FXML
    private Button closeAdminSignUpButton;
    @FXML
    private Label userIdLabel;
    @FXML
    private ComboBox<String> userTypeChoice;
    private final String[] userType = {"Admin", "Staff", "Customer"};
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField contactNumberTextField;
    @FXML
    private PasswordField passwordPasswordField;
    @FXML
    private PasswordField confiPasswordPasswordField;
    @FXML
    private Label dateLabel;

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public void setUserData(String idstr) {
        userIdLabel.setText(idstr);
    }

    @FXML
    public void signUpButton() {
        String url = "jdbc:mysql://127.0.0.1:3306/signup_schema";
        String user = "root";
        String dbPassword = "10141996";

        try (Connection connection = DriverManager.getConnection(url, user, dbPassword)) {
            String name = nameTextField.getText();
            String email = emailTextField.getText();
            String contactNumberStr = contactNumberTextField.getText();
            String password = passwordPasswordField.getText();
            String confirm = confiPasswordPasswordField.getText();
            String userType = userTypeChoice.getValue();

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

            String Idstr = generateUserId(userType);

            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a");
            String formattedDateTime = currentDateTime.format(formatter);

            String insertSQL = "INSERT INTO users (userid, user_type, name, email, contact_number, password, date_time, updated) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(insertSQL);

            statement.setString(1, Idstr);
            statement.setString(2, userType);
            statement.setString(3, name);
            statement.setString(4, email);
            statement.setLong(5, contact_number);
            statement.setString(6, password);
            statement.setString(7, formattedDateTime);
            statement.setString(8, formattedDateTime);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                String userInfo = "User ID: " + Idstr + "\n"
                        + "User Type:" + userType + "\n"
                        + "Name: " + name + "\n"
                        + "Email: " + email + "\n"
                        + "Contact number: " + contact_number;
                alert.setHeaderText("Successfully Registered User");
                alert.setContentText(userInfo);
                alert.showAndWait();
            } else {
                showAlert("Failed to register user.");
            }
        } catch (SQLException e) {
            logger.severe("Error Signing Up an Account " + e.getMessage());
        }
    }

    private String generateUserId(String userType) {
        String prefix = prefix(userType);
        int maxId = getMaxUserId(prefix);

        // Determine the minimum id based on userType
        int minimumId;
        switch (userType) {
            case "Admin":
                minimumId = 1000;
                break;
            case "Staff":
                minimumId = 2000;
                break;
            case "Customer":
                minimumId = 3000;
                break;
            default:
                throw new IllegalArgumentException("Invalid user type: " + userType);
        }
        // If maxId is less than the minimumId, set it to the minimumId
        if (maxId < minimumId) {
            maxId = minimumId;
        }
        // Increment the maxId to get the next available user ID
        int userId = maxId + 1;
        // Construct the user ID with prefix and incremented userId
        return prefix + userId;
    }

    private String prefix(String userType) {
        if(userType.equals("Admin")){
            return "A-";
        }else if(userType.equals("Staff")){
            return "S-";
        }else if(userType.equals("Customer")){
            return "C-";
        }else{
            throw new IllegalArgumentException("Invalid user type: " + userType);
        }
    }

    private int getMaxUserId(String prefix) {
        String url = "jdbc:mysql://127.0.0.1:3306/signup_schema";
        String user = "root";
        String dbPassword = "10141996";

        int maxId = 0;
        String sql = "SELECT MAX(CAST(SUBSTRING(userid, LOCATE('-', userid) + 1) AS UNSIGNED)) FROM users WHERE userid LIKE ?";
        try (Connection connection = DriverManager.getConnection(url, user, dbPassword);
             PreparedStatement selectionStatement = connection.prepareStatement(sql)) {
            selectionStatement.setString(1, prefix + "%");
            try (ResultSet result = selectionStatement.executeQuery()) {
                if (result.next()) {
                    maxId = result.getInt(1);
                }
            }
        } catch (SQLException e) {
            logger.severe("Error Database " + e.getMessage());
        }
        return maxId;
    }

    @FXML
    private void initialize() {
        userTypeChoice.getItems().addAll(userType);
        updateDateLabel();
    }

    public void updateDateLabel() {
        EventHandler<ActionEvent> eventHandler = event -> {
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a");
            String formattedDateTime = currentDateTime.format(formatter);
            dateLabel.setText(formattedDateTime);
        };

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), eventHandler)
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void BackButtonOnAction(ActionEvent event) {
        String idstr = userIdLabel.getText();
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("adminHomePage.fxml"));
            Parent root = loader.load();

            // Get the controller instance of adminHomePageController
            adminHomePage controller = loader.getController();

            // Pass user data back to adminHomePageController
            controller.setUserData(idstr);

            // Show the adminHomePage scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            logger.severe("Error loading adminHomePage.fxml: " + e.getMessage());
        }
    }

    public void closeAdminSignUpButton() {
        Stage stage = (Stage) closeAdminSignUpButton.getScene().getWindow();
        stage.close();
    }
}

