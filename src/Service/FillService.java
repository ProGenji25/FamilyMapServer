package Service;

import java.util.UUID;

import DataAccess.Database;
import DataAccess.DataAccessException;
import DataAccess.EventDao;
import DataAccess.PersonDao;
import DataAccess.UserDao;
import Model.Person;
import Model.User;
import Result.FillResult;
/**
 * Populates the server's database with generated data 
 * for the specified user name.The required "username" 
 * parameter must be a user already registered with the 
 * server. If there is any data in the database already 
 * associated with the given user name, it is deleted. 
 * The optional generations parameter
 * specify the number of generations of ancestors to be 
 * generated, and must be a non-negative integer.
 * @author golde3
 *
 */
public class FillService 
{
	private Database myDatabase;
	
	/**
	 * Constructor for new FillService object
	 */
	public FillService()
	{
		myDatabase = new Database();
	}
	
	/**
	 * fill method for fill web API
	 * @param username 		User's user name
	 * @param generations 	Number of generations of family data to fill
	 * @return FillResult	Object stating success of fill method
	 */
	public FillResult fill(String username, int generations)
	{
		FillResult result = new FillResult();
        try
        {
        	myDatabase.openConnection();
            UserDao myUserDAO = myDatabase.getMyUserDAO();
            EventDao myEventDAO = myDatabase.getMyEventDAO();
            PersonDao myPersonDAO = myDatabase.getMyPersonDAO();
            
            if (!myUserDAO.doesUsernameExist(username))
            {
                throw new DataAccessException("username does not exist");
            }
            User user = myUserDAO.selectUser(username); //user does not have the same personID as the userName user
            user.setPersonID(UUID.randomUUID().toString());
            myDatabase.deleteUserData(user); //deletes all things of user, including the user
            myUserDAO.insertUser(user); //inserts same user but with new personID into the database
            Person root = new Person(user);  //creates a person representation of the user
            myPersonDAO.insertPerson(root); //inserts root into database
            int rootBirthYear = myEventDAO.makeRootsEvents(root); //make root's events

            //Now were going to give generateGenerations root, which generates fathers and mothers, then generates fathers and mothers events, and each father and mother is passed on to have its generations made
            if (generations == -1) //default case
            {
                myPersonDAO.generateGenerations(root, 4, myEventDAO, rootBirthYear); //default is four generations
                result.setNumPersons(31);
                result.setNumEvents(124);
                result.setSuccess(true);
            } 
            else
            {
                myPersonDAO.generateGenerations(root, generations, myEventDAO, rootBirthYear);
                double numG = (double) generations;
                double answer = (Math.pow(2.0, (numG + 1.0)) - 1.0);
                int finalAnswer = (int) answer;
                result.setNumPersons(finalAnswer);
                result.setNumEvents(finalAnswer * 4);
                result.setSuccess(true);
            }
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