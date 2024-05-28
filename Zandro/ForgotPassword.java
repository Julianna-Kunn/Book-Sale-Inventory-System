package com.example.system4;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.logging.Logger;

public class ForgotPassword {

    @FXML
    private TextField UserIDTextField;
    @FXML
    private PasswordField PassPasswordField;
    @FXML
    private PasswordField ConfirmPassField;
    @FXML
    private TextField ContactNumberField;
    @FXML
    private TextField EmailField;

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    @FXML
    public void ChangePass() {
        String url = "jdbc:mysql://127.0.0.1:3306/signup_schema";
        DatabaseInformation data = new DatabaseInformation();
        String idstr = UserIDTextField.getText();
        String contactNumber = ContactNumberField.getText();
        String email = EmailField.getText();
        String confirm = ConfirmPassField.getText();
        String password = PassPasswordField.getText();

        try (Connection connection = DriverManager.getConnection(url, data.user, data.password)) {
            String query = "SELECT * FROM users WHERE userid = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, idstr);
                ResultSet result = preparedStatement.executeQuery();

                if (result.next()) {
                    String query2 = "SELECT * FROM users WHERE contact_number = ? AND email = ?";
                    try (PreparedStatement statement = connection.prepareStatement(query2)) {
                        statement.setString(1, contactNumber);
                        statement.setString(2, email);
                        ResultSet resultSet = statement.executeQuery();

                        if (resultSet.next()) {

                            if (!password.equals(confirm)) {
                                showAlert("Passwords do not match. Please re-enter.");
                                return;
                            }

                            if (!isValidPassword(password)) {
                                showAlert("Password must be at least 8 characters long, and include a lowercase letter, an uppercase letter, a digit, and a special character.");
                                return;
                            }

                            String update = "UPDATE users SET password = ? WHERE userid = ?";
                            try (PreparedStatement updateStatement = connection.prepareStatement(update)) {
                                updateStatement.setString(1, password);
                                updateStatement.setString(2, idstr);

                                int rowsAffected = updateStatement.executeUpdate();
                                if (rowsAffected > 0) {
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    String userInfo = "Password changed successfully";
                                    alert.setContentText(userInfo);
                                    alert.showAndWait();
                                } else {
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    String userInfo = "Failed to Change Password successfully";
                                    alert.setContentText(userInfo);
                                    alert.showAndWait();
                                }
                                UserIDTextField.clear();
                                ContactNumberField.clear();
                                EmailField.clear();
                                PassPasswordField.clear();
                                ConfirmPassField.clear();
                            }
                        } else {
                            showAlert("Contact Number or Email do not match. Please try again.");
                        }
                    }
                } else {
                    showAlert("User ID do not match. Please try again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    private boolean isValidPassword(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return password.matches(regex);
    }

    @FXML
    public void BackButtonOnAction(ActionEvent event) {
        URL fxmlResource = getClass().getResource("Log-in.fxml");

        if (fxmlResource == null) {
            logger.severe("FXML resource Login.fxml' not found");
            return;
        }
        try {
            Parent root = FXMLLoader.load(fxmlResource);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            logger.severe("Error loading Login.fxml: " + e.getMessage());
        }
    }
}
