package com.memorycards;

import animatefx.animation.Shake;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class MainController implements Initializable {
    @FXML
    private GridPane gridPane;
    @FXML
    private Label move;
    @FXML
    private Label time;

    private Board board;
    private Tracker tracker;
    private Card firstCard;
    private Card secondCard;

    public MainController() {
        board = new Board();
        tracker = new Tracker();
        firstCard = null;
        secondCard = null;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // as soon as the game starts set the initial cards view which is a question mark
        initialCardsView();
        // then populate the cards on the board randomly each time
        board.populateCards();
        // keep checking cards till a pair is found
        checkCardsView();
        // start the timer on the UI and update it constantly
        startTimer();
    }

    private void initialCardsView() {
        int card = 0;
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                ((Button) gridPane.getChildren().get(card)).setGraphic(getImage("q", 40, 40));
                card++;
            }
        }
    }

    private void setCardView(MouseEvent event) {
        Node source = (Node) event.getSource();
        String rowAndCol = (String) source.getUserData();

        int row = Integer.parseInt(rowAndCol.split(",")[0]);
        int col = Integer.parseInt(rowAndCol.split(",")[1]);

        String image = board.getCards()[row][col].getName();
        ((Button) source).setGraphic(getImage(image, 50, 50));
    }

    private void checkCardsView() {
        int card = 0;
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                gridPane.getChildren().get(card).setUserData(row + "," + col);
                int selectedRow = row;
                int selectedCol = col;
                gridPane.getChildren().get(card).setOnMouseClicked(event -> {
                    setCardView(event);
                    setCards(selectedRow, selectedCol);
                    if (board.areAllCardsFlipped()) showResult(true);
                });
                card++;
            }
        }
    }

    private void setCards(int selectedRow, int selectedCol) {
        if (firstCard == null) {
            firstCard = board.getCards()[selectedRow][selectedCol];
            firstCard.setFlipped(true);
        } else if (secondCard == null) {
            secondCard = board.getCards()[selectedRow][selectedCol];
            secondCard.setFlipped(true);

            // if they don't have the same reference address then compare
            if (firstCard != secondCard) {
                // keep updating the moves label
                int moves = tracker.trackMoves(firstCard, secondCard);
                move.setText("Moves: " + moves);
                compareCards();
            } else {
                secondCard = null; // else take another second card to compare
            }
        } else {
            // else take two others
            firstCard = board.getCards()[selectedRow][selectedCol];
            secondCard = null;
            // update the flip state
            firstCard.setFlipped(true);
        }
    }

    private void compareCards() {
        final int ROW_AND_COL = 4;
        int firstIndex = (firstCard.getRow() * ROW_AND_COL) + firstCard.getColumn();
        int secondIndex = (secondCard.getRow() * ROW_AND_COL) + secondCard.getColumn();

        Button selectedCard1 = (Button) gridPane.getChildren().get(firstIndex);
        Button selectedCard2 = (Button) gridPane.getChildren().get(secondIndex);

        if (firstCard.getName().equals(secondCard.getName())) {
            handleMatchedCards(selectedCard1, selectedCard2);
        } else {
            handleUnmatchedCards(selectedCard1, selectedCard2);
        }
    }

    private void startTimer() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (tracker.timeLimit > 0) {
                    Platform.runLater(() -> {
                        int minutes = tracker.timeLimit > 60 ? 1 : 0;
                        int seconds = tracker.getTimer() - 1;

                        String minutesStr = (minutes == 0) ? "0" : "1";
                        String secondsStr = (seconds > 10) ? String.valueOf(seconds) : "0" + seconds;

                        time.setText(minutesStr + ":" + secondsStr);

                        tracker.timeLimit--;
                        tracker.countDown--;
                        tracker.timePlayed++;
                    });
                } else {
                    timer.cancel();
                    Platform.runLater(() -> {
                        showResult(false);
                    });
                }
            }
        }, 1000, 1000);
    }

    private void showResult(boolean hasWon) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Result.fxml"));
            Parent root = fxmlLoader.load();

            ResultController resultController = fxmlLoader.getController();
            resultController.setResults(hasWon, tracker.timePlayed, tracker.trackMoves(firstCard, secondCard));

            Stage stage = (Stage) gridPane.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

            if (hasWon) Sound.playSound("winning");
            else Sound.playSound("losing");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleMatchedCards(Button card1, Button card2) {
        card1.setDisable(true);
        card2.setDisable(true);
    }

    private void handleUnmatchedCards(Button card1, Button card2) {
        // Flip the cards back to the initial state
        firstCard.setFlipped(false);
        secondCard.setFlipped(false);

        // Play the error sound
        Sound.playSound("error");

        // Shake Animation if they aren't paired
        new Shake(card1).play();
        new Shake(card2).play();

        // Delaying the unpaired cards for 1.2s and then flip them backward
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1.2), ev -> {
            card1.setGraphic(getImage("q", 40, 40));
            card2.setGraphic(getImage("q", 40, 40));
        }));
        timeline.play();
    }

    private ImageView getImage(String imageName, int width, int height) {
        Image image = new Image(Board.class.getResource("images/" + imageName + ".png").toString());
        ImageView view = new ImageView(image);
        view.setFitWidth(width);
        view.setFitHeight(height);
        return view;
    }
}
