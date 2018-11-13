/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierecsys.dal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import movierecsys.be.Rating;
import movierecsys.be.User;

/**
 *
 * @author pgn
 */
public class RatingDAO
{
    private static final String RATING_SOURCE = "data/ratings.txt";
    /**
     * Persists the given rating.
     * @param rating the rating to persist.
     */
    public void createRating(Rating rating)
    {
        //TODO Rate movie
    }
    
    /**
     * Updates the rating to reflect the given object.
     * @param rating The updated rating to persist.
     */
    public void updateRating(Rating rating)
    {
        //TODO Update rating
    }
    
    /**
     * Removes the given rating.
     * @param rating 
     */
    public void deleteRating(Rating rating)
    {
        //TODO Delete rating
    }
    
    /**
     * Gets all ratings from all users.
     * @return List of all ratings.
     */
    public List<Rating> getAllRatings() throws FileNotFoundException, IOException
    {
        List<Rating> allRatings = new ArrayList<>();

        File file = new File(RATING_SOURCE);


        try (BufferedReader reader = new BufferedReader(new FileReader(file)))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                if (!line.isEmpty())
                {
                    try
                    {
                        Rating rat = stringArrayToRating(line);
                        allRatings.add(rat);
                    } catch (Exception ex)
                    {
                        //Do nothing we simply do not accept malformed lines of data.
                        //In a perfect world you should at least log the incident.
                    }
                }
            }
        }
        return allRatings;
    }
    private Rating stringArrayToRating(String t) throws IOException
    {
        String[] arrRating = t.split(",");

        int movieId = Integer.parseInt(arrRating[0]);
        int userId = Integer.parseInt(arrRating[1]);
        int ratingValue = Integer.parseInt(arrRating[2]);
        MovieDAO movieDao = new MovieDAO();
        UserDAO userDao = new UserDAO();
        
        Rating rating = new Rating(movieDao.getMovie(movieId), userDao.getUser(userId), ratingValue);
//        Rating rating = new Rating(movieId, userId, ratingValue);
        return rating;
    }
    /**
     * Get all ratings from a specific user.
     * @param user The user 
     * @return The list of ratings.
     */
    public List<Rating> getRatings(User user) throws FileNotFoundException, IOException
    {
        File file = new File(RATING_SOURCE);
        List<Rating> allRatings = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file)))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                if (!line.isEmpty())
                {
                    try
                    {
                        Rating rat = stringArrayToRating(line);
                        String[] arrRating = line.split(",");
                        int userId = Integer.parseInt(arrRating[1]);
                        if (user.getId()==userId)
                        {
                            allRatings.add(rat);
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
