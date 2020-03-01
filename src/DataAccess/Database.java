package DataAccess;

import java.sql.*;
import Model.User;
/**
 * Database class builds database in Java by importing SQL tables
 * @author golde3
 *
 */
public class Database
{
	private AuthTokenDao myAuthTokenDAO;
	private EventDao myEventDAO;
	private PersonDao myPersonDAO;
	private UserDao myUserDAO;
	private Connection conn;
	
	static 
	{
		try 
		{
			final String driver = "org.sqlite.JDBC";
			Class.forName(driver);
		} 
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	public Database()
	{
		/**
		 * Constructs new DAO objects
		 */
		myAuthTokenDAO = new AuthTokenDao();
		myEventDAO = new EventDao();
		myPersonDAO = new PersonDao();
		myUserDAO = new UserDao();
	}
	
	public void openConnection() throws DataAccessException 
	{
		/**
		 * Opens an SQL database connection
		 * @throws DatabaseException
		 */
		try 
		{
			//Open database connection
			final String CONNECTION_URL = "jdbc:sqlite:data/database.sqlite";
			conn = DriverManager.getConnection(CONNECTION_URL);
			
			//Set DAO connections
			myAuthTokenDAO.setConnection(conn);
			myEventDAO.setConnection(conn);
			myPersonDAO.setConnection(conn);
			myUserDAO.setConnection(conn);
			
			//Start transaction
			conn.setAutoCommit(false);
		} 
		catch(SQLException e) 
		{
			throw new DataAccessException("Unable to open connection to database.");
		}
	}
		
	public void clearTables() throws DataAccessException 
	{
		/**
		 * Wraper function to handle DAO reset functions
		 * @throws DatabaseException
		 */
		myAuthTokenDAO.clear();
		myEventDAO.clear();
		myPersonDAO.clear();
		myUserDAO.clear();
	}
	
	public void closeConnection(boolean commit) throws DataAccessException
	{
		/**
		 * Closes current database connection and commits recent changes
		 * @param commit checks if database update successful
		 * @throws DatabaseException
		 */
		try 
		{
			if(commit) 
			{
				conn.commit();
			}
			else
			{
				conn.rollback();
			}
			conn.close();
			conn = null;
		} 
		catch(SQLException e) 
		{
			throw new DataAccessException("Unable to close connection to database.");
		}
	}
	
	/**
	 * Function used to remove User account data
	 * @param user	User of account to be removed
	 * @throws DataAccessException
	 */
	public void deleteUserData(User user) throws DataAccessException 
	{
		myEventDAO.deleteAllEventsOfUser(user);
		myPersonDAO.deleteAllPeopleOfUser(user);
		myUserDAO.deleteUser(user);
	}
	
	public AuthTokenDao getMyAuthTokenDAO() 
	{
		return myAuthTokenDAO;
	}

	public EventDao getMyEventDAO() 
	{
		return myEventDAO;
	}

	public PersonDao getMyPersonDAO() 
	{
		return myPersonDAO;
	}

	public UserDao getMyUserDAO()
	{
		return myUserDAO;
	}
}
