/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierecsys.dal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import movierecsys.be.Movie;

/**
 *
 * @author pgn
 */
public class MovieDAO
{

    private static final String MOVIE_SOURCE = "data/movie_titles.txt";

    /**
     * Gets a list of all movies in the persistence storage.
     *
     * @return List of movies.
     */
    public List<Movie> getAllMovies() throws IOException
    {
        List<Movie> allMovies = new ArrayList<>();
        File file = new File(MOVIE_SOURCE);

        try (BufferedReader reader = new BufferedReader(new FileReader(file)))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                if (!line.isEmpty())
                {
                    try
                    {
                        Movie mov = stringArrayToMovie(line);
                        allMovies.add(mov);
                    } catch (Exception ex)
                    {
                        //Do nothing we simply do not accept malformed lines of data.
                        //In a perfect world you should at least log the incident.
                    }
                }
            }
        }
        return allMovies;
    }
    private Movie stringArrayToMovie(String t)
    {
        String[] arrMovie = t.split(",");

        int id = Integer.parseInt(arrMovie[0]);
        int year = Integer.parseInt(arrMovie[1]);
        String title = arrMovie[2];

        Movie mov = new Movie(id, year, title);
        return mov;
    }

    /**
     * Creates a movie in the persistence storage.
     *
     * @param releaseYear The release year of the movie
     * @param title The title of the movie
     * @return The object representation of the movie added to the persistence
     * storage.
     */
    public Movie createMovie(int releaseYear, String title) throws IOException
    {
        Path path = new File(MOVIE_SOURCE).toPath();
        int id = -1;
        try (BufferedWriter bw = Files.newBufferedWriter(path, StandardOpenOption.SYNC, StandardOpenOption.APPEND, StandardOpenOption.WRITE))
        {
            id = getNextAvailableMovieID();
            bw.newLine();
            bw.write(id + "," + releaseYear + "," + title);
        }
        return new Movie(id, releaseYear, title);
    }
    int getNextAvailableMovieID() throws IOException
    {
        int id = 0;
        int counter = 1;
        List<Movie> allMovies = getAllMovies();
        for (int i = 0; i < allMovies.size(); i++)
        {
            
            if (allMovies.get(i).getId()!= counter)
            {
                id = counter;
                return id;
            }
            counter++;
        }
        int nextId = counter + 1;
        
        return nextId;

    }

    /**
     * Deletes a movie from the persistence storage.
     *
     * @param movie The movie to delete.
     */
    public void deleteMovie(Movie movie) throws IOException
    {
        File tmp = new File("data/tmp_movies.txt");
        List<Movie> allMovies = getAllMovies();
        allMovies.removeIf((Movie t) -> t.getId() == movie.getId());
        Collections.sort(allMovies, (Movie o1, Movie o2) -> Integer.compare(o1.getId(), o2.getId()));
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(tmp)))
        {
            for (Movie mov : allMovies)
            {
                bw.write(mov.getId() + "," + mov.getYear() + "," + mov.getTitle());
                bw.newLine();
            }
        }
        Files.copy(tmp.toPath(), new File(MOVIE_SOURCE).toPath(), StandardCopyOption.REPLACE_EXISTING);
        Files.delete(tmp.toPath());
        /**
         * this was the way I tried to do it, but it didn't work
         * it would never go into my for loop
        List<Movie> allMovies = getAllMovies();
        try 
        {
            for (Movie mov : allMovies) 
            {
                if (movie.getId()==mov.getId())
                {
                    allMovies.remove(mov.getId());
                }
            }
        } catch (Exception ex) 
        {
            System.out.println("error 1");
        }

        

        File tmp = new File("data/tmp_movies.txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(tmp)))
        {
            for (Movie allMovy : allMovies) 
            {
                bw.write(allMovy.getId() + "," + allMovy.getYear() + "," + allMovy.getTitle());
                bw.newLine();
            }
        }
        Files.copy(tmp.toPath(), new File(MOVIE_SOURCE).toPath(), StandardCopyOption.REPLACE_EXISTING);
        Files.delete(tmp.toPath());
        */
    }

    /**
     * Updates the movie in the persistence storage to reflect the values in the
     * given Movie object.
     *
     * @param movie The updated movie.
     */
    public void updateMovie(Movie movie) throws IOException
    {
        File tmp = new File("data/tmp_movies.txt");
        List<Movie> allMovies = getAllMovies();
        allMovies.removeIf((Movie t) -> t.getId() == movie.getId());
        allMovies.add(movie);
        Collections.sort(allMovies, (Movie o1, Movie o2) -> Integer.compare(o1.getId(), o2.getId()));
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(tmp)))
        {
            for (Movie mov : allMovies)
            {
                bw.write(mov.getId() + "," + mov.getYear() + "," + mov.getTitle());
                bw.newLine();
            }
        }
        Files.copy(tmp.toPath(), new File(MOVIE_SOURCE).toPath(), StandardCopyOption.REPLACE_EXISTING);
        Files.delete(tmp.toPath());
    }

    /**
     * Gets a the movie with the given ID.
     *
     * @param id ID of the movie.
     * @return A Movie object.
     */
    public Movie getMovie(int id) throws FileNotFoundException, IOException
    {
        File file = new File(MOVIE_SOURCE);

        try (BufferedReader reader = new BufferedReader(new FileReader(file)))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                if (!line.isEmpty())
                {
                    try
                    {
                        Movie mov = stringArrayToMovie(line);
                        if (mov.getId()==id)
                        {
                            return mov;
                        }                
                    } catch (Exception ex)
                    {
                        //Do nothing we simply do not accept malformed lines of data.
                        //In a perfect world you should at least log the incident.
                    }
                }
            }
        }
        return null;
    }

}
