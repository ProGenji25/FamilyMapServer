package Service;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDao;
import Model.AuthToken;
import Model.Person;
import Result.GetPersonIdResult;
/**
 * Returns the single Person object with the specified ID.
 * @author golde3
 *
 */
public class GetPersonIdService 
{
	private Database myDatabase;
	/**
	 * Constructor for new GetPersonIdService object
	 */
	public GetPersonIdService()
	{
		myDatabase = new Database();
	}
	
	/**
	 * method for retrieving data in personID web API
	 * @param authToken Unique auth token associated with current user
	 * @return Person object with the specified ID.
	 */
	public GetPersonIdResult personID(String personId, String authToken) 
	{
        GetPersonIdResult myGetPersonIdResult = new GetPersonIdResult();
        try
        {
        	myDatabase.openConnection();
            PersonDao myPersonDAO = myDatabase.getMyPersonDAO();
            AuthTokenDao myAuthDAO = myDatabase.getMyAuthTokenDAO();

            if(myAuthDAO.doesAuthTokenExist(authToken))
            {
                AuthToken auth = myAuthDAO.selectAuthToken(authToken);
                if (myPersonDAO.doesPersonExist(personId))
                {
                    Person person = myPersonDAO.selectPerson(personId);
                    if (!person.getDescendant().equals(auth.getUsername()))
                    {
                        throw new DataAccessException("PersonID does not match given authToken");
                    }
                    myGetPersonIdResult = new GetPersonIdResult(person);
                }
            }
            myDatabase.closeConnection(true);
            myGetPersonIdResult.setSuccess(true);
        } 
        catch (DataAccessException e)
        {
            myGetPersonIdResult.setSuccess(false);
            myGetPersonIdResult.setMessage(e.getMessage());
            try
            {
            	myDatabase.closeConnection(false);
            }
            catch (DataAccessException d)
            {
                myGetPersonIdResult.setSuccess(false);
                myGetPersonIdResult.setMessage(d.getMessage());
            }
        }
        return myGetPersonIdResult;
	}
}
