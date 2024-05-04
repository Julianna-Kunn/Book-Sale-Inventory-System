package com.example.system4;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class BuyBook {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public void backBuyBooksButton(ActionEvent event){
        URL fxmlResource = getClass().getResource("MainInterface.fxml");

        if (fxmlResource == null){
            logger.severe("FXML resource 'MainInterface.fxml' not found");
            return;
        }try{
            Parent root = FXMLLoader.load(fxmlResource);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (IOException e){
            logger.severe("Error loading MainInterface.fxml: " + e.getMessage());
        }
    }


}
