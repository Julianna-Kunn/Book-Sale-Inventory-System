package com.example.system4;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.logging.Logger;

public class userInfo {
    @FXML
    private Label userIdLabel;
    @FXML
    private Label userTypeLabel;
    @FXML
    private Label userNameLabel;
    @FXML
    private Label userEmailLabel;
    @FXML
    private Label userContactNumberLabel;
    @FXML
    private Label userPasswordLabel;
    @FXML
    private Label userDateLabel;
    @FXML
    private Button closeUserInfo;

    private static final Logger logger = Logger.getLogger(Main.class.getName());


    public void setUserData(String idstr) {
        userIdLabel.setText(idstr);
        setText();
    }

    public void setText(){
        String idstr = userIdLabel.getText();

        String url = "jdbc:mysql://127.0.0.1:3306/signup_schema";
        String user = "root";
        String dbPassword = "119904100297";

        try(Connection connection = DriverManager.getConnection(url, user, dbPassword)){
            String userid = "SELECT * FROM users WHERE userid = ?";

            PreparedStatement selectionStatement = connection.prepareStatement(userid);
            selectionStatement.setString(1, idstr);
            ResultSet result = selectionStatement.executeQuery();

            while(result.next()){
                String storedUserType = result.getString("user_type");
                String storedName = result.getString("name");
                String storedEmail = result.getString("email");
                String storedContact_Number = result.getString("contact_number");
                String storedPassword = result.getString("password");
                String storedDate = result.getString("date_time");

                userTypeLabel.setText(storedUserType);
                userNameLabel.setText(storedName);
                userEmailLabel.setText(storedEmail);
                userContactNumberLabel.setText(storedContact_Number);
                userPasswordLabel.setText(storedPassword);
                userDateLabel.setText(storedDate);
            }
        }catch(SQLException e){
            logger.severe("Error occurred during Setting Names" + e.getMessage());
        }
    }

    public void Update(ActionEvent event){
        String idstr = userIdLabel.getText();
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("userUpdate.fxml"));
            Parent root = loader.load();

            // Get the controller instance of adminHomePageController
            userUpdate controller = loader.getController();

            // Pass user data back to adminHomePageController
            controller.setUserData(idstr);

            // Show the adminHomePage scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            logger.severe("Error loading userUpdate.fxml: " + e.getMessage());
        }
    }

    public void Back(ActionEvent event){
        String idstr = userIdLabel.getText();

        String url = "jdbc:mysql://127.0.0.1:3306/signup_schema";
        String user = "root";
        String dbPassword = "119904100297";

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

    @FXML
    public void openStaffHome(ActionEvent event) {
        String idstr = userIdLabel.getText();
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("staffHomePage.fxml"));
            Parent root = loader.load();

            staffHomePage controller = loader.getController();

            controller.setUserData(idstr);

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
        String idstr = userIdLabel.getText();
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("customerHomePage.fxml"));
            Parent root = loader.load();

            customerHomePage controller = loader.getController();

            controller.setUserData(idstr);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            logger.severe("Error loading customerHomePage.fxml: " + e.getMessage());
        }
    }

    public void closeUserInfo() {
        Stage stage = (Stage) this.closeUserInfo.getScene().getWindow();
        stage.close();
    }
}