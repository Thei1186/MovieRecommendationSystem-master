/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierecsys.dal.MRSinterfaces;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import movierecsys.be.Rating;

/**
 *
 * @author kokus
 */
public interface IRatingRepository
{

    /**
     * Persists the given rating.
     *
     * @param rating the rating to persist.
     */
    void createRating(Rating rating) throws FileNotFoundException, IOException;

    /**
     * Removes the given rating.
     *
     * @param rating
     */
    void deleteRating(Rating rating) throws FileNotFoundException, IOException;

    /**
     * Gets all ratings from all users.
     *
     * @return List of all ratings.
     */
    List<Rating> getAllRatings() throws IOException;

    /**
     * Updates the rating to reflect the given object. Assumes that the source file is order by movie ID, then User ID..
     *
     * @param rating The updated rating to persist.
     * @throws java.io.IOException
     */
    //    public void updateRating1(Rating rating) throws IOException
    //    {
    //        File tmp = new File("data/tmp_ratings.txt");
    //        List<Rating> allRatings = getAllRatings();
    //        allRatings.removeIf((Rating t) -> t.getMovie() == rating.getMovie() && t.getUser() == rating.getUser());
    //        allRatings.add(rating);
    //        Collections.sort(allRatings, (Rating o1, Rating o2) ->
    //        {
    //            int movieCompare = Integer.compare(o1.getMovie(), o2.getMovie());
    //            return movieCompare == 0 ? Integer.compare(o1.getUser(), o2.getUser()) : movieCompare;
    //        });
    //        try (BufferedWriter bw = new BufferedWriter(new FileWriter(tmp)))
    //        {
    //            for (Rating rat : allRatings)
    //            {
    //                bw.write(rat.getMovie() + "," + rat.getUser()+ "," + rat.getRating());
    //                bw.newLine();
    //
    //            }
    //        }
    //        Files.copy(tmp.toPath(), new File(R_SOURCE).toPath(), StandardCopyOption.REPLACE_EXISTING);
    //        Files.delete(tmp.toPath());
    //        //make it into a file where it is in bytes, I don't remember how to do it atm.
    //
    //
    //    }
    void updateRating(Rating rating) throws IOException;
    
}
