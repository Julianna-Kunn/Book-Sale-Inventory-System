module com.example.system {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;

    requires com.dlsc.formsfx;
    requires mysql.connector.j;
    requires jdk.compiler;
    requires java.desktop;

    opens com.example.system4 to javafx.fxml;
    exports com.example.system4;
}