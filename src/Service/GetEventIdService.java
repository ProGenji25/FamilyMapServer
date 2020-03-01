package Service;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.EventDao;
import Model.AuthToken;
import Model.Event;
import Result.GetEventIdResult;
/**
 * Returns the single Event object with the specified ID
 * @author golde3
 *
 */
public class GetEventIdService 
{
	private Database myDatabase;
	/**
	 * Constructor for new GetEventIdService object
	 */
	public GetEventIdService()
	{
		myDatabase = new Database();
	}
	
	/**
	 * method for retrieving data in eventID web API
	 * @param authToken	Unique auth token associated with current user
	 * @return Event object with the specified ID
	 */
	public GetEventIdResult eventID(String eventId, String authToken) 
	{
		GetEventIdResult result = new GetEventIdResult();
        try
        {
        	myDatabase.openConnection();
            EventDao myEventDAO = myDatabase.getMyEventDAO();
            AuthTokenDao myAuthDAO = myDatabase.getMyAuthTokenDAO();
            
            if(myAuthDAO.doesAuthTokenExist(authToken))
            {
                AuthToken auth = myAuthDAO.selectAuthToken(authToken);
                if (myEventDAO.doesEventExist(eventId))
                {
                    Event event = myEventDAO.selectEvent(eventId);
                    if (!event.getDescendant().equals(auth.getUsername()))
                    {
                        throw new DataAccessException("Descendant of event and username of auth token do not match");
                    }
                    result = new GetEventIdResult(event);
                }
            }
            myDatabase.closeConnection(true);
            result.setSuccess(true);
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