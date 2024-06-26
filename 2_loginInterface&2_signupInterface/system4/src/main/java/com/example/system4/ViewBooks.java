package com.example.system4;

import com.mysql.cj.protocol.Resultset;
import com.sun.tools.javac.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.*;

import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.sql.Connection;
import java.util.logging.Logger;

public class ViewBooks {
    private static final Logger logger = Logger.getLogger(ViewBooks.class.getName());

    @FXML
    private ComboBox genreDrop;

    @FXML
    private ComboBox Status;

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
    private TableColumn<ViewBookInformation, String> VISBN_col;

    @FXML
    private TableColumn<ViewBookInformation, Double> VPrice_col;

    @FXML
    private TableColumn<ViewBookInformation, String> VStatus_col;

    @FXML
    private TableColumn<ViewBookInformation, String> VPubDate_col;

    @FXML
    private TableColumn<ViewBookInformation, Double> VShelf_col;



    int index = -1;

    Connection connect = null;
    Resultset rs = null;
    PreparedStatement pstmt = null;



    @FXML
    public void initialize() {
        VBookID_col.setCellValueFactory(new PropertyValueFactory<ViewBookInformation, String>("bookId"));
        VBookTitle_col.setCellValueFactory(new PropertyValueFactory<ViewBookInformation, String>("title"));
        VBookAuthor_col.setCellValueFactory(new PropertyValueFactory<ViewBookInformation, String>("author"));
        VBseries_col.setCellValueFactory(new PropertyValueFactory<ViewBookInformation, Integer>("seriesNo"));
        VGenre_col.setCellValueFactory(new PropertyValueFactory<ViewBookInformation, String>("genre"));
        VISBN_col.setCellValueFactory(new PropertyValueFactory<ViewBookInformation, String>("isbn"));
        VPrice_col.setCellValueFactory(new PropertyValueFactory<ViewBookInformation, Double>("sellingPrice"));
        VStatus_col.setCellValueFactory(new PropertyValueFactory<ViewBookInformation, String>("status"));
        VPubDate_col.setCellValueFactory(new PropertyValueFactory<ViewBookInformation, String>("pubdate"));
        VShelf_col.setCellValueFactory(new PropertyValueFactory<ViewBookInformation, Double>("shelf"));

        //loadDataFromDatabase();
        ObservableList<ViewBookInformation> data = SQLConnector.getDatausers();
        System.out.println("Size of the list: " + data.size());
        ViewTableM.setItems(data);
        ViewTableM.refresh();
    }

    @FXML
    private void loadDataFromDatabase() {
        String url = "jdbc:mysql://127.0.0.1:3306/addbookinvoice";
        String user = "root";
        String password = "10141996";

        String sql = "SELECT `BOOK ID`, TITLE, AUTHOR, `SERIES NO.`, GENRE, SHELF, `PUB-DATE`, ISBN, STATUS, PRICE FROM addbookinvoice.addbooksinfo";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery();) {
            // Clear existing items in the TableView
            ViewTableM.getItems().clear();

            // Iterate through the ResultSet and populate the TableView
            while (rs.next()) {
                ViewBookInformation data = new ViewBookInformation(rs.getString("BOOK ID"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("genre"),
                        rs.getString("status"),
                        rs.getString("isbn"),
                        rs.getInt("pubdate"),
                        rs.getDouble("sellingPrice"),
                        rs.getInt("seriesNo"),
                        rs.getString("shelf")
                );  ViewTableM.getItems().add(data);

            }
        } catch (SQLException e) {
            logger.severe("Error loading MainInterface.fxml: " + e.getMessage());
        }
    }

    @FXML
    public void homeButton(ActionEvent event) {
        URL fxmlResource = getClass().getResource("adminHomePage.fxml");

        if (fxmlResource == null) {
            logger.severe("FXML resource 'adminHomePage.fxml' not found");
            return;
        }
        try {
            Parent root = FXMLLoader.load(fxmlResource);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            logger.severe("Error loading adminHomePage.fxml: " + e.getMessage());
        }
    }

}