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
import movierecsys.be.Movie;
import movierecsys.be.User;

/**
 *
 * @author pgn
 */
public class UserDAO
{
    private static final String USER_SOURCE = "data/users.txt";
    /**
     * Gets a list of all known users.
     * @return List of users.
     */
    public List<User> getAllUsers() throws FileNotFoundException, IOException
    {
        List<User> allUsers = new ArrayList<>();

        File file = new File(USER_SOURCE);


        try (BufferedReader reader = new BufferedReader(new FileReader(file)))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                if (!line.isEmpty())
                {
                    try
                    {
                        User user = stringArrayToUser(line);
                        allUsers.add(user);
                    } catch (Exception ex)
                    {
                        //Do nothing we simply do not accept malformed lines of data.
                        //In a perfect world you should at least log the incident.
                    }
                }
            }
        }
        return allUsers;
    }
    private User stringArrayToUser(String t)
    {
        String[] arrUser = t.split(",");

        int id = Integer.parseInt(arrUser[0]);
        String name = arrUser[1];

        User user = new User(id, name);
        return user;
    }
    /**
     * Gets a single User by its ID.
     * @param id The ID of the user.
     * @return The User with the ID.
     */
    public User getUser(int id) throws FileNotFoundException, IOException
    {
        File file = new File(USER_SOURCE);

        try (BufferedReader reader = new BufferedReader(new FileReader(file)))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                if (!line.isEmpty())
                {
                    try
                    {
                        User user = stringArrayToUser(line);
                        if (user.getId()==id)
                        {
                            return user;
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
    
    /**
     * Updates a user so the persistence storage reflects the given User object.
     * @param user The updated user.
     */
    public void updateUser(User user)
    {
        //TODO Update user.
    }
    
}
