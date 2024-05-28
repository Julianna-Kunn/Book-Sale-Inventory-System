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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

public class AdminSignUp {
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
    @FXML
    private ImageView Profile;
    private File selectedImageFile;
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public void setUserData(String idstr) {
        userIdLabel.setText(idstr);
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
            String userType = userTypeChoice.getValue();

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
                showAlert("Invalid email. Please include '@' in the email address.");
            }

            if (name.isEmpty() || email.isEmpty() || contactNumberStr.isEmpty() || password.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Please fill out user credentials.");
                alert.showAndWait();
                return;
            }

            String Idstr = generateUserId(userType);

            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a");
            String formattedDateTime = currentDateTime.format(formatter);

            String insertSQL = "INSERT INTO users (userid, user_type, name, email, contact_number, password, date_time) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(insertSQL);

            statement.setString(1, Idstr);
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
                String userInfo = "User ID: " + Idstr + "\n"
                        + "User Type:" + userType + "\n"
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
                Profile.setImage(null);
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

    private boolean isValidPassword(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return password.matches(regex);
    }

    public boolean isValidEmail(String email) {
        return email.contains("@");
    }

    @FXML
    private void selectImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        selectedImageFile = fileChooser.showOpenDialog(null);

        if (selectedImageFile != null) {
            Image image = new Image(selectedImageFile.toURI().toString());
            Profile.setImage(image);
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
        if (userType.equals("Admin")) {
            return "A-";
        } else if (userType.equals("Staff")) {
            return "S-";
        } else if (userType.equals("Customer")) {
            return "C-";
        } else {
            throw new IllegalArgumentException("Invalid user type: " + userType);
        }
    }

    private int getMaxUserId(String prefix) {
        String url = "jdbc:mysql://127.0.0.1:3306/signup_schema";
        DatabaseInformation data = new DatabaseInformation();

        int maxId = 0;
        String sql = "SELECT MAX(CAST(SUBSTRING(userid, LOCATE('-', userid) + 1) AS UNSIGNED)) FROM users WHERE userid LIKE ?";
        try (Connection connection = DriverManager.getConnection(url, data.user, data.password);
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

    public void adminInfo(ActionEvent event) {
        String idstr = userIdLabel.getText();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UserInfo.fxml"));
            Parent root = loader.load();

            UserInfo controller = loader.getController();

            controller.setUserData(idstr);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            logger.severe("Error occurred during opening adminInfo " + e.getMessage());
        }
    }

    @FXML
    public void BackButtonDashboard(ActionEvent event) {
        String idstr = userIdLabel.getText();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminHomePage.fxml"));
            Parent root = loader.load();

            // Get the controller instance of adminHomePageController
            AdminHomePage controller = loader.getController();

            // Pass user data back to adminHomePageController
            controller.setUserData(idstr);

            // Show the adminHomePage scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            logger.severe("Error loading AdminHomePage.fxml: " + e.getMessage());
        }
    }

    public void userLogButton(ActionEvent event) {
        String idstr = userIdLabel.getText();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UserLog.fxml"));
            Parent root = loader.load();

            UserLog controller = loader.getController();

            controller.setUserData(idstr);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            logger.severe("Error occurred during opening adminSubPage " + e.getMessage());
        }
    }
    @FXML
    public void addBooksButton(ActionEvent event) {
        String idstr = userIdLabel.getText();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddInvoice.fxml"));
            Parent root = loader.load();

            AddBooksInvoice controller = loader.getController();

            controller.setUserData(idstr);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            logger.severe("Error occurred during opening adminSubPage " + e.getMessage());
        }
    }

    @FXML
    public void viewBooksButton(ActionEvent event) {
        String idstr = userIdLabel.getText();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewBooks.fxml"));
            Parent root = loader.load();

            ViewBooks controller = loader.getController();

            controller.setUserData(idstr);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            logger.severe("Error occurred during opening adminSubPage " + e.getMessage());
        }
    }

    @FXML
    public void SuppliersButton(ActionEvent event) {
        String idstr = userIdLabel.getText();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddSupplier.fxml"));
            Parent root = loader.load();

            AddSupplier controller = loader.getController();

            controller.setUserData(idstr);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            logger.severe("Error occurred during opening adminSubPage " + e.getMessage());
        }
    }

    @FXML
    public void UpdateInfoBook(ActionEvent event) {
        String idstr = userIdLabel.getText();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewUpdateBooksInfo.fxml"));
            Parent root = loader.load();

            ViewUpdateInfoBook controller = loader.getController();

            controller.setUserData(idstr);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            logger.severe("Error occurred during opening adminSubPage " + e.getMessage());
        }
    }

    @FXML
    public void ViewTransac(ActionEvent event) {
        String idstr = userIdLabel.getText();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewTransacHistory.fxml"));
            Parent root = loader.load();

            ViewTransacHistory controller = loader.getController();

            controller.setUserData(idstr);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            logger.severe("Error occurred during opening adminSubPage " + e.getMessage());
        }
    }
}

