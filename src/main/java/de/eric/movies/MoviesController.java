package de.eric.movies;

import de.eric.movies.movie.MovieController;
import de.eric.movies.movie.MovieModel;
import de.eric.movies.movie.category.Category;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Random;

public class MoviesController {

    @FXML
    public TextField searchBar;

    @FXML
    public ScrollPane moviesPane;

    @FXML
    public void initialize() throws IOException {
        VBox movies = new VBox();

        for(int i = 0; i < 100; ++i)
        {
            MovieModel movieModel = new MovieModel();
            movieModel.title = "Ein toller Film der einen zum glück nicht übertrieben langen Titel hat";

            Random random = new Random();

            movieModel.category = Category.values()[random.nextInt(Category.values().length)];
            movieModel.description = "Ein test";

            movieModel.ratingStars = random.nextInt(5) + 1;
            FXMLLoader loader = new FXMLLoader(Movies.class.getResource("movie-list-entry.fxml"));
            AnchorPane anchorPane = (AnchorPane) loader.load();
            MovieController controller = loader.getController();
            controller.initModel(movieModel);
            movies.getChildren().add(anchorPane);
        }
        movies.setSpacing(10);
        moviesPane.setContent(movies);
    }

    public void searchStart() {

    }

}
