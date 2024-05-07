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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.logging.Logger;

public class adminHomePage {
    @FXML
    private Label userIdLabel;
    @FXML
    private Label dateLabel;
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
        updateDateLabel();
        loadUsertable();
        loadUserLog();
    }

    public void loadUsertable() {
        String url = "jdbc:mysql://127.0.0.1:3306/signup_schema";
        String user = "root";
        String dbPassword = "119904100297";

        try (Connection connection = DriverManager.getConnection(url, user, dbPassword)) {
            String select = "SELECT * FROM signup_schema.users";
            PreparedStatement prepare = connection.prepareStatement(select);
            ResultSet resultSet = prepare.executeQuery();

            while (resultSet.next()) {
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

        } catch (SQLException e) {
            logger.severe("Error loading user table data: " + e.getMessage());
        }
    }

    public void loadUserLog() {
        String url = "jdbc:mysql://127.0.0.1:3306/signup_schema";
        String user = "root";
        String dbPassword = "119904100297";

        try (Connection connection = DriverManager.getConnection(url, user, dbPassword)) {
            String select = "SELECT * FROM signup_schema.user_log";
            PreparedStatement prepare = connection.prepareStatement(select);
            ResultSet resultSet = prepare.executeQuery();

            while (resultSet.next()) {
                String id = resultSet.getString("userid");
                String user_type = resultSet.getString("user_type");
                String date = resultSet.getString("date_time");

                userLogTable.getItems().add(new userLogTable(id, user_type, date));
            }

            uidCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            user_typeCol.setCellValueFactory(new PropertyValueFactory<>("user_type"));
            date_timeCol.setCellValueFactory(new PropertyValueFactory<>("date_time"));

        } catch (SQLException e) {
            logger.severe("Error loading user log data: " + e.getMessage());
        }
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

    public void Logout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure you want to log-out?");

        ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.NO);

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == buttonTypeYes) {
            URL fxmlResource = getClass().getResource("MainInterface.fxml");

            if (fxmlResource == null) {
                logger.severe("FXML resource 'MainInterface.fxml' not found");
                return;
            }
            try {
                Parent root = FXMLLoader.load(fxmlResource);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                logger.severe("Error loading MainInterface.fxml: " + e.getMessage());
            }
        }
    }

    public void signUp(ActionEvent event) {
        String idstr = userIdLabel.getText();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("adminSignUp.fxml"));
            Parent root = loader.load();

            adminSignUp controller = loader.getController();

            controller.setUserData(idstr);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            logger.severe("Error occurred during opening adminSignUp " + e.getMessage());
        }
    }

    public void adminInfo(ActionEvent event) {
        String idstr = userIdLabel.getText();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("userInfo.fxml"));
            Parent root = loader.load();

            userInfo controller = loader.getController();

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







