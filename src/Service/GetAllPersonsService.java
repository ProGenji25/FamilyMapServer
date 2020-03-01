package Service;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.PersonDao;
import Model.AuthToken;
import Model.Person;
import Result.GetAllPersonsResult;
import java.util.*;
/**
 * Returns ALL family members of the current user. 
 * The current user is determined from the provided 
 * auth token.
 * @author golde3
 *
 */
public class GetAllPersonsService 
{
	private Database myDatabase;
	/**
	 * Constructor for new GetAllPersonsService object
	 */
	public GetAllPersonsService()
	{
		myDatabase = new Database();
	}
	
	/**
	 * method for servicing person web API
	 * @param authToken					Token of current user
	 * @return GetAllPersonsResult 	Array of person objects
	 */
	public GetAllPersonsResult getPeople(String authToken) 
	{
        GetAllPersonsResult myGetAllPersonsResult = new GetAllPersonsResult();
        try
        {
        	myDatabase.openConnection();
            PersonDao myPersonDAO = myDatabase.getMyPersonDAO();
            AuthTokenDao myAuthDAO = myDatabase.getMyAuthTokenDAO();

            if(myAuthDAO.doesAuthTokenExist(authToken))
            {
                AuthToken auth = myAuthDAO.selectAuthToken(authToken);
                ArrayList<Person> persons = myPersonDAO.selectAllPersons(auth.getUsername());
                myGetAllPersonsResult = new GetAllPersonsResult(persons);
            } 

            myDatabase.closeConnection(true);
            myGetAllPersonsResult.setSuccess(true);

        } 
        catch (DataAccessException e)
        {
            myGetAllPersonsResult.setSuccess(false);
            myGetAllPersonsResult.setMessage(e.getMessage());
            try
            {
            	myDatabase.closeConnection(false);
            }
            catch (DataAccessException d)
            {
                myGetAllPersonsResult.setSuccess(false);
                myGetAllPersonsResult.setMessage(d.getMessage());
            }
        }
        return myGetAllPersonsResult;
	}
}
