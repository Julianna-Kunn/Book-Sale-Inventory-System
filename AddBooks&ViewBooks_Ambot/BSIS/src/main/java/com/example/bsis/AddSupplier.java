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

public class AddSupplier implements Initializable {

    @FXML
    private TextField Birthdate;

    @FXML
    private TextField CompanyName;

    @FXML
    private TextField Contactnumber;

    @FXML
    private TextField SupplierId;

    @FXML
    private TextField SupplierName;

    @FXML
    private Button Save;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SupplierId.setText(Sidgenerate());
    }

    @FXML
    public void save(ActionEvent event){
        String supplierId = Sidgenerate();
        String companyName = CompanyName.getText();
        String supplierName = SupplierName.getText();
        String birthdate = Birthdate.getText();
        int contactnum = Integer.parseInt(Contactnumber.getText());

        AddSupplier(supplierId, companyName, supplierName, birthdate, contactnum);
    }

    @FXML
    public String Sidgenerate() {
        //int maxId = 1000;
        int maxId = getSupplierId() + 1;
        return "S-" + maxId;
    }

    @FXML
    public int getSupplierId() {
        String url = "jdbc:mysql://localhost:3306/add_supplier";
        String user = "root";
        String password = "septembersapphire_07";
        int maxId = 1000;

        String sql = "SELECT MAX(CAST(SUBSTRING_INDEX(`supplier_id`, '-', -1) AS UNSIGNED)) FROM add_supplier.add_supplier";

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
    private boolean AddSupplier( String supplierId, String companyName, String supplierName, String birthdate, int contactnum) {
        String url = "jdbc:mysql://localhost:3306/add_supplier";
        String user = "root";
        String password = "septembersapphire_07";

        String sql = "INSERT INTO add_supplier.add_supplier (`supplier_id`, `company name`, `representative name`, `Birthdate`, `contact number`, TimeStamp) VALUES (?,?,?,?,?,?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, supplierId);
            pstmt.setString(2, companyName);
            pstmt.setString(3, supplierName);
            pstmt.setString(4, birthdate);  // Assuming seriesNo is an integer
            pstmt.setInt(5, contactnum);
            pstmt.setString(6, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            pstmt.executeUpdate();
            return true; // Indicate successful addition
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Indicate failed addition
        }
    }


}
