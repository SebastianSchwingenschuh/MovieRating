module com.example.movieratings {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.movieratings to javafx.fxml;
    exports com.example.movieratings;
}