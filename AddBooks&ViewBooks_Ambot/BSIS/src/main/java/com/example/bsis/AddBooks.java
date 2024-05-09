package com.example.bsis;

import com.sun.javafx.application.PlatformImpl;
import com.sun.tools.javac.Main;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.collections.ObservableList;

import java.awt.print.Book;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.util.ResourceBundle;
import java.net.URL;
import javafx.scene.Node;
import java.util.logging.Logger;

public class AddBooks implements Initializable {

    @FXML
    private TextField BQuantity;

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
    private TextField NumberOfBooksDelivered;

    @FXML
    private TextField BAuthor;

    //===================================
    @FXML
    private TextField BBookId;

    @FXML
    private ComboBox<String> genreDrop;

    @FXML
    private ComboBox<String> Status;

    private PlatformImpl Platform;

    private static final Logger logger = Logger.getLogger(Main.class.getName());

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
    public String Bidgenerate() {
        //int maxId = 1000;
        int maxId = getBookId() + 1;
        return "B-" + maxId;
    }


    @FXML
    public String InvoiceIdgenerate() {
        //int maxInvoice = 2000;
        int maxInvoice = getInvoiceId() + 1;

        return "INV-" + maxInvoice;
    }


    @FXML
    public int getBookId() {
        String url = "jdbc:mysql://localhost:3306/addbookinvoice";
        String user = "root";
        String password = "septembersapphire_07";
        int maxId = 1000;

        String sql = "SELECT MAX(CAST(SUBSTRING_INDEX(`BOOK ID`, '-', -1) AS UNSIGNED)) FROM addbookinvoice.addbooksinfo";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
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
    public int getInvoiceId() {
        String url = "jdbc:mysql://localhost:3306/addbookinvoice";
        String user = "root";
        String password = "septembersapphire_07";

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
    private boolean validateFields(String formType) {
        if (formType.equals("new")) {
           return BTitle.getText().isEmpty() ||
                    BAuthor.getText().isEmpty() || BSeriesNo.getText().isEmpty() ||
                    genreDrop.getValue() == null || BShelf.getText().isEmpty() ||
                    BPubDate.getText().isEmpty() || BIsbn.getText().isEmpty() ||
                    Status.getValue() == null || BSellingPrice.getText().isEmpty() ||
                    BSupplierPrice.getText().isEmpty() || NumberOfBooksDelivered.getText().isEmpty() || BQuantity.getText().isEmpty();
        } else {
            return false;
        }
    }

    private String shelf;
    private int count = 0;

    int numBooksDelivered;

    @FXML
    public void AddBookTrigger(ActionEvent event) {
        URL fxmlResource = getClass().getResource("AddBooks.fxml");

        if (fxmlResource == null) {
            logger.severe("FXML resource 'AddBookTriDeli.fxml' not found");
            return;
        }try{
            Parent root = FXMLLoader.load(fxmlResource);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            int bookQuantity = Integer.parseInt(NumberOfBooksDelivered.getText());
            initializeArrays(bookQuantity);
        }catch (IOException e){
            logger.severe("Error loading adminPage.fxml: " + e.getMessage());
        }
    }


    private void initializeArrays(int size) {
        this.numBooksDelivered = size;
        bookId = new String[size];
        title = new String[size];
        author = new String[size];
        genre = new String[size];
        status = new String[size];
        isbn = new String[size];
        pubDate = new int[size];
        seriesNo = new int[size];
        supplierPrice = new double[size];
        sellingPrice = new double[size];
    }

    private String[] bookId;
    private String[] title;
    private String[] author;
    private String[] genre;
    private String[] status;
    private String[] isbn;
    private int[] pubDate;
    private int[] seriesNo;
    private double[] supplierPrice;
    private double[] sellingPrice;

    @FXML
    public void addingtian() {
        // Retrieve numBooksDelivered from the TextField
        String invoiceId = InvoiceIdgenerate(); // Initialize the invoice ID

        if(count < numBooksDelivered) {
            String currentBookId = Bidgenerate();
            String currentTitle = BTitle.getText();
            String currentAuthor = BAuthor.getText();
            String currentGenre = genreDrop.getValue();
            String currentStatus = Status.getValue();
            String currentIsbn = BIsbn.getText();
            int currentPubDate = Integer.parseInt(BPubDate.getText());
            int currentSeriesNo = Integer.parseInt(BSeriesNo.getText());
            String currentShelf = populateGenreToShelfMap(currentGenre);
            double currentSupplierPrice = Double.parseDouble(BSupplierPrice.getText());
            double currentSellingPrice = Prices(currentSupplierPrice, currentShelf);

            if (validateFields("new")) {
                System.out.println("Validation failed: All fields are required.");
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Caution..");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("All fields are required!");
                errorAlert.showAndWait();
                return; // Exit the method if validation fails
            } else {
                // Store the book details in arrays
                bookId[count] = currentBookId;
                title[count] = currentTitle;
                author[count] = currentAuthor;
                genre[count] = currentGenre;
                status[count] = currentStatus;
                isbn[count] = currentIsbn;
                pubDate[count] = currentPubDate;
                seriesNo[count] = currentSeriesNo;
                supplierPrice[count] = currentSupplierPrice;
                sellingPrice[count] = currentSellingPrice;

                boolean addedSuccessfully = AddBook(currentBookId, currentTitle, currentAuthor, currentGenre, currentStatus, currentIsbn, currentPubDate, currentSellingPrice, currentSeriesNo, currentShelf, currentSupplierPrice, invoiceId, 1);

                if (addedSuccessfully) {
                    System.out.println("Successful Addition: " + currentTitle);
                    count++;
                } else {
                    System.out.println("Failed to add book with Title: " + currentTitle);
                }
            }
        }

        if(count == numBooksDelivered){
            InvoiceIdgenerate();
            for(int i = 0; i < numBooksDelivered; i++){
                System.out.println("bookTitles: " + title[i]);
            }
        }
        clearInputFields();
    }


    @FXML
    public double Prices(double supplierPrice, String shelf){
        String selectedStatus = Status.getSelectionModel().getSelectedItem().toString();
        System.out.println("Supplier Price: " + supplierPrice); // Debugging output
        System.out.println("Status: " + selectedStatus); // Debugging output
        double sellingPrice = 0;

        if(selectedStatus.equals("New/Excellent")){
            sellingPrice = supplierPrice + (supplierPrice * .70);
        } else if(selectedStatus.equals("Good")){
            sellingPrice = supplierPrice + (supplierPrice * .40);
        } else if (selectedStatus.equals("Acceptable/Poor")){
            sellingPrice = supplierPrice + (supplierPrice *.20);
        }

        System.out.println("Selling Price: " + sellingPrice);

        return sellingPrice;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) { //You cannot have a lot of initialize
        Genre();
        Status();
        BBookId.setText(Bidgenerate());
        System.out.println("INITIALIZE: " + Bidgenerate() + " ");
        genreDrop.setOnAction(event -> {
            String selectedGenre = genreDrop.getValue();
            // Call populateGenreToShelfMap with the selected genre
            BShelf.setText(populateGenreToShelfMap(selectedGenre));
        });
        BInvoiceId.setText(InvoiceIdgenerate());
        BSupplierPrice.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.isEmpty()){
                double supplierPrice = Double.parseDouble(newValue);
                BSellingPrice.setText(Double.toString(Prices(supplierPrice, this.shelf)));
            } else {
                BSellingPrice.clear();
            }
        });

    }

    @FXML
    private Button saveButton;

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
        BQuantity.clear();
    }

    @FXML
    private boolean AddBook(String bookid, String title, String author, String genre, String status, String isbn,
                            int pubdate, double sellingPrice,
                            int seriesNo, String shelf, double supplierPrice, String invoiceid, int bquantity) {
        String url = "jdbc:mysql://localhost:3306/addbookinvoice";
        String user = "root";
        String password = "septembersapphire_07";

        String sql = "INSERT INTO addbookinvoice.addbooksinfo (`BOOK ID`, TITLE, AUTHOR, `SERIES NO.`, GENRE, SHELF, `PUB-DATE`, ISBN, STATUS, PRICE, `SUP-PRICE`, QUANTITY, `INVOICE ID`, `TIMESTAMP`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

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
            pstmt.setInt(12, 1);
            pstmt.setString(13, invoiceid);
            pstmt.setString(14, " (" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + ")");

            pstmt.executeUpdate();
            BBookId.setText(Bidgenerate());
            System.out.println("SAVE: " + Bidgenerate() + " ");
            // Clear the book quantity field
            return true; // Indicate successful addition
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Indicate failed addition
        }
    }


    @FXML
    private boolean bookExists(String title, String author, String genre, String isbn, int pubdate, double sellingPrice,
                               int seriesNo, String shelf, double supplierPrice) {
        String url = "jdbc:mysql://localhost:3306/addbookinvoice";
        String user = "root";
        String password = "septembersapphire_07";

        String sql = "SELECT COUNT(*) FROM addbookinvoice.addbooksinfo WHERE TITLE = ? AND AUTHOR = ? AND GENRE = ? AND ISBN = ? " +
                "AND `PUB-DATE` = ? AND PRICE = ? AND `SERIES NO.` = ? AND SHELF = ? AND `SUP-PRICE` = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, author);
            pstmt.setString(3, genre);
            pstmt.setString(4, isbn);
            pstmt.setInt(5, pubdate);
            pstmt.setDouble(6, sellingPrice);
            pstmt.setInt(7, seriesNo);
            pstmt.setString(8, shelf);
            pstmt.setDouble(9, supplierPrice);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count == 9; // Returns true if there's at least one matching entry in the database
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}

