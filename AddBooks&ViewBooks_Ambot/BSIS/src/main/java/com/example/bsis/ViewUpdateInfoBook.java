package com.example.bsis;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import static com.mysql.cj.conf.PropertyKey.logger;


public class ViewUpdateInfoBook {

    @FXML
    private TextField BAuthor_d;

    @FXML
    private TextField BInvoiceId_d;

    @FXML
    private TextField BIsbn_d;

    @FXML
    private TextField BPubDate_d;

    @FXML
    private Text BQuantity;

    @FXML
    private TextField BQuantity_d;

    @FXML
    private TextField BSellingPrice_d;

    @FXML
    private TextField BSeriesNo_d;

    @FXML
    private TextField BShelf_d;

    @FXML
    private TextField BSupplierPrice_d;

    @FXML
    private TextField BTimeStamp_d;

    @FXML
    private TextField BTitle_d;

    @FXML
    private TextField BBookId_d;

    @FXML
    private Button Save;

    @FXML
    private ComboBox<String> Status;

    @FXML
    private Button Update;

    @FXML
    private TableColumn<ViewBookInformation, String> VBookAuthor_col;

    @FXML
    private TableColumn<ViewBookInformation, String> VBookID_col;

    @FXML
    private TableColumn<ViewBookInformation, String> VBookTitle_col;

    @FXML
    private TableColumn<ViewBookInformation, Integer> VBseries_col;

    @FXML
    private TableColumn<ViewBookInformation, String> VGenre_col;

    @FXML
    private TableColumn<ViewBookInformation, String> VISBN_col;

    @FXML
    private TableColumn<ViewBookInformation, String> VInvoiceId_col;

    @FXML
    private TableColumn<ViewBookInformation, Double> VPrice_col;

    @FXML
    private TableColumn<ViewBookInformation, String> VPubDate_col;

    @FXML
    private TableColumn<ViewBookInformation, Integer> VQuantity_col;

    @FXML
    private TableColumn<ViewBookInformation, Double> VShelf_col;

    @FXML
    private TableColumn<ViewBookInformation, String> VStatus_col;

    @FXML
    private TableColumn<ViewBookInformation, Double> VSupplierPrice;

    @FXML
    private TableColumn<ViewBookInformation, String> VTimestamp_col;

    @FXML
    private TableView<ViewBookInformation> ViewTableM;

    @FXML
    private ComboBox<String> genreDrop;

    @FXML
    public String Status() {
        ObservableList<String> status = FXCollections.observableArrayList("New/Excellent", "Good", "Acceptable/Poor");
        Status.setItems(status);
        return null;
    }

    @FXML
    void handleStatusSelection(ActionEvent event) {
        String selectedStatus = Status.getSelectionModel().getSelectedItem().toString();

        Alert WindowPane = new Alert(Alert.AlertType.INFORMATION);
        WindowPane.setTitle("Selected Status");

        if (selectedStatus != null) {
            WindowPane.setHeaderText("You selected: " + selectedStatus);
        }

        WindowPane.showAndWait();

    }

    @FXML
    void Genre(ActionEvent event) {
        String selectedGenre = genreDrop.getSelectionModel().getSelectedItem().toString();

        Alert WindowPane = new Alert(Alert.AlertType.INFORMATION);
        WindowPane.setTitle("Selected Genre");
        if (selectedGenre != null) {
            WindowPane.setHeaderText("You selected: " + selectedGenre);
        } else {
            WindowPane.setHeaderText("No Genre Selected");
        }
        WindowPane.showAndWait();

    }

    @FXML
    public void Genre() {
        ObservableList<String> genre = FXCollections.observableArrayList("Fiction", "Science Fiction", "Fantasy", "Mystery/Thriller",
                "Romance", "Historical Fiction", "Adventure", "Horror", "Western", "Dystopian", "Crime", "Biography/AutoBiography",
                "Memoir", "Self-Help", "History", "True Crime", "Science", "Travel", "Cook Books", "Philosophy", "Psychology", "Political Science",
                "Business/Finance", "Technology", "Health and Fitness", "Environment Ecology", "Parenting/Family", "Education", "Religion", "Art/Photography",
                "Music", "Sports", "Poetry", "Tragedy", "Comedy", "Melodrama", "Historical Drama", "Kids: Picture Books", "Young Adult",
                "Devotionals", "Encyclopedias", "Dictionaries", "Atlas", "Almanacs", "Manga", "Graphic Non-Fiction", "Erotic Fiction",
                "Metafiction", "Thriller/Suspense", "Health and Wellness", "Arts/Crafts", "Travelogue/Memoir", "True Adventure/Documentary", "Nature and Environment",
                "Cultural Studies", "Medical TextBooks", "Technical Hard Bounds", "Coffee Table Books");

        genre.sort(String::compareTo);
        genreDrop.setItems(genre);

    }

    @FXML
    private String populateGenreToShelfMap(String genre) {

        Map<String, String> genreToShelf = new HashMap<>();
        // Fiction Shelf
        genreToShelf.put("Fiction", "Fiction Shelf");
        genreToShelf.put("Science Fiction", "Fiction Shelf");
        genreToShelf.put("Fantasy", "Fiction Shelf");
        genreToShelf.put("Mystery/Thriller", "Fiction Shelf");
        genreToShelf.put("Romance", "Fiction Shelf");
        genreToShelf.put("Historical Fiction", "Fiction Shelf");
        genreToShelf.put("Adventure", "Fiction Shelf");
        genreToShelf.put("Horror", "Fiction Shelf");
        genreToShelf.put("Western", "Fiction Shelf");
        genreToShelf.put("Dystopian", "Fiction Shelf");
        genreToShelf.put("Crime", "Fiction Shelf");
        genreToShelf.put("True Crime", "Fiction Shelf");
        genreToShelf.put("Metafiction", "Fiction Shelf");
        genreToShelf.put("Thriller/Suspense", "Fiction Shelf");
        genreToShelf.put("Melodrama", "Fiction Shelf");
        genreToShelf.put("Historical Drama", "Fiction Shelf");
        genreToShelf.put("Young Adult", "Fiction Shelf");
        genreToShelf.put("Erotic Fiction", "Fiction Shelf");

        // Non-Fiction Shelf
        genreToShelf.put("Biography/AutoBiography", "Non-Fiction Shelf");
        genreToShelf.put("Memoir", "Non-Fiction Shelf");
        genreToShelf.put("Self-Help", "Non-Fiction Shelf");
        genreToShelf.put("History", "Non-Fiction Shelf");
        genreToShelf.put("Science", "Non-Fiction Shelf");
        genreToShelf.put("Travel", "Non-Fiction Shelf");
        genreToShelf.put("Cook Books", "Non-Fiction Shelf");
        genreToShelf.put("Philosophy", "Non-Fiction Shelf");
        genreToShelf.put("Psychology", "Non-Fiction Shelf");
        genreToShelf.put("Political Science", "Non-Fiction Shelf");
        genreToShelf.put("Business/Finance", "Non-Fiction Shelf");
        genreToShelf.put("Technology", "Non-Fiction Shelf");
        genreToShelf.put("Health and Fitness", "Non-Fiction Shelf");
        genreToShelf.put("Environment Ecology", "Non-Fiction Shelf");
        genreToShelf.put("Parenting/Family", "Non-Fiction Shelf");
        genreToShelf.put("Art/Photography", "Non-Fiction Shelf");
        genreToShelf.put("Music", "Non-Fiction Shelf");
        genreToShelf.put("Sports", "Non-Fiction Shelf");
        genreToShelf.put("Poetry", "Non-Fiction Shelf");
        genreToShelf.put("Tragedy", "Non-Fiction Shelf");
        genreToShelf.put("Comedy", "Non-Fiction Shelf");
        genreToShelf.put("Graphic Non-Fiction", "Non-Fiction Shelf");
        genreToShelf.put("Health and Wellness", "Non-Fiction Shelf");
        genreToShelf.put("Arts/Crafts", "Non-Fiction Shelf");
        genreToShelf.put("Travelogue/Memoir", "Non-Fiction Shelf");
        genreToShelf.put("True Adventure/Documentary", "Non-Fiction Shelf");
        genreToShelf.put("Nature and Environment", "Non-Fiction Shelf");
        genreToShelf.put("Cultural Studies", "Non-Fiction Shelf");
        genreToShelf.put("Technical Hard Bounds", "Non-Fiction Shelf");
        genreToShelf.put("Coffee Table Books", "Non-Fiction Shelf");

        // Academic Shelf
        genreToShelf.put("Education", "Academic Shelf");
        genreToShelf.put("Religion", "Academic Shelf");
        genreToShelf.put("Medical TextBooks", "Academic Shelf");
        genreToShelf.put("Cultural Studies", "Academic Shelf");
        genreToShelf.put("Philosophy", "Academic Shelf");
        genreToShelf.put("Psychology", "Academic Shelf");
        genreToShelf.put("Political Science", "Academic Shelf");
        genreToShelf.put("Business/Finance", "Academic Shelf");
        genreToShelf.put("Technology", "Academic Shelf");
        genreToShelf.put("Science", "Academic Shelf");
        genreToShelf.put("Health and Wellness", "Academic Shelf");

        // Kids Shelf
        genreToShelf.put("Kids: Picture Books", "Kids Shelf");
        genreToShelf.put("Manga", "Kids Shelf");
        genreToShelf.put("Children's Literature", "Kids Shelf");

        // Reference Shelf
        genreToShelf.put("Encyclopedias", "Reference Shelf");
        genreToShelf.put("Dictionaries", "Reference Shelf");
        genreToShelf.put("Atlases", "Reference Shelf");
        genreToShelf.put("Almanacs", "Reference Shelf");
        genreToShelf.put("Atlas", "Reference Shelf");


        // Religious Shelf
        genreToShelf.put("Religion", "Religious Shelf");
        genreToShelf.put("Devotionals", "Religious Shelf");
        genreToShelf.put("Inspirational Literature", "Religious Shelf");

        if (genreToShelf.containsKey(genre)) {
            // Return the corresponding shelf for the genre
            return genreToShelf.get(genre);
        } else {
            // If the genre is not found in the map, return "Unknown"
            System.err.println("No shelf category found for genre: " + genre);
            return "Unknown";
        }
    }

    @FXML
    public void initialize() { //You cannot have a lot of initialize
        Genre();
        Status();

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
        VQuantity_col.setCellValueFactory(new PropertyValueFactory<ViewBookInformation, Integer>("quantity"));
        VSupplierPrice.setCellValueFactory(new PropertyValueFactory<ViewBookInformation, Double>("supplierPrice"));
        VInvoiceId_col.setCellValueFactory(new PropertyValueFactory<ViewBookInformation, String>("invoiceid"));
        VTimestamp_col.setCellValueFactory(new PropertyValueFactory<ViewBookInformation, String>("timestamp"));

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
        String sql = "SELECT `BOOK ID`, TITLE, AUTHOR, `SERIES NO.`, GENRE, SHELF, `PUB-DATE`, ISBN, STATUS, PRICE, QUANTITY, `SUP-PRICE`, `INVOICE ID`, TIMESTAMP FROM addbookinvoice.addbooksinfo";
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
                        rs.getString("shelf"),
                        rs.getInt("quantity"),
                        rs.getDouble("supplierPrice"),
                        rs.getString("invoiceid"),
                        rs.getString("timestamp")
                );  ViewTableM.getItems().add(data);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}