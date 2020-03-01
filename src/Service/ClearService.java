package Service;

import DataAccess.Database;
import DataAccess.DataAccessException;
import Result.ClearResult;

/**
 * Deletes ALL data from the database, including user accounts, 
 * auth tokens, and generated person and event data.
 * @author golde3
 *
 */
public class ClearService 
{
	private Database myDatabase;
	/**
	 * Constructor for new ClearService object
	 */
	public ClearService() 
	{
		myDatabase = new Database();
	}
	
	/**
	 * clear method for clear web API
	 * @return ClearResult 	message object indicating 
	 * 		   				if database was successfully cleared
	 */
	public ClearResult clear() 
	{
		ClearResult result = new ClearResult();
        try 
        {
        	myDatabase.openConnection();
        	myDatabase.clearTables();
        	myDatabase.closeConnection(true);
        } 
        catch (DataAccess.DataAccessException e)
        {
            System.out.println(e.getMessage());
            result.setMessage("Internal server error");
            try 
            {
            	myDatabase.closeConnection(false);
            }
            catch (DataAccessException d)
            {
            	result.setMessage(d.getMessage());
                return result;
            }
            return result;
        }
        result.setMessage("Clear succeeded");
        return result;
	}
}