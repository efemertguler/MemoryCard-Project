module com.memorycards {
    requires javafx.controls;
    requires javafx.fxml;
    requires AnimateFX;
    requires javafx.media;

    opens com.memorycards to javafx.fxml;
    exports com.memorycards;
}