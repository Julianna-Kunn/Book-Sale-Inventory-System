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
    private Button closeUserUpdateButton;
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
        update();
    }

    public void update() {
        String url = "jdbc:mysql://127.0.0.1:3306/signup_schema";
        String user = "root";
        String dbPassword = "10141996";

        try (Connection connection = DriverManager.getConnection(url, user, dbPassword)) {
            String idstr = userIdLabel.getText();

            String selectUserQuery = "SELECT * FROM users WHERE userid = ?";
            PreparedStatement selectStatement = connection.prepareStatement(selectUserQuery);
            selectStatement.setString(1, idstr);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                String name = nameTextField.getText();
                String email = emailTextField.getText();
                String contactNumberStr = contactNumberTextField.getText();
                String password = passwordPasswordField.getText();
                String confirm = confiPasswordPasswordField.getText();
                String userType = resultSet.getString("user_type");

                if (!password.equals(confirm)) {
                    showAlert("Passwords do not match. Please re-enter.");
                    return;
                }

                long contact_number;
                try {
                    contact_number = Long.parseLong(contactNumberStr);
                } catch (NumberFormatException e) {
                    logger.severe("Please enter a valid contact number " + e.getMessage());
                    return;
                }

                LocalDateTime currentDateTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a");
                String formattedDateTime = currentDateTime.format(formatter);

                // Update the user record with the new values
                String updateSQL = "UPDATE users SET name = ?, email = ?, contact_number = ?, password = ?, updated = ?, userid = ? WHERE userid = ?";
                PreparedStatement updateStatement = connection.prepareStatement(updateSQL);

                updateStatement.setString(1, name);
                updateStatement.setString(2, email);
                updateStatement.setLong(3, contact_number);
                updateStatement.setString(4, password);
                updateStatement.setString(5, formattedDateTime);
                updateStatement.setString(6, idstr);
                updateStatement.setString(7, idstr);
                int rowsAffected = updateStatement.executeUpdate();

                if (rowsAffected > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    String userInfo = "User ID: " + idstr + "\n"
                            + "User Type: " + userType + "\n"
                            + "Name: " + name + "\n"
                            + "Email: " + email + "\n"
                            + "Contact number: " + contact_number;
                    alert.setHeaderText("Successfully Updated User");
                    alert.setContentText(userInfo);
                    alert.showAndWait();
                } else {
                    showAlert("Unable to update user, Please try again.");
                }
            } else {
                showAlert("User ID not found, Please provide a valid user ID.");
            }
        } catch (SQLException e) {
            logger.severe("Error Updating an Account: " + e.getMessage());
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

    public void Home(ActionEvent event){
        String idstr = userIdLabel.getText();

        String url = "jdbc:mysql://127.0.0.1:3306/signup_schema";
        String user = "root";
        String dbPassword = "10141996";

        try(Connection connection = DriverManager.getConnection(url, user, dbPassword)){
            String userid = "SELECT * FROM users WHERE userid = ?";

            PreparedStatement selectionStatement = connection.prepareStatement(userid);
            selectionStatement.setString(1, idstr);
            ResultSet result = selectionStatement.executeQuery();

            while(result.next()){
                String storedUserType = result.getString("user_type");
                if(storedUserType.equals("Admin")){
                    openAdminHome(event);
                }else if(storedUserType.equals("Staff")){
                    openStaffHome(event);
                }else if(storedUserType.equals("Customer")){
                    openCustomerHome(event);
                }
            }
        }catch(SQLException e){
            logger.severe("Error occurred during Selection" + e.getMessage());
        }
    }

    @FXML
    public void openAdminHome(ActionEvent event) {
        String userid = userIdLabel.getText();
        try{
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
        try{
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
        try{
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

    public void closeUserUpdate() {
        Stage stage = (Stage) this.closeUserUpdateButton.getScene().getWindow();
        stage.close();
    }
}
