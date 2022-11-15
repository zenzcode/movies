package de.eric.movies.movie;

import de.eric.movies.Movies;
import de.eric.movies.movie.movieDetails.MovieDetailsController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.controlsfx.control.Rating;

import java.io.IOException;

public class MovieController {

    @FXML
    public Label movieTitle;

    @FXML
    public Label movieCategory;

    @FXML
    public Rating movieRating;

    @FXML
    public ImageView movieImage;


    private MovieModel movie;

    public void initModel(MovieModel movie) {
        if(movie == null)
        {
            throw new RuntimeException();
        }
        this.movie = movie;
        movieTitle.setText(movie.title);
        movieCategory.setText(movie.category.name());
        movieRating.setRating(movie.ratingStars);
        movieImage.setImage(new Image(movie.imagePath.toString()));
    }

    /**
     * Opens a detail page for a movie
     */
    public void openDetailsPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(Movies.class.getResource("movie-details.fxml"));
        Scene scene = new Scene(loader.load(), 840, 600);

        MovieDetailsController movieDetailsController = loader.getController();

        movieDetailsController.initModel(movie);

        Stage stage = (Stage) movieTitle.getScene().getWindow();

        stage.setTitle(movie.title + " - Details");

        stage.setScene(scene);
    }
}
