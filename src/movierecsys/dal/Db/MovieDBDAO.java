/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierecsys.dal.Db;

import movierecsys.dal.MRSinterfaces.IMovieRepository;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import movierecsys.be.Movie;

/**
 *
 * @author kokus
 */
public class MovieDBDAO implements IMovieRepository
{
    DbConnectionProvider ds;
    public MovieDBDAO() throws IOException
    {
        ds = new DbConnectionProvider(); 
    }

    @Override
    public Movie createMovie(int releaseYear, String title) throws IOException
    {
        String sq1 = "INSERT INTO [Movie] (year, title)VALUES(?,?);";
        try (Connection con = ds.getConnection())
        {
            PreparedStatement st = con.prepareStatement(sq1, Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, releaseYear);
            st.setString(2, title);
            int rowsAffected = st.executeUpdate();
      
            ResultSet rs = st.getGeneratedKeys();
            int id = 0;
            if (rs.next())
            {
               id = rs.getInt(1);
            }
            Movie movie = new Movie(id, releaseYear, title);
            return movie;
        
        } catch (Exception e)
        {
            e.printStackTrace();
        }
            return null;
    }

   
    
    @Override
    public void deleteMovie(Movie movie) throws IOException
    {
    String sq1 = "DELETE FROM Movie WHERE id = ?";
        try(Connection con = ds.getConnection()) 
        {
            PreparedStatement pstmt = con.prepareStatement(sq1);
            
            pstmt.setInt(1, movie.getId());
            pstmt.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
      
    }

    @Override
    public List<Movie> getAllMovies() throws IOException
    {
     
        List<Movie> movies = new ArrayList<>();

        try (Connection con = ds.getConnection())
        {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Movie ");
            while (rs.next())
            {
                int id = rs.getInt("id");
                int year = rs.getInt("year");
                String title = rs.getString("title");
                Movie movie = new Movie(id, year, title);
                movies.add(movie);
                System.out.println("" + id);
            }

        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return movies;
    }

    @Override
    public Movie getMovie(int id) throws FileNotFoundException, IOException
    {
    
    return null;
    }

    @Override
    public List<Movie> searchMovies(String query) throws IOException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateMovie(Movie movie) throws IOException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
//
//     public int getNextAvailableId() throws IOException
//    {
//     
//     int lastId = 0;
//        try (Connection con = ds.getConnection())
//        {
//          
//            Statement statement = con.createStatement();
//            ResultSet rs = statement.executeQuery("SELECT * FROM Movie");
//            while (rs.next())
//            {
//                
//                lastId = rs.getInt("id");
//            }
//        } catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//       return lastId +1;
//        
//    }
}
