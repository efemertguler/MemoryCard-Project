package com.memorycards;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class ResultController {
    @FXML
    private ImageView resultImage;
    @FXML
    private Label resultMoves;
    @FXML
    private Label timePlayed;

    public void setResults(boolean hasWon, int timePlayed, int moves) {
        setResultMoves(moves);
        setResultTime(hasWon, timePlayed);
        setResultImage(hasWon);
    }

    public void playAgain(ActionEvent event) throws IOException {
        // Load Main controller
        Parent fxmlLoader = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader);
        stage.setScene(scene);
        stage.show();
    }

    // simply close the program
    public void closeGame(){ System.exit(0); }

    private void setResultMoves(int moves) {
        resultMoves.setText(String.valueOf(moves));
    }

    private void setResultTime(boolean hasWon, int timePlayed) {
        if (hasWon) {
            int minutes = timePlayed / 60;
            int seconds = timePlayed % 60;
            String timeFormatted = String.format("%d:%02d", minutes, seconds);
            this.timePlayed.setText(timeFormatted);
        } else {
            this.timePlayed.setText("2:00");
        }
    }

    private void setResultImage(boolean hasWon) {
        String imagePath = hasWon ? "images/ResultImages/Win.png" : "images/ResultImages/Lose.png";
        Image result = new Image(getClass().getResource(imagePath).toString());
        resultImage.setImage(result);
    }
}
