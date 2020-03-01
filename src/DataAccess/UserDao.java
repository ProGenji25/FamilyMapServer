package DataAccess;

import java.sql.*;
import Model.User;

/**
 * UserDao object provides interaction between User class and 
 * user database
 * @author golde3
 *
 */
public class UserDao
{
	private Connection conn;
	
	/**
	 * Constructor for User DAO object
	 */
	public UserDao()
	{	
	}
	
	/**
	 * Sets up database connection for User DAO
	 * @param c 	SQL database connection
	 * @throws DataAccessException
	 */
	public void setConnection(Connection c) throws DataAccessException
	{
		conn = c;
	}
	
	/**
	 * Builds and clears user table in database
	 * @throws DataAccessException
	 */
	public void clear() throws DataAccessException
	{
        try 
        {
            Statement stmt = null;
            try 
            {
                stmt = conn.createStatement();

                stmt.executeUpdate("drop table if exists users");
                stmt.executeUpdate("create table users "
                		+ "(username VARCHAR(50) UNIQUE PRIMARY KEY, "
                		+ "password VARCHAR(50) NOT NULL, "
                		+ "email VARCHAR(50) NOT NULL, "
                		+ "firstName VARCHAR(50) NOT NULL, " 
                		+ "lastName VARCHAR(50) NOT NULL, "
                        + "gender CHAR(1) NOT NULL, "
                        + "personId VARCHAR(25) NOT NULL)");
            }
            finally
            {
                if (stmt != null) 
                {
                    stmt.close();
                    stmt = null;
                }
            }
        }
        catch (SQLException e) 
        {
            throw new DataAccessException("reset UserTable failed");
        }
	}
	
	/**
	 * Inserts new user into user table
	 * @param user 	User object to be inserted
	 * @throws DataAccessException
	 */
	public void insertUser(User user) throws DataAccessException
	{
        try
        {
            PreparedStatement stmt = null;
            try 
            {

                String sql = "insert into users (username, password, email, firstName, lastName, gender, personId) values (?,?,?,?,?,?,?)";
                stmt = conn.prepareStatement(sql);

                stmt.setString(1,user.getUsername());
                stmt.setString(2,user.getPassword());
                stmt.setString(3,user.getEmail());
                stmt.setString(4,user.getFirstName());
                stmt.setString(5,user.getLastName());

                if(user.getGender().length() != 1 || (!user.getGender().equals("m") && !user.getGender().equals("f"))){
                    throw new DataAccessException("gender is incorrect format user");
                }
                stmt.setString(6,user.getGender());
                stmt.setString(7,user.getPersonID());
                stmt.executeUpdate();
            }
            finally 
            {
                if (stmt != null)
                {
                    stmt.close();
                }
            }
        }
        catch (SQLException e)
        {
            throw new DataAccessException("User already exists failed");
        }
	}
	
	/**
	 * Selects a single user from database
	 * @param username	User name of requested user
	 * @return	User object with requested user data
	 * @throws DataAccessException
	 */
	public User selectUser(String username) throws DataAccessException
	{
		User user = new User();
		try
		{
			PreparedStatement stmt = null;
			ResultSet rs = null;
			try
			{
				String sql = "select * from users WHERE username = '" + username +"'";
				stmt = conn.prepareStatement(sql);
				rs = stmt.executeQuery();
				while(rs.next())
				{
					user.setUsername(rs.getString(1));
					user.setPassword(rs.getString(2));
					user.setEmail(rs.getString(3));
					user.setFirstName(rs.getString(4));
					user.setLastName(rs.getString(5));
					user.setGender(rs.getString(6));
					user.setPersonID(rs.getString(7));
				}
			}
			finally
			{
				if(rs != null)
				{
					rs.close();
				}
				if(stmt != null)
				{
					stmt.close();
				}
			}
		}
		catch(SQLException e)
		{
			throw new DataAccessException("Error encountered while finding user.");
		}
		return user;
	}
	
	/**
	 * Delete user account data of registered user from user table
	 * @param user 	User object of current user
	 * @throws DataAccessException
	 */
	public void deleteUser(User user) throws DataAccessException
	{
		try
		{
			Statement stmt = null;
			try
			{
				stmt = conn.createStatement();
				String sql = "DELETE FROM users WHERE username = '" + user.getUsername() + "'";
				stmt.executeUpdate(sql);
			}
			finally
			{
				if(stmt != null)
				{
					stmt.close();
					stmt = null;
				}
			}
		}
		catch(SQLException e)
		{
			throw new DataAccessException("deleteUser failed.");
		}
	}

	/**
	 * Function to see if requested user name already exists in database
	 * @param username	User name being queried
	 * @return true if user name exists
	 * @throws DataAccessException
	 */
	public boolean doesUsernameExist(String username) throws DataAccessException
	{
		try
		{
			PreparedStatement stmt = null;
			ResultSet rs = null;
			try
			{
				String sql = "select * from users WHERE username = '" + username + "'";
				stmt = conn.prepareStatement(sql);
				rs = stmt.executeQuery();
				if(!rs.next())
				{
					throw new DataAccessException("This username does not exist.");
				}
				else
				{
					return true;
				}
			}
			finally
			{
				if(rs != null)
				{
					rs.close();
				}
				if(stmt != null)
				{
					stmt.close();
				}
			}
		}
		catch(SQLException e)
		{
			throw new DataAccessException("Couldn't retrieve username.");
		}
	}
	
	/**
	 * Function to see if requested user name and password already exists in database
	 * @param username	User being queried about on stored user name and password
	 * @return true if user name and password exists
	 * @throws DataAccessException
	 */
	public boolean doesUsernameAndPasswordExist(User user) throws DataAccessException
	{
		try
		{
			PreparedStatement stmt = null;
			ResultSet rs = null;
			try
			{
				String sql = "select * from users WHERE username = '" + user.getUsername() + "' AND password = '" + user.getPassword() + "'";
				stmt = conn.prepareStatement(sql);
				rs = stmt.executeQuery();
				if(!rs.next())
				{
					throw new DataAccessException("This username and/or password does not exist.");
				}
				else
				{
					return true;
				}
			}
			finally
			{
				if(rs != null)
				{
					rs.close();
				}
				if(stmt != null)
				{
					stmt.close();
				}
			}
		}
		catch(SQLException e)
		{
			throw new DataAccessException("Couldn't retrieve username and/or password.");
		}
	}
	
	/**
	 * Function to retrieve person ID of a user in the database
	 * @param user	User being requested from database
	 * @return	Person ID associated with user
	 * @throws DataAccessException
	 */
	public String getUserID(User user) throws DataAccessException
	{
		String userID = new String();
		try
		{
			PreparedStatement stmt = null;
			ResultSet rs = null;
			try
			{
				String sql = "select * from users WHERE username = '" + user.getUsername() + "' AND password = '" + user.getPassword() + "'";
				stmt = conn.prepareStatement(sql);
				rs = stmt.executeQuery();
				while(rs.next())
				{
					userID = rs.getString(7);
				}
			}
			finally
			{
				if(rs != null)
				{
					rs.close();
				}
				if(stmt != null)
				{
					stmt.close();
				}
			}
		}
		catch(SQLException e)
		{
			throw new DataAccessException("Couldn't retrieve userID.");
		}
		return userID;
	}
	
    public String tableToString() throws DataAccessException 
    {
        StringBuilder out = new StringBuilder();
        try
        {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try 
            {
                String sql = "select * from users";
                stmt = conn.prepareStatement(sql);
                rs = stmt.executeQuery();
                while (rs.next()) 
                {
                    String word = rs.getString(1);
                    String password = rs.getString(2);
                    String email = rs.getString(3);
                    String firstName = rs.getString(4);
                    String lastName = rs.getString(5);
                    String gender = rs.getString(6);
                    String personID = rs.getString(7);
                    out.append((word + "\t" + password + "\t" + email + "\t" + firstName + "\t" + lastName + "\t" + gender + "\t" + personID + "\n"));
                }
            }
            finally 
            {
                if (rs != null)
                {
                    rs.close();
                }
                if (stmt != null) 
                {
                    stmt.close();
                }
            }
        }
        catch (SQLException e)
        {
            throw new DataAccessException("seeTable users failed");
        }
        return out.toString();
    }
}
