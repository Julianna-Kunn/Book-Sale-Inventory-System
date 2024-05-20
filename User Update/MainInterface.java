package com.example.system4;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class MainInterface {
    @FXML
    private TableView<BookSearch> BookSearch;
    @FXML
    private TableColumn<BookSearch, String> bookIDColumn;
    @FXML
    private TableColumn<BookSearch, String> bookTitleColumn;
    @FXML
    private TableColumn<BookSearch, String> bookAuthorColumn;
    @FXML
    private TableColumn<BookSearch, Integer> bookSeriesColumn;
    @FXML
    private TableColumn<BookSearch, String> bookGenreColumn;
    @FXML
    private TableColumn<BookSearch, String> bookShelfColumn;
    @FXML
    private TableColumn<BookSearch, String> bookStatusColumn;
    @FXML
    private TableColumn<BookSearch, Double> bookPriceColumn;
    @FXML
    private TextField SearchBook;

    ObservableList<BookSearch> BookSearchObservableList = FXCollections.observableArrayList();

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public void initialize() {
      searching();
    }

    public void searching(){
        String url = "jdbc:mysql://127.0.0.1:3306/addbooksinvoice";
        String user = "root";
        String dbPass = "119904100297";

        String BookSearchQuery = "SELECT * FROM addbooksinvoice.addbooksinfo";
        try (Connection connection = DriverManager.getConnection(url, user, dbPass)) {
            PreparedStatement statement = connection.prepareStatement(BookSearchQuery);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String bookID = resultSet.getString("BOOK ID");
                String title = resultSet.getString("TITLE");
                String author = resultSet.getString("AUTHOR");
                int series = resultSet.getInt("SERIES NO.");
                String genre = resultSet.getString("GENRE");
                String shelf = resultSet.getString("SHELF");
                String status = resultSet.getString("STATUS");
                Double price = resultSet.getDouble("PRICE");

                // Populate the ObservableList
                BookSearchObservableList.add(new BookSearch(bookID, title, author, series, genre, shelf, status, price));
            }

            // Set cell value factories for TableView columns
            bookIDColumn.setCellValueFactory(new PropertyValueFactory<>("BookId"));
            bookTitleColumn.setCellValueFactory(new PropertyValueFactory<>("Title"));
            bookAuthorColumn.setCellValueFactory(new PropertyValueFactory<>("Author"));
            bookSeriesColumn.setCellValueFactory(new PropertyValueFactory<>("Series"));
            bookGenreColumn.setCellValueFactory(new PropertyValueFactory<>("Genre"));
            bookShelfColumn.setCellValueFactory(new PropertyValueFactory<>("Shelf"));
            bookStatusColumn.setCellValueFactory(new PropertyValueFactory<>("Status"));
            bookPriceColumn.setCellValueFactory(new PropertyValueFactory<>("Price"));

            BookSearch.setItems(BookSearchObservableList);

            // Create a filtered list for search functionality
            FilteredList<BookSearch> filteredData = new FilteredList<>(BookSearchObservableList, b -> true);

            // Listen for changes in the search text field
            SearchBook.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(book -> {
                if (newValue == null || newValue.trim().isEmpty()) {
                    return true; // Show all data if search text is empty
                }

                String search = newValue.trim().toLowerCase();

                // Check if any property contains the search text for title, author, or genre
                return book.getTitle().toLowerCase().contains(search) ||
                        book.getAuthor().toLowerCase().contains(search) ||
                        book.getGenre().toLowerCase().contains(search);
            }));

            // Wrap the filtered list in a SortedList to enable sorting
            SortedList<BookSearch> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(BookSearch.comparatorProperty());
            BookSearch.setItems(sortedData);

        } catch (SQLException e) {
            logger.severe("Error searching: " + e.getMessage());
        }
    }

    public void logInButton(ActionEvent event) {
        URL fxmlResource = getClass().getResource("Log-in.fxml");

        if (fxmlResource == null) {
            logger.severe("FXML resource 'Log-in.fxml' not found");
            return;
        }
        try {
            Parent root = FXMLLoader.load(fxmlResource);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            logger.severe("Error loading Log-in.fxml: " + e.getMessage());
        }
    }

    public void signUpButton(ActionEvent event) {
        URL fxmlResource = getClass().getResource("CustomerSignUp.fxml");

        if (fxmlResource == null) {
            logger.severe("FXML resource 'CustomerSignUp.fxml' not found");
            return;
        }
        try {
            Parent root = FXMLLoader.load(fxmlResource);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            logger.severe("Error loading CustomerSignUp.fxml: " + e.getMessage());
        }
    }


}
