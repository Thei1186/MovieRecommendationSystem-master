/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierecsys.dal.Db;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import movierecsys.be.User;
import movierecsys.dal.MRSinterfaces.IUserRepository;

/**
 *
 * @author kokus
 */
public class UserDBDAO implements IUserRepository
{

    @Override
    public List<User> getAllUsers() throws FileNotFoundException, IOException
    {
        DbConnectionProvider ds = new DbConnectionProvider();

        List<User> users = new ArrayList<>();

        try (Connection con = ds.getConnection())
        {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM [User] ");
            while (rs.next())
            {
                int id = rs.getInt("id");
                int year = rs.getInt("year");
                String title = rs.getString("title");
                User user = new User(id, "name");
                users.add(user);
                System.out.println("" + id);
            }

        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return users;
    }

    @Override
    public User getUser(int id) throws FileNotFoundException, IOException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateUser(User user) throws IOException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
