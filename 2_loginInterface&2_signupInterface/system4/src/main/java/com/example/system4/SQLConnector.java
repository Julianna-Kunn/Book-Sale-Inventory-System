package com.example.system4;

import java.sql.Connection;
import java.sql.DriverManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SQLConnector {

    Connection connect = null;

    public static Connection ConnectDb() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://127.0.0.1:3306/addbookinvoice";
            String user = "root";
            String password = "10141996";
            Connection conn = DriverManager.getConnection(url, user, password);
            Alert connectionstatus = new Alert(Alert.AlertType.INFORMATION);
            connectionstatus.setTitle("CONNECTION STATUS");
            connectionstatus.setHeaderText(null);
            connectionstatus.setContentText("CONNECTION ESTABLISHED");
            connectionstatus.showAndWait();
            return conn;
        } catch (Exception e) {
            Alert connectionstatus = new Alert(Alert.AlertType.ERROR);
            connectionstatus.setTitle("CONNECTION STATUS");
            connectionstatus.setHeaderText(null);
            connectionstatus.setContentText("CONNECTION FAILED");
            connectionstatus.showAndWait();
            return null;
        }
    }

    public static ObservableList<ViewBookInformation> getDatausers(){

        Connection connect = ConnectDb();
        ObservableList<ViewBookInformation>  list = FXCollections.observableArrayList();
        String sql = "SELECT `BOOK ID`, TITLE, AUTHOR, `SERIES NO.`, GENRE, SHELF, `PUB-DATE`, ISBN, STATUS, PRICE FROM addbookinvoice.addbooksinfo";

        try(
                PreparedStatement pstmt = connect.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery();){

            while(rs.next()){
                list.add(new ViewBookInformation(
                        rs.getString("BOOK ID"),
                        rs.getString("TITLE"),
                        rs.getString("AUTHOR"),
                        rs.getString("GENRE"),
                        rs.getString("STATUS"),
                        rs.getString("ISBN"),
                        rs.getInt("PUB-DATE"),
                        rs.getDouble("PRICE"),
                        rs.getInt("SERIES NO."),
                        rs.getString("SHELF")
                ));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

}