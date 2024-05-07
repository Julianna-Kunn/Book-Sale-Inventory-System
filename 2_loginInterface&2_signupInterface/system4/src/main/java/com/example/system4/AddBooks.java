package com.example.system4;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.net.URL;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.stage.Stage;


public class AddBooks implements Initializable {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    @FXML
    private ComboBox<String> Status;
    private Platform Platform;

    public void Status() {
        ObservableList<String> status = FXCollections.observableArrayList("New/Excellent", "Good", "Acceptable/Poor");
        Status.setItems(status);
    }
    @FXML
    void Status(ActionEvent event) {
        String selectedStatus = Status.getSelectionModel().getSelectedItem().toString();

        Alert WindowPane = new Alert(Alert.AlertType.INFORMATION);
        WindowPane.setTitle("Selected Status");

        if (selectedStatus != null) {
            WindowPane.setHeaderText("You selected: " + selectedStatus);
        }

        WindowPane.showAndWait();

    }
    @FXML
    private ComboBox<String> genreDrop;

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
    private TextField BAuthor;

    //===================================
    @FXML
    private TextField BBookId;


    @FXML
    public String Bidgenerate() {
        int maxId = 1000;
        maxId = getBookId() + 1;
        return "B-" + maxId;
    }


    @FXML
    public String InvoiceIdgenerate(){
        int maxInvoice = 2000; // Declare maxInvoice as a class variable
        // Fetch the maximum invoice ID from the database
        maxInvoice = getInvoiceId() + 1;

        // Increment the maximum invoice ID for the next invoice

        return "INV-" + maxInvoice;
    }


    @FXML
    public int getBookId() {
        String url = "jdbc:mysql://127.0.0.1:3306/addbookinvoice";
        String user = "root";
        String password = "10141996";
        int maxId = 1000;

        String sql = "SELECT MAX(CAST(SUBSTRING_INDEX(`BOOK ID`, '-', -1) AS UNSIGNED)) FROM addbookinvoice.addbooksinfo";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                int retrieved = rs.getInt(1);
                if(retrieved > maxId){
                    maxId = retrieved;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maxId;
    }

    @FXML
    public int getInvoiceId() {
        String url = "jdbc:mysql://127.0.0.1:3306/addbookinvoice";
        String user = "root";
        String password = "10141996";

        int maxId = 2000;

        String sql = "SELECT MAX(CAST(SUBSTRING_INDEX(`INVOICE ID`, '-', -1) AS UNSIGNED)) FROM addbookinvoice.addbooksinfo";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery();) {
            if (rs.next()) {
                int retrieved = rs.getInt(1);
                if (retrieved > maxId) {
                    maxId = retrieved;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maxId;
    }

    @FXML
    private TextField BInvoiceId;
    //===================================

    @FXML
    private TextField BIsbn;
    @FXML
    private TextField BPubDate;
    @FXML
    private TextField BSellingPrice;
    @FXML
    private TextField BSeriesNo;
    @FXML
    private TextField BShelf;
    @FXML
    private TextField BSupplierPrice;
    @FXML
    private TextField BTitle;
    //============================
    @FXML
    private Button Home;
    //============================

    @FXML
    private TextField BBookQuantity;

    @Override
    public void initialize(URL url, ResourceBundle rb) { //You cannot have a lot of initialize
        Genre();
        Status();
        genreDrop.setOnAction(event -> {
            String selectedGenre = genreDrop.getValue();
            // Call populateGenreToShelfMap with the selected genre
            BShelf.setText(populateGenreToShelfMap(selectedGenre));
        });
        BBookId.setText(Bidgenerate());
        BInvoiceId.setText(InvoiceIdgenerate());
    }


    @FXML
    private boolean validateFields(String formType) {
        if (formType.equals("new")) {
            return  BBookId.getText().isEmpty() || BTitle.getText().isEmpty() ||
                    BAuthor.getText().isEmpty() || BSeriesNo.getText().isEmpty() ||
                    genreDrop.getValue() == null || BShelf.getText().isEmpty() ||
                    BPubDate.getText().isEmpty() || BIsbn.getText().isEmpty() ||
                    Status.getValue() == null || BSellingPrice.getText().isEmpty() ||
                    BSupplierPrice.getText().isEmpty() || BInvoiceId.getText().isEmpty() ||
                    BBookQuantity.getText().isEmpty();
        } else {
            // Handle other form types if needed
            return false; // For now, returning false as default
        }
    }

    @FXML
    void Save(ActionEvent event) {
        int bookQuantity = Integer.parseInt(BBookQuantity.getText());
        String invoiceId = null;
        int successfulAdditions = 0;

        // Generate invoice ID only once at the beginning of the loop
        if (invoiceId == null) {
            invoiceId = InvoiceIdgenerate();
        }

        // Loop through each book to add
        for (int i = 1; i <= bookQuantity; i++) {
            successfulAdditions = 0;
            // Generate book ID for each book
            String bookid = Bidgenerate();
            // Update the book ID on the JavaFX Application Thread
            String title = BTitle.getText();
            String author = BAuthor.getText();
            String genre = genreDrop.getValue();
            String status = Status.getValue();
            String isbn = BIsbn.getText();
            int pubdate = Integer.parseInt(BPubDate.getText());
            double sellingPrice = Double.parseDouble(BSellingPrice.getText());
            int seriesNo = Integer.parseInt(BSeriesNo.getText());
            String shelf = populateGenreToShelfMap(genre);
            double supplierPrice = Double.parseDouble(BSupplierPrice.getText());
            BBookId.setText(Bidgenerate());
            try {
                AddBook(bookid, title, author, genre, status, isbn, pubdate, sellingPrice, seriesNo, shelf, supplierPrice, invoiceId);
                successfulAdditions++;
            } catch (Exception e) {
                e.printStackTrace();
                // Handle any exceptions during book addition
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("An error occurred while adding the book!");
                errorAlert.showAndWait();
            }

            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);

            if(validateFields("new")){
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Caution..");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("All fields are required!");
                errorAlert.showAndWait();
            } else {
                try{
                    successAlert.setTitle("Success");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Book added successfully!");
                    successAlert.showAndWait();

                    // Clear input fields
                    clearInputFields();
                } catch (Exception e){
                    e.printStackTrace();
                    successAlert.setTitle(null);
                    successAlert.setHeaderText(null);
                    successAlert.setContentText(null);
                    successAlert.showAndWait();
                }
            }
        }

        if (successfulAdditions == bookQuantity) { // Increment the invoice counter
            BInvoiceId.setText(InvoiceIdgenerate()); // Update the invoice ID in the UI
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Success");
            successAlert.setHeaderText(null);
            successAlert.setContentText("All books added successfully!");
            successAlert.showAndWait();
        } else {
            // Display error message if some books failed to be added
            //int remainingBooks = bookQuantity - successfulAdditions;
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Some books failed to be added. Please try again or check your input.");
            errorAlert.showAndWait();

        }

        // Clear the book quantity field
        BBookQuantity.clear();
    }

    @FXML
    public void clearInputFields(){
        BTitle.clear();
        BAuthor.clear();
        genreDrop.getSelectionModel().clearSelection();
        Status.getSelectionModel().clearSelection();
        BIsbn.clear();
        BPubDate.clear();
        BSellingPrice.clear();
        BSeriesNo.clear();
        BShelf.clear();
        BSupplierPrice.clear();
    }

    @FXML
    private void AddBook(String bookid, String title, String author, String genre, String status, String isbn,
                         int pubdate, double sellingPrice,
                         int seriesNo, String shelf, double supplierPrice, String invoiceid) {
        String url = "jdbc:mysql://127.0.0.1:3306/addbookinvoice";
        String user = "root";
        String password = "10141996";

        String sql = "INSERT INTO addbookinvoice.addbooksinfo (`BOOK ID`, TITLE, AUTHOR, `SERIES NO.`, GENRE, SHELF, `PUB-DATE`, ISBN, STATUS, PRICE, `SUP-PRICE`, `INVOICE ID`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";


        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, bookid);
            pstmt.setString(2, title);
            pstmt.setString(3, author);
            pstmt.setInt(4, seriesNo);  // Assuming seriesNo is an integer
            pstmt.setString(5, genre);
            pstmt.setString(6, shelf);   // Assuming shelf is a string
            pstmt.setInt(7, pubdate); // Assuming pubDate is a string
            pstmt.setString(8, isbn);
            pstmt.setString(9, status);
            pstmt.setDouble(10, sellingPrice);   // Assuming price is a double
            pstmt.setDouble(11, supplierPrice); // Assuming supPrice is a double
            pstmt.setString(12, invoiceid + " (" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + ")");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
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

