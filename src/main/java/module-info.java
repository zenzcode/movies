module de.eric.movies {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens de.eric.movies to javafx.fxml;
    exports de.eric.movies;
    exports de.eric.movies.movie;
    exports de.eric.movies.movie.movieDetails;
}