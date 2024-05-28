package com.example.system4;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

public class CustomerSignUp {
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
    private Label useridLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private ImageView Img_photo;

    private static final Logger logger = Logger.getLogger(Main.class.getName());
    private File selectedImageFile;

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
        DatabaseInformation data = new DatabaseInformation();

        try (Connection connection = DriverManager.getConnection(url, data.user, data.password)) {
            String name = nameTextField.getText();
            String email = emailTextField.getText();
            String contactNumberStr = contactNumberTextField.getText();
            String password = passwordPasswordField.getText();
            String confirm = confiPasswordPasswordField.getText();


            if (!password.equals(confirm)) {
                showAlert("Passwords do not match. Please re-enter.");
                return;
            }

            if (!isValidPassword(password)) {
                showAlert("Password must be at least 8 characters long, and include a lowercase letter, an uppercase letter, a digit, and a special character.");
                return;
            }

            if (isValidEmail(email)) {
                // Email is valid
            } else {
                showAlert("Invalid Email Address");
            }

            if (name.isEmpty() || email.isEmpty() || contactNumberStr.isEmpty() || password.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Please fill out user credentials.");
                alert.showAndWait();
                return;
            }

            int userId = generateUserId();
            String userType = "Customer";
            String IdStr = "C-" + userId;

            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy / hh:mm:ss a");
            String formattedDateTime = currentDateTime.format(formatter);

            String insertSQL = "INSERT INTO users (userid, user_type, name, email, contact_number, password, date_time, profile) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(insertSQL);
            statement.setString(1, IdStr);
            statement.setString(2, userType);
            statement.setString(3, name);
            statement.setString(4, email);
            statement.setString(5, contactNumberStr);
            statement.setString(6, password);
            statement.setString(7, formattedDateTime);

            if (selectedImageFile != null) {
                FileInputStream fis = new FileInputStream(selectedImageFile);
                statement.setBinaryStream(8, fis, (int) selectedImageFile.length());
            } else {
                statement.setNull(8, Types.BLOB);
            }

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                String userInfo = "User ID: " + IdStr + "\n"
                        + "Name: " + name + "\n"
                        + "Email: " + email + "\n"
                        + "Contact number: " + contactNumberStr;
                alert.setHeaderText("Successfully Registered User");
                alert.setContentText(userInfo);
                alert.showAndWait();
                nameTextField.clear();
                emailTextField.clear();
                contactNumberTextField.clear();
                passwordPasswordField.clear();
                confiPasswordPasswordField.clear();
                Img_photo.setImage(null);
                selectedImageFile = null;
            } else {
                showAlert("Failed to register user.");
            }
        } catch (SQLException e) {
            logger.severe("Error Signing Up an Account " + e.getMessage());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isValidEmail(String email) {
        return email.contains("@");
    }

    private boolean isValidPassword(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return password.matches(regex);
    }

    @FXML
    private void selectImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        selectedImageFile = fileChooser.showOpenDialog(null);

        if (selectedImageFile != null) {
            Image image = new Image(selectedImageFile.toURI().toString());
            Img_photo.setImage(image);
        }
    }

    private void setText() {
        int userId = generateUserId();
        useridLabel.setText("C-" + userId);
    }

    private int generateUserId() {
        int maxId = getMaxUserId("C-");
        // If maxId is less than 1000, start from 1000
        if (maxId < 3000) {
            maxId = 3000;
        }

        // Increment the maxId to get the next available user ID
        int userId = maxId + 1;

        return userId;
    }

    private int getMaxUserId(String prefix) {
        int maxId = 0;
        String url = "jdbc:mysql://127.0.0.1:3306/signup_schema";
        DatabaseInformation data = new DatabaseInformation();

        try (Connection connection = DriverManager.getConnection(url, data.user, data.password)) {
            String sql = "SELECT MAX(CAST(SUBSTRING(userid, LOCATE('-', userid) + 1) AS UNSIGNED)) FROM users WHERE userid LIKE ?";
            PreparedStatement selectionStatement = connection.prepareStatement(sql);
            selectionStatement.setString(1, prefix + "%");
            ResultSet result = selectionStatement.executeQuery();

            if (result.next()) {
                maxId = result.getInt(1);
            }
        } catch (SQLException e) {
            logger.severe("Error Database " + e.getMessage());
        }
        return maxId;
    }

    @FXML
    private void initialize() {
        updateDateLabel();
        setText();
    }

    public void updateDateLabel() {
        EventHandler<ActionEvent> eventHandler = event -> {
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy / hh:mm:ss a");
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
}
