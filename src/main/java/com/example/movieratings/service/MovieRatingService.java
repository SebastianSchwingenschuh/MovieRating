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
    private static MovieRatingService instance;

    public MovieRatingService getInstance(){
        if(instance == null){
            instance = new MovieRatingService();
        }
        return instance;
    }

    private SimpleIntegerProperty movieCount;
    private SimpleDoubleProperty avgRating;
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

    public MovieRatingService() {
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
        this.avgRating.set(this.movieRatings.stream()
                .mapToDouble(MovieRating::getRating)
                .average()
                .orElse(0.0)
        );
    }

    private void reloadMovieRatings() {
        this.movieRatings.clear();
        this.movieRatings.addAll(MovieRatingRepository.getInstance().findAll());
    }

    public void createMovieRating(String title, int year, double rating) {
        MovieRatingRepository.getInstance().save(new MovieRating(title, year, rating));
        this.reloadMovieRatings();
    }

    public void removeMovieRating(String title, int year, double rating) {
        MovieRatingRepository.getInstance().delete(new MovieRating(title, year, rating));
        this.reloadMovieRatings();
    }

}
