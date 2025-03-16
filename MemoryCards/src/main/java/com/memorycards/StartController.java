package com.memorycards;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StartController {
    @FXML
    private Button startBtn;
    @FXML
    private Button helpBtn;
    @FXML
    private ImageView view;

    public void start(ActionEvent event) throws IOException {
        loadFXML("Main.fxml", event);
    }

    public void help(ActionEvent event) throws IOException {
        loadFXML("Help.fxml", event);
    }

    private void loadFXML(String fxmlFileName, ActionEvent event) throws IOException {
        Parent fxmlLoader = FXMLLoader.load(getClass().getResource(fxmlFileName));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader);
        stage.setScene(scene);
        stage.show();
    }
}
