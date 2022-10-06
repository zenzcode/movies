package de.eric.movies.movie;

import de.eric.movies.Movies;
import de.eric.movies.movie.category.Category;

import java.net.URL;

public class MovieModel {
    public String title;
    public String description;
    public int ratingStars;
    public Category category;

    public URL imagePath = Movies.class.getResource("placeholder-image.png");
}
