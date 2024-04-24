module org.example.booksales_inventory_system {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens org.example.booksales_inventory_system to javafx.fxml;
    exports org.example.booksales_inventory_system;
}