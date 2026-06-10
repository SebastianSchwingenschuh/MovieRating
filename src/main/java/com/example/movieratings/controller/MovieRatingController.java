package com.example.movieratings.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MovieRatingController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
