package com.memorycards;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.print.PageLayout;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        final int FONT_SIZE = 20, WIDTH = 800, HEIGHT = 600;

        // Loading Custom Fonts
        Font.loadFont(getClass().getResource("fonts/Chewy-Regular.ttf").toString(), FONT_SIZE);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Start.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
        // By using setTitle we can set the title of the main program that appears on the top of scene
        stage.setTitle("Memory Card Game - Flipping Tiles");
        // turning off the program's resizability so we can prevent from breaking the screen
        stage.setResizable(false);
        // Adding the Logo of the Program to the App
        Image icon = new Image(getClass().getResource("images/logo.png").toString());
        stage.getIcons().add(icon);
        // This method closes and terminates the program when user close the actual app
        stage.setOnCloseRequest(event -> {System.exit(0);});
        // Finally setting the scene and displaying it
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) { launch(); }
}