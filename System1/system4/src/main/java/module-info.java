module com.example.system4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;

    requires com.dlsc.formsfx;

    opens com.example.system4 to javafx.fxml;
    exports com.example.system4;
}