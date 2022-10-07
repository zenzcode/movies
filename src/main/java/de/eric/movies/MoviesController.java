package de.eric.movies;

import de.eric.movies.movie.MovieController;
import de.eric.movies.movie.MovieModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MoviesController {

    @FXML
    public TextField searchBar;

    @FXML
    public ScrollPane moviesPane;

    private int currentOffset = 0;

    private int moviesPerScroll = 10;

    private List<MovieModel> loadedMovies;

    private boolean bLoadingNewMovies = false;

    @FXML
    public void initialize() throws IOException {
        loadedMovies = new ArrayList<>();
        try {
            bLoadingNewMovies = true;
            loadNewMovies();
            currentOffset+=moviesPerScroll;
            renderMovies();
            bLoadingNewMovies = false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        moviesPane.vvalueProperty().addListener((obs, oldValue, newValue) -> {
            if(newValue.doubleValue() == 1.f && !bLoadingNewMovies) {
                bLoadingNewMovies = true;
                new Thread(() -> Platform.runLater(() -> {
                    try {
                        loadNewMovies();
                        currentOffset += moviesPerScroll;
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch(IOException e) {
                        throw new RuntimeException(e);
                    }
                })).start();
            } else if(newValue.doubleValue() == 0.f && !bLoadingNewMovies)
            {
                bLoadingNewMovies = true;
                new Thread(() -> Platform.runLater(() -> {
                    try {
                        currentOffset -= moviesPerScroll;
                        if(currentOffset < 0 )
                        {
                            currentOffset = 0;
                        }
                        loadNewMovies();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })).start();
            }
        });
    }

    public void searchStart() {

    }

    private void renderMovies() throws IOException {
        VBox movies = new VBox();
        moviesPane.setContent(null);
        for(int i = 0; i < loadedMovies.size(); ++i)
        {
            MovieModel movieModel = loadedMovies.get(i);
            FXMLLoader loader = new FXMLLoader(Movies.class.getResource("movie-list-entry.fxml"));
            AnchorPane anchorPane = (AnchorPane) loader.load();
            MovieController controller = loader.getController();
            controller.initModel(movieModel);
            movies.getChildren().add(anchorPane);
        }
        movies.setSpacing(10);
        moviesPane.setContent(movies);
    }

    private void loadNewMovies() throws SQLException, IOException{
        System.out.printf("LOADING MOVIES FROM OFFSET %d \n", currentOffset);
        List<MovieModel> temporaryMovieModels = Movies.getMySQLConnection().getMoviesWithLimit(moviesPerScroll, currentOffset - 1 < 0 ? 0 : currentOffset);
        loadedMovies.clear();
        loadedMovies.addAll(temporaryMovieModels);
        renderMovies();
        moviesPane.setVvalue(0);
        bLoadingNewMovies = false;
    }

}
