package de.eric.movies.movie.movieDetails;

import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

public class MovieDetailsController {


    @FXML
    public TextFlow movieLongText;

    public void initialize() {
        Text text1 = new Text("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.");
        text1.setFill(Color.WHITE);

        this.movieLongText.getChildren().add(text1);
    }
}
