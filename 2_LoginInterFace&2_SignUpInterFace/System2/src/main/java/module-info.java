module org.example.system2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens org.example.system2 to javafx.fxml;
    exports org.example.system2;
}