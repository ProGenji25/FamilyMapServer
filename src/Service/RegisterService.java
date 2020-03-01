package Service;

import DataAccess.*;
import Model.*;
import Result.RegisterResult;
import Request.RegisterRequest;
/**
 * Creates a new user account, 
 * generates 4 generations of ancestor data for the new user,
 * logs the user in, and returns an auth token.
 * @author golde3
 *
 */
public class RegisterService
{
	 private Database myDatabase;
	/**
	 * Constructor for new RegisterService object
	 */
	public RegisterService()
	{
		myDatabase = new Database();
	}
	
	/**
	 * load method for the register web API
	 * @param r		new RegisterRequest input object
	 * @return new RegisterResult output message
	 */
	public RegisterResult register(RegisterRequest r)
	{
        RegisterResult result = new RegisterResult();
        try
        {
        	myDatabase.openConnection();

            UserDao myUserDAO = myDatabase.getMyUserDAO();
            PersonDao myPersonDAO = myDatabase.getMyPersonDAO();
            EventDao myEventDAO = myDatabase.getMyEventDAO();
            AuthTokenDao myAuthDAO = myDatabase.getMyAuthTokenDAO();

            User u = new User(r);
            myUserDAO.insertUser(u);

            Person root = new Person(u);
            myPersonDAO.insertPerson(root); //inserts root into database

            int rootBirthYear = myEventDAO.makeRootsEvents(root); //make root's events

            //Now were going to give generations root, which generates fathers and mothers, then generates fathers and mothers events, and each father and mother is passed on to have its generations made
            myPersonDAO.generateGenerations(root, 4, myEventDAO, rootBirthYear);

            //Auth token stuff
            AuthToken returnAuth = new AuthToken(u);
            myAuthDAO.insertToken(returnAuth);
            result = new RegisterResult(returnAuth);
            result.setSuccess(true);
            myDatabase.closeConnection(true);
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
