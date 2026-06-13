package com.example.movieratings.controller;

import com.example.movieratings.model.MovieRating;
import com.example.movieratings.service.MovieRatingService;
import javafx.application.Platform;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
    private final MovieRatingService service = MovieRatingService.getInstance();

    @FXML private void initialize() {
        tbcTitle.setCellValueFactory(param -> param.getValue().titleProperty());
        tbcYear.setCellValueFactory(param -> param.getValue().yearProperty().asObject());
        tbcRating.setCellValueFactory(param -> param.getValue().ratingProperty().asObject());

        this.filteredMovies = new FilteredList<>(service.getMovieRatings());
        SortedList<MovieRating> sortedMovies = new SortedList<>(this.filteredMovies);

        sortedMovies.comparatorProperty().bind(this.tbvMovies.comparatorProperty());
        this.tbvMovies.setItems(sortedMovies);

        this.lblMovieCount.textProperty().bind(service.movieCountProperty().asString());
        this.lblAverageRating.textProperty().bind(service.avgRatingProperty().asString("%.2f"));

        this.sldYearFilter.minProperty().bind(service.minYearProperty());
        this.sldYearFilter.maxProperty().bind(service.maxYearProperty());
        this.sldYearFilter.setShowTickLabels(true);
        this.sldYearFilter.setShowTickMarks(true);
        this.sldYearFilter.setMajorTickUnit(10);
        this.sldYearFilter.setMinorTickCount(5);
        this.sldYearFilter.setSnapToTicks(false);

        this.sldYearFilter.valueProperty().addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> {
                int minYear = newValue.intValue();
                this.filteredMovies.setPredicate(movieRating -> movieRating.getYear() >= minYear);
            });
        });
    }

    public void handleAddNewRating(ActionEvent actionEvent) {
        try {
            String title = txtTitle.getText();
            int year = Integer.parseInt(txtYear.getText());
            double rating = Double.parseDouble(txtRating.getText());

            service.createMovieRating(title, year, rating);

            txtTitle.clear();
            txtYear.clear();
            txtRating.clear();
        } catch (NumberFormatException ex){
            showAlert("Eingabefehler", "Ungültige Zahlen eingegeben");
        } catch (IllegalArgumentException ex){
            showAlert("Ungültige Daten", ex.getMessage());
        }
    }

    private void showAlert(String title, String msg){
        Alert alert = new Alert(Alert.AlertType.ERROR, msg);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public void btnHandleRemoveSelected(ActionEvent actionEvent) {
        MovieRating selected = tbvMovies.getSelectionModel().getSelectedItem();
        if(selected != null){
            try {
                service.removeMovieRating(selected);
            } catch (Exception ex){
                showAlert("Fehler bim Löschen", ex.getMessage());
            }
        } else {
            showAlert("Keine Auswhal", "Kein Film zum löschen ausgewählt");
        }
    }
}
