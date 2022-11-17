package de.eric.movies.movie.movieDetails;

import de.eric.movies.Movies;
import de.eric.movies.MoviesController;
import de.eric.movies.movie.MovieController;
import de.eric.movies.movie.MovieModel;
import de.eric.movies.movie.category.Category;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;

import java.io.IOException;

public class MovieDetailsController {

    private MovieModel movieModel;

    @FXML
    public TextFlow movieLongText;

    @FXML
    public Label movieTitle;

    @FXML
    public Label movieCategory;

    @FXML
    public Label movieLengthValue;
    @FXML
    public Label movieCostValue;
    @FXML
    public Label movieReleaseValue;
    @FXML
    public ImageView movieImage;

    @FXML
    public Rating movieRating;

    public void initModel(MovieModel model) {
        this.movieModel = model;

        String URL = Movies.class.getResource("Picture/"+ model.picturenname).toString();
        Image bild = new Image(URL);
        Text descriptionText = new Text(model.description);
        descriptionText.setFill(Color.WHITE);

        this.movieLongText.getChildren().add(descriptionText);
        this.movieTitle.setText(model.title);
        this.movieCategory.setText(model.category.name());
        this.movieLengthValue.setText(Integer.toString(model.length));
        this.movieCostValue.setText(Double.toString(model.price));
        this.movieReleaseValue.setText(Integer.toString(model.year));
        this.movieRating.setRating(model.ratingStars);
        this.movieImage.setImage(bild);
    }

    /**
     *  Goes back to main page
     */
    public void returnMainPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(Movies.class.getResource("movies-main.fxml"));
        Scene scene = new Scene(loader.load(), 840, 600);

        MoviesController moviesController = loader.getController();

        moviesController.initialize();

        Stage stage = (Stage) movieTitle.getScene().getWindow();

        stage.setTitle("Movies");

        stage.setResizable(false);

        stage.setScene(scene);

        stage.show();
    }
}
