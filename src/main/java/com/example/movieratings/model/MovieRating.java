package com.example.movieratings.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Objects;

public class MovieRating {
    private SimpleStringProperty title;
    private SimpleIntegerProperty year;
    private SimpleDoubleProperty rating;

    public MovieRating(String title, int year, double rating) {
        this.title = new SimpleStringProperty();
        this.year = new SimpleIntegerProperty();
        this.rating = new SimpleDoubleProperty();

        this.setTitle(title);
        this.setYear(year);
        this.setRating(rating);
    }

    public String getTitle() {
        return title.get();
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        if(title == null || title.isEmpty()){
            throw new IllegalArgumentException("title must not be null or empty");
        }
        this.title.set(title);
    }

    public int getYear() {
        return year.get();
    }

    public SimpleIntegerProperty yearProperty() {
        return year;
    }

    public void setYear(int year) {if(year <= 1888){
            throw new IllegalArgumentException("there is no older film than 1888");
        }
        this.year.set(year);
    }

    public double getRating() {
        return rating.get();
    }

    public SimpleDoubleProperty ratingProperty() {
        return rating;
    }

    public void setRating(double rating) {
        if(rating < 1.0 || rating > 5.0){
            throw new IllegalArgumentException("rating must be between 1.0 and 5.0");
        }
        this.rating.set(rating);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MovieRating that = (MovieRating) o;
        return year.get() == that.year.get() && Objects.equals(title.get(), that.title.get());
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, year);
    }
}
