package Service;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDao;
import DataAccess.PersonDao;
import DataAccess.UserDao;
import Model.Event;
import Model.Person;
import Model.User;
import Result.LoadResult;
import Request.LoadRequest;
/**
 * Clears all data from the database, 
 * and then loads the posted user, 
 * person, and event data into the database.
 * @author golde3
 *
 */
public class LoadService
{
	private Database myDatabase;
	/**
	 * Constructor for new LoadService object
	 */
	public LoadService()
	{
		myDatabase = new Database();
	}

	/**
	 * load method for the load web API
	 * @param r		new LoadRequest input object
	 * @return new LoadResult output message
	 */
	public LoadResult load(LoadRequest r)
	{
		LoadResult result = new LoadResult();
	    try
	    {
	    	myDatabase.openConnection();
	    	myDatabase.clearTables();

	        UserDao myUserDAO = myDatabase.getMyUserDAO();
	        PersonDao myPersonDAO = myDatabase.getMyPersonDAO();
	        EventDao myEventDAO = myDatabase.getMyEventDAO();

	        User[] users = r.getUsers();
	        Person[] persons = r.getPersons();
	        Event[] events = r.getEvents();

	        for (int i = 0; i < users.length; i++)
	        {
	        	myUserDAO.insertUser(users[i]);
	        }

	        for (int i = 0; i < persons.length; i++)
	        {
	        	myPersonDAO.insertPerson(persons[i]);
	        }

	        for (int i = 0; i < events.length; i++)
	        {
	        	myEventDAO.insertEvent(events[i]);
	        }

	        myDatabase.closeConnection(true);
	        result.setSuccess(true);
	        result.setNumUsers(users.length);
	        result.setNumEvents(events.length);
	        result.setNumPersons(persons.length);
	    } 
	    catch (DataAccessException e)
	    {
	    	result.setSuccess(false);
	        result.setMessage(e.getMessage());
	        try
	        {
	        	myDatabase.closeConnection(false);
	        }
	        catch (DataAccessException d) 
	        {
	        	result.setSuccess(false);
	        	result.setMessage(d.getMessage());
	        }
	    }
	    return result;
	}
}