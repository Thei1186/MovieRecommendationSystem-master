/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierecsys.dal;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;
import movierecsys.be.Movie;
import movierecsys.be.Rating;
import movierecsys.be.User;
import movierecsys.dal.Db.DbConnectionProvider;
import movierecsys.dal.Db.MovieDBDAO;

/**
 *
 * @author pgn
 */
public class FileReaderTester
{

    /**
     * Example method. This is the code I used to create the users.txt files.
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException, SQLException
    {
        // mitigateRatings();
        // mitigateUsers();
//         mitigateMovie();
        //movieDaoTester();
        //userDaoTester();
        //ratingDaoTester();
        dbMovieTester();
    }

    public static void ratingDaoTester() throws IOException
    {
        String target = "data/user_ratings";
        RatingDAO ratingDao = new RatingDAO();
//        List<Rating> allRatings = ratingDao.getAllRatings();

        List<Rating> all = ratingDao.getAllRatings();

        try (RandomAccessFile raf = new RandomAccessFile(target, "rw"))
        {
            for (Rating rating : all)
            {
                raf.writeInt(rating.getMovie());
                raf.writeInt(rating.getUser());
                raf.writeInt(rating.getRating());
                System.out.println(rating.getRating());
            }
        } catch (Exception e)
        {

        }

        User me = new User(7, "Georgi Facello");
        List<Rating> ratingsByUser = ratingDao.getRatings(me);
        for (Rating rating : ratingsByUser)
        {
            System.out.println(rating.getUser());
        }
        System.out.println(ratingsByUser.size());

//        It worked,but this was what I used for the method that was too slow
//        System.out.println("start");
//        RatingDAO ratingDao = new RatingDAO();
//        List<Rating> allRatings = ratingDao.getAllRatings();
//        System.out.println(allRatings.size());
//        for (int i = 0; i < 1000; i++) 
//        {
//            System.out.println(allRatings.get(i).getRating() + "stars for " + allRatings.get(i).getMovie().getTitle());
//        }
    }

    public static void mitigateRatings() throws IOException
    {
        DbConnectionProvider ds = new DbConnectionProvider();

        RatingDAO raDao = new RatingDAO();
        List<Rating> ratings = raDao.getAllRatings();

        try (Connection con = ds.getConnection())
        {
            Statement statement = con.createStatement();
            int counter = 0;
            for (Rating rating : ratings)
            {
                String sql = "INSERT INTO [Ratings] (Movieid,Userid,Rating) VALUES("
                        + rating.getMovie() + ","
                        + rating.getUser() + ",'"
                        + rating.getRating() + "');";
                statement.addBatch(sql);
                counter++;
                if (counter % 10000 == 0)
                {
                    statement.executeBatch();
                    System.out.println("Added " + counter + " ratings.");
                }
            }
            if (counter % 10000 != 0)
            {
                statement.executeBatch();
                System.out.println("Added " + counter + " ratings.");
            }
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    public static void mitigateUsers() throws IOException
    {
        DbConnectionProvider ds = new DbConnectionProvider();

        UserDAO usDAO = new UserDAO();
        List<User> users = usDAO.getAllUsers();

        try (Connection con = ds.getConnection())
        {
            Statement statement = con.createStatement();
            int counter = 0;
            for (User user : users)
            {
                String sql = "INSERT INTO [User] (id,name) VALUES("
                        + user.getId() + ",'"
                        + user.getName() + "');";
                statement.addBatch(sql);
                counter++;
                if (counter % 1000 == 0)
                {
                    statement.executeBatch();
                    System.out.println("Added 1000 users.");
                }
            }
            if (counter % 1000 != 0)
            {
                statement.executeBatch();
                System.out.println("Added final batch of users.");
            }
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    public static void mitigateMovie() throws SQLException, IOException
    {
        DbConnectionProvider ds = new DbConnectionProvider();

        MovieDAO mvDao = new MovieDAO();
        List<Movie> movies = mvDao.getAllMovies();

        try (Connection con = ds.getConnection())
        {
            Statement statement = con.createStatement();
            int counter = 0;
            for (Movie movie : movies)
            {
                String sq1 = "INSERT INTO Movie (id, year, title) VALUES("
                        + movie.getId() + ","
                        + movie.getYear() + ",'"
                        + movie.getTitle().replace("'", "") + "');";
                statement.addBatch(sq1);
                counter++;
                if (counter % 1000 == 0)
                {
                    statement.executeBatch();
                    System.out.println("Added  " + counter + " movies");
                }
            }
            if (counter % 1000 != 0)
            {
                statement.executeBatch();
                System.out.println("Added  " + counter + " movies");
            }

        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }

    }

    public static void dbMovieTester() throws IOException
    {
        MovieDBDAO mDbDao = new MovieDBDAO();
        mDbDao.createMovie(2000, "Epoch");
        System.out.println("Did we get an id?" );
    }

    public static void userDaoTester() throws IOException
    {
        UserDAO userDao = new UserDAO();
        List<User> allUsers = userDao.getAllUsers();
        for (User user : allUsers)
        {
            System.out.println(user.getName());
        }
        System.out.println("User count: " + allUsers.size());
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
        List<Movie> searchedMovies = movieDao.searchMovies("hello");
        for (Movie searchedMovy : searchedMovies)
        {
            System.out.println(searchedMovy.getTitle());
        }

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
