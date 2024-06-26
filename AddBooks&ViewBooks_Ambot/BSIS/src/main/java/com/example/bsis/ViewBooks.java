package com.example.bsis;

import com.mysql.cj.protocol.Resultset;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;

import javafx.collections.ObservableList;
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
        String url = "jdbc:mysql://localhost:3306/addbookinvoice";
        String user = "root";
        String password = "septembersapphire_07";

        String sql = "SELECT `BOOK ID`, TITLE, AUTHOR, `SERIES NO.`, GENRE, SHELF, `PUB-DATE`, ISBN, STATUS, PRICE, `SUP-PRICE`, QUANTITY, `INVOICE ID`, `TIMESTAMP` FROM addbookinvoice.addbooksinfo";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            // Clear existing items in the TableView
            ViewTableM.getItems().clear();

            // Iterate through the ResultSet and populate the TableView
            while (rs.next()) {
                ViewBookInformation data = new ViewBookInformation(
                        rs.getString("BOOK ID"),
                        rs.getString("TITLE"),
                        rs.getString("AUTHOR"),
                        rs.getString("GENRE"),
                        rs.getString("STATUS"),
                        rs.getString("ISBN"),
                        rs.getInt("PUB-DATE"),
                        rs.getDouble("PRICE"),
                        rs.getInt("SERIES NO."),
                        rs.getString("SHELF"),
                        rs.getInt("QUANTITY"),
                        rs.getDouble("SUPPLIER PRICE"),
                        rs.getString("INVOICE ID"),
                        rs.getString("TIMESTAMP")
                );
                ViewTableM.getItems().add(data);
            }

        } catch (SQLException e) {
            logger.severe("Error loading MainInterface.fxml: " + e.getMessage());
        }
    }
}