package com.memorycards;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Sound {
    public static void playSound(String sound) {
        Media media = new Media(Sound.class.getResource("sounds/" + sound + ".mp3").toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }
}
