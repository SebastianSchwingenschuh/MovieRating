package com.example.movieratings.repository;

import com.example.movieratings.model.MovieRating;

public class MovieRatingRepository {
    MovieRatingRepository instance;

    public MovieRatingRepository() {
    }

    public MovieRatingRepository getInstance(){
        if(instance == null){
            instance = new MovieRatingRepository();
        }
        return instance;
    }


}
