module com.example {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    opens com.example to javafx.fxml;
    exports com.example;
}