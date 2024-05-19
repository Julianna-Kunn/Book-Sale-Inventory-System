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

public class userUpdate {

    @FXML
    private Label userIdLabel;
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
        setText();
    }

    public void setText(){
        String url = "jdbc:mysql://127.0.0.1:3306/signup_schema";
        String user = "root";
        String dbPassword = "119904100297";
        String idstr = userIdLabel.getText();

        String selectUserSQL = "SELECT * FROM users WHERE userid = ?";
        try(Connection connection = DriverManager.getConnection(url, user, dbPassword)){
            PreparedStatement selectStatement = connection.prepareStatement(selectUserSQL);
            selectStatement.setString(1, idstr);
            ResultSet resultSet = selectStatement.executeQuery();

            if(resultSet.next()){
                String OldName = resultSet.getString("name");
                String OldEmail = resultSet.getString("email");
                String OldContactNumber = resultSet.getString("contact_number");
                String OldPassword = resultSet.getString("password");

                nameTextField.setText(OldName);
                emailTextField.setText(OldEmail);
                contactNumberTextField.setText(OldContactNumber);
                passwordPasswordField.setText(OldPassword);
            }
        }catch(SQLException e){
            showAlert("User ID not found, Please provide a valid user ID.");
        }
    }

    public void UserUpdate() {
        String historyUrl = "jdbc:mysql://127.0.0.1:3306/account_history";
        String url = "jdbc:mysql://127.0.0.1:3306/signup_schema";
        String user = "root";
        String dbPassword = "119904100297";
        String idstr = userIdLabel.getText();

        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a");
        String formattedDateTime = currentDateTime.format(formatter);

        try {
            // Fetch existing user data from users database
            String selectUserSQL = "SELECT * FROM users WHERE userid = ?";
            try (Connection connection = DriverManager.getConnection(url, user, dbPassword);
                 PreparedStatement selectStatement = connection.prepareStatement(selectUserSQL)) {

                selectStatement.setString(1, idstr);
                ResultSet resultSet = selectStatement.executeQuery();

                if (resultSet.next()) {
                    String OldName = resultSet.getString("name");
                    String OldEmail = resultSet.getString("email");
                    String OldContactNumber = resultSet.getString("contact_number");
                    String OldPassword = resultSet.getString("password");
                    String userType = resultSet.getString("user_type");

                    // Update user information in users database
                    String NewName = nameTextField.getText();
                    String NewEmail = emailTextField.getText();
                    String NewContact_Number = contactNumberTextField.getText();
                    String NewPass = passwordPasswordField.getText();
                    String confi = confiPasswordPasswordField.getText();

                    if (!NewPass.equals(confi)) {
                        showAlert("Confirm Password Does Not Match or Please Confirm Password");
                        return;
                    }

                    // Only proceed if all fields are filled
                    if (NewName.isEmpty() || NewEmail.isEmpty() || NewContact_Number.isEmpty() || NewPass.isEmpty()){
                        showAlert("Please Fill out all fields");
                        return;
                    }

                    // Store fetched user data in account_history database
                    try (Connection historyConnection = DriverManager.getConnection(historyUrl, user, dbPassword)) {
                        String insertSQL = "INSERT INTO account_history.account_activitylog (userid, user_type, name, email, contact_number, password, updated) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?)";
                        PreparedStatement statement = historyConnection.prepareStatement(insertSQL);

                        statement.setString(1, idstr);
                        statement.setString(2, userType);
                        statement.setString(3, OldName);
                        statement.setString(4, OldEmail);
                        statement.setString(5, OldContactNumber);
                        statement.setString(6, OldPassword);
                        statement.setString(7, formattedDateTime);

                        int rowsInserted = statement.executeUpdate();

                        if (rowsInserted > 0) {
                            System.out.println("Successfully inserted into account_history");
                        } else {
                            System.out.println("Insert into account_history failed");
                            return; // Abort operation if insertion into history fails
                        }
                    } catch (SQLException e) {
                        logger.severe("Error occurred during inserting into account_history: " + e.getMessage());
                        return; // Abort operation if an error occurs
                    }

                    // Update user information in users database
                    String updateSQL = "UPDATE users SET name = ?, email = ?, contact_number = ?, password = ?, date_time = ? WHERE userid = ?";
                    try (PreparedStatement updateStatement = connection.prepareStatement(updateSQL)) {
                        updateStatement.setString(1, NewName);
                        updateStatement.setString(2, NewEmail);
                        updateStatement.setString(3, NewContact_Number);
                        updateStatement.setString(5, formattedDateTime);
                        updateStatement.setString(6, idstr);

                        int rowsAffected = updateStatement.executeUpdate();

                        if (rowsAffected > 0) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            String userInfo = "User ID: " + idstr + "\n"
                                    + "Name: " + NewName + "\n"
                                    + "Email: " + NewEmail + "\n"
                                    + "Contact number: " + NewContact_Number;
                            alert.setHeaderText("Successfully Updated User");
                            alert.setContentText(userInfo);
                            alert.showAndWait();
                        } else {
                            showAlert("Unable to update user, Please try again.");
                        }
                    }
                } else {
                    showAlert("User ID not found, Please provide a valid user ID.");
                }
            } catch (SQLException e) {
                logger.severe("Error fetching/updating user data: " + e.getMessage());
            }
        } catch (Exception e) {
            logger.severe("General error during user update: " + e.getMessage());
        }
    }

    @FXML
    private void initialize() {
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

    public void Home(ActionEvent event) {
        String idstr = userIdLabel.getText();

        String url = "jdbc:mysql://127.0.0.1:3306/signup_schema";
        String user = "root";
        String dbPassword = "119904100297";

        try (Connection connection = DriverManager.getConnection(url, user, dbPassword)) {
            String userid = "SELECT * FROM users WHERE userid = ?";

            PreparedStatement selectionStatement = connection.prepareStatement(userid);
            selectionStatement.setString(1, idstr);
            ResultSet result = selectionStatement.executeQuery();

            while (result.next()) {
                String storedUserType = result.getString("user_type");
                if (storedUserType.equals("Admin")) {
                    openAdminHome(event);
                } else if (storedUserType.equals("Staff")) {
                    openStaffHome(event);
                } else if (storedUserType.equals("Customer")) {
                    openCustomerHome(event);
                }
            }
        } catch (SQLException e) {
            logger.severe("Error occurred during Selection" + e.getMessage());
        }
    }

    @FXML
    public void openAdminHome(ActionEvent event) {
        String userid = userIdLabel.getText();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("adminHomePage.fxml"));
            Parent root = loader.load();

            // Get the controller instance of adminHomePageController
            adminHomePage controller = loader.getController();

            // Pass user data back to adminHomePageController
            controller.setUserData(userid);

            // Show the adminHomePage scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            logger.severe("Error loading adminHomePage.fxml: " + e.getMessage());
        }
    }

    @FXML
    public void openStaffHome(ActionEvent event) {
        String userid = userIdLabel.getText();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("staffHomePage.fxml"));
            Parent root = loader.load();

            staffHomePage controller = loader.getController();

            controller.setUserData(userid);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            logger.severe("Error loading staffHomePage.fxml: " + e.getMessage());
        }
    }

    @FXML
    public void openCustomerHome(ActionEvent event) {
        String userid = userIdLabel.getText();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("customerHomePage.fxml"));
            Parent root = loader.load();

            customerHomePage controller = loader.getController();

            controller.setUserData(userid);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            logger.severe("Error loading customerHomePage.fxml: " + e.getMessage());
        }
    }
}
