package de.eric.movies;

import de.eric.movies.movie.MySQLConnection;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Movies extends Application {

    private static MySQLConnection mySQLConnection = null;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Movies.class.getResource("movies-main.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 840, 600);

        stage.setTitle("Movies");

        stage.setResizable(false);

        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {
        try {
            mySQLConnection = new MySQLConnection("localhost", "3306", "movies", "root", "");
            mySQLConnection.create();
        }catch(Exception e){
            e.printStackTrace();
        }
        launch();
    }
    public static MySQLConnection getMySQLConnection() {
        return mySQLConnection;
    }
}
