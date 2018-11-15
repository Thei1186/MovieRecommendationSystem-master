/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierecsys.dal;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;
import movierecsys.be.Movie;
import movierecsys.be.Rating;
import movierecsys.be.User;

/**
 *
 * @author pgn
 */
public class FileReaderTester
{

    /**
     * Example method. This is the code I used to create the users.txt files.
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException
    {
        //movieDaoTester();
        //userDaoTester();
        ratingDaoTester();
    }
    public static void ratingDaoTester() throws IOException
    {
        System.out.println("start");
        RatingDAO ratingDao = new RatingDAO();
        List<Rating> allRatings = ratingDao.getAllRatings();
        System.out.println(allRatings.size());
//        for (Rating rating : allRatings) 
//        {
//            System.out.println(rating.getRating() + "stars for " + rating.getMovie().getTitle());
//        }        
        for (int i = 0; i < 1000; i++) 
        {
            System.out.println(allRatings.get(i).getRating() + "stars for " + allRatings.get(i).getMovie().getTitle());
        }
//        List<Rating> userRating = ratingDao.getRatings(new User(7, "Georgi Facello"));
//        for (Rating rating : userRating)
//        {
//            System.out.println(rating.getRating() + "stars for " + rating.getMovie().getTitle());
//        }
        System.exit(0);
        
    }
    public static void userDaoTester() throws IOException
    {
        UserDAO userDao = new UserDAO();
        List<User> allUsers = userDao.getAllUsers();
        for (User user : allUsers)
        {
            System.out.println(user.getName());
        }
        System.out.println("User count: " +allUsers.size());
        System.out.println(userDao.getUser(79).getName());
        userDao.updateUser(new User(1, "bo hans"));
    }
    public static void movieDaoTester() throws IOException
    {
        MovieDAO movieDao = new MovieDAO();
        List<Movie> allMovies = movieDao.getAllMovies();
        for (Movie movie : allMovies)
        {
            System.out.println(movie.getTitle());
        }
        System.out.println("Movie count: " + allMovies.size());

        System.out.println(movieDao.getMovie(87).getTitle());
        Movie movie1 = new Movie(1, 2018, "miav miav");
        Movie movie2 = new Movie(2, 2018, "miav miav");
        System.out.println(movieDao.getNextAvailableMovieID());
        movieDao.updateMovie(movie1);
        movieDao.deleteMovie(movie2);
        movieDao.updateMovie(movieDao.createMovie(2001, "Hello"));
    
        
//        MovieDAO getNextAvailableMovieID = new MovieDAO();
//        List<Movie> nextId = getNextAvailableMovieID.getAllMovies(); 
//        for (Movie Id : nextId)
//        {
//            System.out.println(movie.getTitle));
//        }

        
//        
//        File ratings = new File("data/ratings.txt");   
//        Set<Integer> uniqueIds = new HashSet<>();
//        Stream<String> lines = Files.lines(ratings.toPath());
//        lines.forEach((String t) ->
//        {
//            String[] rating = t.split(",");
//            int id = Integer.parseInt(rating[1]);
//            uniqueIds.add(id);
//        });
//        TreeSet<Integer> sorted = new TreeSet<>(uniqueIds);
//        
//        File users = new File("data/users.txt");
//        try (BufferedWriter bw = Files.newBufferedWriter(users.toPath(), StandardOpenOption.CREATE, StandardOpenOption.WRITE))
//        {
//            File source = new File("C:\\Users\\pgn\\Desktop\\Netflix Data\\load_employees.txt");
//            List<String> emplyees = Files.readAllLines(source.toPath());
//            Iterator<Integer> it = sorted.iterator();
//            for (int i = 0; it.hasNext() && i < emplyees.size(); i++)
//            {
//                String[] cols = emplyees.get(i).split(",");
//                String firstName = cols[2].replace("\'", "");
//                String lastName = cols[3].replace("\'", "");
//                bw.write(Integer.toString(it.next()) + "," + firstName + " " + lastName);
//                bw.newLine();
//            }
//        }
//    }

    }
}