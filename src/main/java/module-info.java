module com.example.movieratings {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    // Öffnet das Hauptpaket für FXML (für die Root-View)
    opens com.example.movieratings to javafx.fxml;
    exports com.example.movieratings;

    // NEU: Öffnet das Controller-Paket für FXML
    opens com.example.movieratings.controller to javafx.fxml;

    // Falls du auch ein Model-Paket hast, das per Reflection geladen wird:
    // opens com.example.movieratings.model to javafx.base;
}