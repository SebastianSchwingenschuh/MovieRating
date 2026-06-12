package com.example.movieratings.repository;

import com.example.movieratings.model.MovieRating;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieRatingRepository {
    private static String DATABASE_URL = "jdbc:h2:tcp://localhost:9092/./default";

    MovieRatingRepository instance;

    public MovieRatingRepository() {
    }

    public MovieRatingRepository getInstance(){
        if(instance == null){
            instance = new MovieRatingRepository();
        }
        return instance;
    }

    public List<MovieRating> findAll () {
        List<MovieRating> result = new ArrayList<>();

        try(Connection connection = DriverManager.getConnection(DATABASE_URL)) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery("select * from movie")){
                    while (resultSet.next()){
                        MovieRating movieRating = new MovieRating(
                                resultSet.getString("title"),
                                resultSet.getInt("year"),
                                resultSet.getDouble("rating")
                        );
                        result.add(movieRating);
                    }
                }
            }

        } catch (SQLException e){
            throw new RuntimeException(e);
        }

        return result;
    }

    public void save(MovieRating movieRating){
        try (Connection connection = DriverManager.getConnection(DATABASE_URL)){
            try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO  movie (title, year, rating) values (?, ?, ?)")) {
                preparedStatement.setString(1, movieRating.getTitle());
                preparedStatement.setInt(2, movieRating.getYear());
                preparedStatement.setDouble(3, movieRating.getRating());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
//  in diesem bsp war kein update gefordert
//    public void update(Contact contact) {
//        try (Connection connection = DriverManager.getConnection(DATABASE_URL)) {
//            try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE contact SET NAME = ?, EMAIL = ?, AGE = ?, IS_BUSINESS = ? WHERE ID = ?")) {
//                preparedStatement.setString(1, contact.getName());
//                preparedStatement.setString(2, contact.getEmail());
//                preparedStatement.setInt(3, contact.getAge());
//                preparedStatement.setBoolean(4, contact.isBusinessContact());
//                preparedStatement.setLong(5, contact.getId());
//                preparedStatement.executeUpdate();
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public void delete(MovieRating movieRating){
        try ( Connection connection = DriverManager.getConnection(DATABASE_URL)){
            try (PreparedStatement preparedStatement = connection.prepareStatement("delete from movie where tite = ? and year = ?")) {
                preparedStatement.setString(1, movieRating.getTitle());
                preparedStatement.setInt(2, movieRating.getYear());
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }


    //bei test allerding wahrscheinlich mit id
    public void delete(Long id) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL)) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM contact WHERE ID = ?")) {
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
