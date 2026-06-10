package com.example.movieratings.model;

public class MovieRating {
    private  int id;
    private String title;
    private int year;
    private double rating;

    public MovieRating(String title, int year, double rating) {
        this.setTitle(title);
        this.setYear(year);
        this.setRating(rating);
    }

    public String getTitle() {
        return title;
    }

    private void setTitle(String title) {
        if(title == null || title.isEmpty()){
            throw new IllegalArgumentException("title must not be null or empty");
        }
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    private void setYear(int year) {
        if(year <= 1888){
            throw new IllegalArgumentException("there is no older film than 1888");
        }
        this.year = year;
    }

    public double getRating() {
        return rating;
    }

    private void setRating(double rating) {
        if(rating < 1.0 || rating > 5.0){
            throw new IllegalArgumentException("rating must be between 1.0 and 5.0");
        }
        this.rating = rating;
    }
}
