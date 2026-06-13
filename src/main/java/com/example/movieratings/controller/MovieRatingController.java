package com.example.movieratings.controller;

import com.example.movieratings.model.MovieRating;
import com.example.movieratings.service.MovieRatingService;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MovieRatingController {

    @FXML private Label lblMovieCount;
    @FXML private Label lblAverageRating;
    @FXML private Slider sldYearFilter;
    @FXML private TableView<MovieRating> tbvMovies;
    @FXML private TableColumn<MovieRating, String> tbcTitle;
    @FXML private TableColumn<MovieRating, Integer> tbcYear;
    @FXML private TableColumn<MovieRating, Double> tbcRating;
    @FXML private TextField txtTitle;
    @FXML private TextField txtYear;
    @FXML private TextField txtRating;
    @FXML private Button btnAddNewRating;
    @FXML private Button btnRemoveSelected;

    private FilteredList<MovieRating> filteredMovies;

    @FXML private void initialize() {
        MovieRatingService service = MovieRatingService.getInstance();

        tbcTitle.setCellValueFactory(param -> param.getValue().titleProperty());
        tbcYear.setCellValueFactory(param -> param.getValue().yearProperty().asObject());
        tbcRating.setCellValueFactory(param -> param.getValue().ratingProperty().asObject());

        this.filteredMovies = new FilteredList<>(service.getMovieRatings());
    }

    public void handleAddNewRating(ActionEvent actionEvent) {
    }

    public void btnHandleRemoveSelected(ActionEvent actionEvent) {
    }
}
