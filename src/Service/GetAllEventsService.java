package Service;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDao;
import Model.AuthToken;
import Result.GetAllEventsResult;

/**
 * Returns ALL events for ALL family members 
 * of the current user. The current user is 
 * determined from the provided auth token
 * @author golde3
 *
 */
public class GetAllEventsService
{
	 private Database myDatabase;
	 
	/**
	 * Constructor for new GetAllEventsService object
	 */
	public GetAllEventsService() 
	{
		 myDatabase = new Database();
	}
	
	/**
	 * method for servicing event web API
	 * @param authToken		Token of current user
	 * @return GetAllEventsResult 	Array of event objects
	 */
	public GetAllEventsResult events(String authToken) 
	{
		GetAllEventsResult result = new GetAllEventsResult();
        try
        {
        	myDatabase.openConnection();
            EventDao myEventDAO = myDatabase.getMyEventDAO();
            AuthTokenDao myAuthDAO = myDatabase.getMyAuthTokenDAO();
            
            if(myAuthDAO.doesAuthTokenExist(authToken))
            {
                AuthToken auth = myAuthDAO.selectAuthToken(authToken);
                result.setEvents(myEventDAO.selectAllEvents(auth.getUsername()));
                myDatabase.closeConnection(true);
                result.setSuccess(true);
            } 
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