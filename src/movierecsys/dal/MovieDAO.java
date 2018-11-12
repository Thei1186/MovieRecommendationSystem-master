/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierecsys.dal;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import movierecsys.be.Movie;

/**
 *
 * @author pgn
 */
public class MovieDAO
{

    private static final String SOURCE = "data/movie_titles.txt";

    /**
     * Gets a list of all movies in the persistence storage.
     *
     * @return List of movies.
     */
    public List<Movie> getAllMovies() throws IOException
    {
        File file = new File(SOURCE);
        List<Movie> allMovies = new ArrayList<>();
        List<String> lines = Files.readAllLines(file.toPath());
        for (String line : lines)
        {
            String[] columns = line.split(",");
            int id = Integer.parseInt(columns[0]);
            int year = Integer.parseInt(columns[1]);
            String title = columns[2];
            Movie movie = new Movie(id, year, title);
            allMovies.add(movie);
        }

        return allMovies;

    }

    /**
     * Creates a movie in the persistence storage.
     *
     * @param releaseYear The release year of the movie
     * @param title The title of the movie
     * @return The object representation of the movie added to the persistence
     * storage.
     */
    public Movie createMovie(int releaseYear, String title)
    {
        //TODO Create movie.
        return null;
    }

    /**
     * Deletes a movie from the persistence storage.
     *
     * @param movie The movie to delete.
     */
    public void deleteMovie(Movie movie)
    {
        //TODO Delete movie
    }

    /**
     * Updates the movie in the persistence storage to reflect the values in the
     * given Movie object.
     *
     * @param movie The updated movie.
     */
    public void updateMovie(Movie movie)
    {
        //TODO Update movies
    }

    /**
     * Gets a the movie with the given ID.
     *
     * @param id ID of the movie.
     * @return A Movie object.
     */
    public Movie getMovie(int id)
    {
        //TODO Get one Movie
        return null;
    }

}
