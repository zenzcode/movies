package de.eric.movies;

import de.eric.movies.movie.MovieController;
import de.eric.movies.movie.MovieModel;
import de.eric.movies.util.DelayManager;
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
import java.util.stream.Collectors;

public class MoviesController {

    @FXML
    public TextField searchBar;

    @FXML
    public ScrollPane moviesPane;

    private int currentOffset = 0;

    private int moviesPerScroll = 10;

    private List<MovieModel> allMovies;
    private List<MovieModel> loadedMovies;
    private List<MovieModel> filteredMovies;


    private boolean bLoadingNewMovies = false;

    private Thread thread = null;

    private boolean bFromSearch = false;

    @FXML
    public void initialize() throws IOException {
        loadedMovies = new ArrayList<>();
        filteredMovies = new ArrayList<>();
        allMovies = new ArrayList<>();
        try {
            bLoadingNewMovies = true;
            loadNewMovies();
            renderMovies();
            bLoadingNewMovies = false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        /*
         * Binds to Scrolling to load new movies
         */
        moviesPane.vvalueProperty().addListener((obs, oldValue, newValue) -> {
            if(bFromSearch) {
                bFromSearch = false;
                return;
            }
            if(newValue.doubleValue() == 1.f && !bLoadingNewMovies) {
                bLoadingNewMovies = true;
                new Thread(() -> Platform.runLater(() -> {
                    try {
                        currentOffset += moviesPerScroll;
                        loadNewMovies();
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
        String requestedSearch = searchBar.getText().trim();

        if(thread != null && thread.isAlive())
        {
            thread.stop();
        }

        thread = DelayManager.delay(1000, () -> {
            if(requestedSearch.isEmpty())
            {
                filteredMovies.addAll(loadedMovies);
                try {
                    renderMovies();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if(requestedSearch.length() > 2) {
                System.out.printf("LOADING MOVIES \n");
                filteredMovies = allMovies.stream().filter(movie -> movie.title.toLowerCase().contains(requestedSearch.toLowerCase()) || movie.category.name().toLowerCase().contains(requestedSearch.toLowerCase())).collect(Collectors.toList());
                try {
                    renderMovies();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    /***
     * Renders Movies into VBox
     */
    private void renderMovies() throws IOException {
        VBox movies = new VBox();
        moviesPane.setContent(null);
        for(int i = 0; i < filteredMovies.size(); ++i)
        {
            MovieModel movieModel = filteredMovies.get(i);
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
        List<MovieModel> temporaryAllMovieModels = Movies.getMySQLConnection().getMovies();
        loadedMovies.clear();
        loadedMovies.addAll(temporaryMovieModels);
        allMovies.clear();
        allMovies.addAll(temporaryAllMovieModels);
        filteredMovies.clear();
        filteredMovies.addAll(loadedMovies);
        renderMovies();
        moviesPane.setVvalue(0);
        bLoadingNewMovies = false;
    }

}
