package Service;

import DataAccess.AuthTokenDao;
import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDao;
import Model.AuthToken;
import Model.User;
import Result.LoginResult;
import Request.LoginRequest;

/**
 * Logs in the user and returns an auth token.
 * @author golde3
 *
 */
public class LoginService
{

	 private Database myDatabase;
	 
	/**
	 * Constructor for new LoginService object
	 */
	public LoginService()
	{
		myDatabase = new Database();
	}
	
	/**
	 * login method for the login web API
	 * @param r		new LoginRequest input object
	 * @return new LoginResult output message
	 */
	public LoginResult login(LoginRequest r)
	{
		LoginResult result = new LoginResult();
	    UserDao myUserDAO = myDatabase.getMyUserDAO();
	    AuthTokenDao myAuthDAO = myDatabase.getMyAuthTokenDAO();
	    try 
	    {
	    	myDatabase.openConnection();
	        User user = new User(r);
	        
	        if (myUserDAO.doesUsernameAndPasswordExist(user))  //the username and password are valid
	        {
	        	AuthToken returnAuth = new AuthToken(user);
	            returnAuth.setPersonID(myUserDAO.getUserID(user)); // will fill in the personID of the user
	            myAuthDAO.insertToken(returnAuth);
	            result = new LoginResult(returnAuth);
	            result.setSuccess(true);
	            myDatabase.closeConnection(true);
	        }
	    }
	    catch (DataAccessException d)
	    {
	        result.setMessage(d.getMessage());
	        result.setSuccess(false);
	        try
	        {
	        	myDatabase.closeConnection(false);
	        } 
	        catch (DataAccessException e)
	        {
	        	result.setSuccess(false);
	            result.setMessage(e.getMessage());
	        }
	   }
	   return result;
	}
}