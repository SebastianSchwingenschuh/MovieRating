package com.example.movieratings.service;

import com.example.movieratings.model.MovieRating;
import com.example.movieratings.repository.MovieRatingRepository;
import javafx.beans.Observable;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.LinkedList;

public class MovieRatingService {
    private static final int INIT_MIN_YEAR = 1888;
    private static final int INIT_MAX_YEAR = 2030;

    private static MovieRatingService instance;

    public static MovieRatingService getInstance(){
        if(instance == null){
            instance = new MovieRatingService();
        }
        return instance;
    }

    private SimpleIntegerProperty movieCount;
    private SimpleDoubleProperty avgRating;
    private SimpleIntegerProperty minYear = new SimpleIntegerProperty(INIT_MIN_YEAR);
    private SimpleIntegerProperty maxYear = new SimpleIntegerProperty(INIT_MAX_YEAR);
    ObservableList<MovieRating> movieRatings;

    public int getMovieCount() {
        return movieCount.get();
    }

    public SimpleIntegerProperty movieCountProperty() {
        return movieCount;
    }

    public double getAvgRating() {
        return avgRating.get();
    }

    public SimpleDoubleProperty avgRatingProperty() {
        return avgRating;
    }

    public int getMaxYear() {
        return maxYear.get();
    }

    public SimpleIntegerProperty maxYearProperty() {
        return maxYear;
    }

    public int getMinYear() {
        return minYear.get();
    }

    public SimpleIntegerProperty minYearProperty() {
        return minYear;
    }

    private MovieRatingService() {
        this.movieRatings = FXCollections.observableList(new LinkedList<>());

        this.movieCount = new SimpleIntegerProperty();
        this.avgRating = new SimpleDoubleProperty();

        this.movieRatings.addListener((ListChangeListener<? super MovieRating>) m -> {
            this.refreshStatistics();
        });
        
        this.reloadMovieRatings();
    }

    private void refreshStatistics() {
        this.movieCount.set(this.movieRatings.size());
        if(!this.movieRatings.isEmpty()) {
            this.avgRating.set(this.movieRatings.stream()
                    .mapToDouble(MovieRating::getRating)
                    .average()
                    .orElse(0.0)
            );

            this.minYear.set(this.movieRatings.stream()
                    .mapToInt(MovieRating::getYear)
                    .min()
                    .orElse(INIT_MIN_YEAR)
            );

            this.maxYear.set(this.movieRatings.stream()
                    .mapToInt(MovieRating::getYear)
                    .max()
                    .orElse(INIT_MAX_YEAR)
            );
        } else {
            this.avgRating.set(0.0);
            this.minYear.set(INIT_MIN_YEAR);
            this.maxYear.set(INIT_MAX_YEAR);
        }
    }

    private void reloadMovieRatings() {
        this.movieRatings.clear();
        this.movieRatings.addAll(MovieRatingRepository.getInstance().findAll());
    }

    public ObservableList<MovieRating> getMovieRatings() {
        return FXCollections.unmodifiableObservableList(movieRatings);
    }

    public void createMovieRating(String title, int year, double rating) {
        MovieRatingRepository.getInstance().save(new MovieRating(title, year, rating));
        this.reloadMovieRatings();
    }

    public void removeMovieRating(MovieRating movieRating) {
        MovieRatingRepository.getInstance().delete(movieRating);
        this.reloadMovieRatings();
    }
}
