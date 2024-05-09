module com.example.bsis {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires mysql.connector.j;
    requires jdk.compiler;


    opens com.example.bsis to javafx.fxml;
    exports com.example.bsis;
}