package de.eric.movies.movie.movieDetails;

import de.eric.movies.movie.MovieModel;
import de.eric.movies.movie.category.Category;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import org.controlsfx.control.Rating;

public class MovieDetailsController {

    private MovieModel movieModel;

    @FXML
    public TextFlow movieLongText;

    @FXML
    public Label movieTitle;

    @FXML
    public Label movieCategory;

    @FXML
    public Rating movieRating;

    public void initModel(MovieModel model) {
        this.movieModel = model;

        Text descriptionText = new Text(model.description);
        descriptionText.setFill(Color.WHITE);

        this.movieLongText.getChildren().add(descriptionText);
        this.movieTitle.setText(model.title);
        this.movieCategory.setText(model.category.name());
        this.movieRating.setRating(model.ratingStars);
    }
}
