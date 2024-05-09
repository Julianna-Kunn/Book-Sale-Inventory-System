package com.example.system4;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.*;
import javafx.stage.Stage;
import java.sql.Connection;
import java.util.logging.Logger;

public class ViewBooks {
    @FXML
    private Label userIdLabel;

    @FXML
    private TableView<ViewBookInformation> ViewTableM;

    @FXML
    private TableColumn<ViewBookInformation, String> VBookID_col;

    @FXML
    private TableColumn<ViewBookInformation, String> VBookTitle_col;

    @FXML
    private TableColumn<ViewBookInformation, String> VBookAuthor_col;

    @FXML
    private TableColumn<ViewBookInformation, Integer> VBseries_col;

    @FXML
    private TableColumn<ViewBookInformation, String> VGenre_col;

    @FXML
    private TableColumn<ViewBookInformation, String> VShelf_col;

    @FXML
    private TableColumn<ViewBookInformation, Integer> VPubDate_col;

    @FXML
    private TableColumn<ViewBookInformation, String> VISBN_col;

    @FXML
    private TableColumn<ViewBookInformation, String> VStatus_col;

    @FXML
    private TableColumn<ViewBookInformation, Double> VPrice_col;

    @FXML
    private TableColumn<ViewBookInformation, Integer> VQuantity_col;

    private static final Logger logger = Logger.getLogger(ViewBooks.class.getName());

    public void setUserData(String idstr) {
        userIdLabel.setText(idstr);
    }

    @FXML
    public void initialize() {
        loadDataFromDatabase();
    }

    @FXML
    private void loadDataFromDatabase() {
        String url = "jdbc:mysql://127.0.0.1:3306/addbooksinvoice";
        String user = "root";
        String password = "119904100297";

        try (Connection conn = DriverManager.getConnection(url, user, password)){
            String select = "SELECT * FROM addbooksinvoice.addbooksinfo";
             PreparedStatement pstmt = conn.prepareStatement(select);
             ResultSet rs = pstmt.executeQuery();

            // Iterate through the ResultSet and populate the TableView
            while (rs.next()) {
                String bookId = rs.getString("BOOK ID");
                String title = rs.getString("TITLE");
                String author = rs.getString("AUTHOR");
                int seriesNo = rs.getInt("SERIES NO.");
                String genre = rs.getString("GENRE");
                String shelf = rs.getString("SHELF");
                int pubdate = rs.getInt("PUB-DATE");
                String isbn = rs.getString("ISBN");
                String status = rs.getString("STATUS");
                double price = rs.getDouble("PRICE");
                int quantity = rs.getInt("QUANTITY");

                ViewTableM.getItems().add(new ViewBookInformation(bookId, title, author, seriesNo, genre, shelf, pubdate, isbn, status, price, quantity));
            }

            VBookID_col.setCellValueFactory(new PropertyValueFactory<>("bookId"));
            VBookTitle_col.setCellValueFactory(new PropertyValueFactory<>("title"));
            VBookAuthor_col.setCellValueFactory(new PropertyValueFactory<>("author"));
            VBseries_col.setCellValueFactory(new PropertyValueFactory<>("seriesNo"));
            VGenre_col.setCellValueFactory(new PropertyValueFactory<>("genre"));
            VShelf_col.setCellValueFactory(new PropertyValueFactory<>("shelf"));
            VPubDate_col.setCellValueFactory(new PropertyValueFactory<>("pubdate"));
            VISBN_col.setCellValueFactory(new PropertyValueFactory<>("isbn"));
            VStatus_col.setCellValueFactory(new PropertyValueFactory<>("status"));
            VPrice_col.setCellValueFactory(new PropertyValueFactory<>("price"));
            VQuantity_col.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        } catch (SQLException e) {
            logger.severe("Error loading ViewBooks.fxml: " + e.getMessage());
        }
    }

    @FXML
    public void BackButtonDashboard(ActionEvent event) {
        String idstr = userIdLabel.getText();
        try {
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
}
