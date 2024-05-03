package com.example.system4;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.logging.Logger;

public class adminSubUserLog {
    @FXML
    private Button closeAdminSub;
    @FXML
    private Label userIdLabel;
    @FXML
    private TableView<userTable> userTable;
    @FXML
    private TableColumn<userTable, String> idCol;
    @FXML
    private TableColumn<userTable, String> typeCol;
    @FXML
    private TableColumn<userTable, String> nameCol;
    @FXML
    private TableColumn<userTable, String> emailCol;
    @FXML
    private TableColumn<userTable, String> contact_numberCol;
    @FXML
    private TableColumn<userTable, String> passCol;
    @FXML
    private TableColumn<userTable, String> createCol;
    @FXML
    private TableColumn<userTable, String> updateCol;

    @FXML
    private TableView<userLogTable> userLogTable;
    @FXML
    private TableColumn<userLogTable, String> uidCol;
    @FXML
    private TableColumn<userLogTable, String> user_typeCol;
    @FXML
    private TableColumn<userLogTable, String> date_timeCol;

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public void setUserData(String idstr) {
        userIdLabel.setText(idstr);
    }

    public void initialize() {
        loadUsertable();
        loadUserLog();
    }

    public void loadUsertable(){
        String url = "jdbc:mysql://127.0.0.1:3306/signup_schema";
        String user = "root";
        String dbPassword = "119904100297";

        try(Connection connection = DriverManager.getConnection(url, user, dbPassword)){
            String select = "SELECT * FROM signup_schema.users";
            PreparedStatement prepare = connection.prepareStatement(select);
            ResultSet resultSet = prepare.executeQuery();

            while(resultSet.next()){
                String id = resultSet.getString("userid");
                String user_type = resultSet.getString("user_type");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String contact_number = resultSet.getString("contact_number");
                String password = resultSet.getString("password");
                String create = resultSet.getString("date_time");
                String update = resultSet.getString("updated");

                userTable.getItems().add(new userTable(id, user_type, name, email, contact_number, password, create, update));
            }

            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            typeCol.setCellValueFactory(new PropertyValueFactory<>("user_type"));
            nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
            contact_numberCol.setCellValueFactory(new PropertyValueFactory<>("contact_number"));
            passCol.setCellValueFactory(new PropertyValueFactory<>("password"));
            createCol.setCellValueFactory(new PropertyValueFactory<>("create"));
            updateCol.setCellValueFactory(new PropertyValueFactory<>("update"));

        }catch (SQLException e){
            logger.severe("Error loading user table data: " + e.getMessage());
        }
    }

    public void loadUserLog(){
        String url = "jdbc:mysql://127.0.0.1:3306/signup_schema";
        String user = "root";
        String dbPassword = "119904100297";

        try(Connection connection = DriverManager.getConnection(url, user, dbPassword)){
            String select = "SELECT * FROM user_log.user_log";
            PreparedStatement prepare = connection.prepareStatement(select);
            ResultSet resultSet = prepare.executeQuery();

            while(resultSet.next()){
                String id = resultSet.getString("userid");
                String user_type = resultSet.getString("user_type");
                String date = resultSet.getString("date_time");

                userLogTable.getItems().add(new userLogTable(id, user_type, date));
            }

            uidCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            user_typeCol.setCellValueFactory(new PropertyValueFactory<>("user_type"));
            date_timeCol.setCellValueFactory(new PropertyValueFactory<>("date_time"));

        }catch(SQLException e){
            logger.severe("Error loading user log data: " + e.getMessage());
        }
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

    public void closeAdminSub() {
        Stage stage = (Stage) this.closeAdminSub.getScene().getWindow();
        stage.close();
    }
}
