module com.example.system2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires  java.sql;

    requires com.dlsc.formsfx;
    requires java.desktop;

    opens com.example.system2 to javafx.fxml;
    exports com.example.system2;
}