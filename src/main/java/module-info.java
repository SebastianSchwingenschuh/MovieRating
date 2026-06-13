module com.example.movieratings {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    // Füge diese Zeile hier ein:
    uses java.sql.Driver;

    opens com.example.movieratings.controller to javafx.fxml;

    exports com.example.movieratings;
}