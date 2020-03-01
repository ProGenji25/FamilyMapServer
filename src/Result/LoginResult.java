package Result;

import Model.AuthToken;
/**
 * Return result to login service indicating
 * login status of user.
 * @author golde3
 *
 */
public class LoginResult
{
	private String authToken;
	private String username;
	private String personID;
	private transient boolean success;
	private transient String message;
	
	/**
	 * Constructor for new LoginResult output object
	 */
	public LoginResult() 
	{
		authToken = new String();
        username = new String();
        personID = new String();
        success = false;
        message = new String();
	}
	
	/**
	 * Overloaded constructor for new LoginResult output object
	 * @param auth The auth token authorizing login of the user
	 */
	public LoginResult(AuthToken auth)
	{
		this.authToken = auth.getAuthToken();
        this.username = auth.getUsername();
        this.personID = auth.getPersonID();
        success = true;
        message = new String();
	}

	public String getAuthToken()
	{
		return authToken;
	}

	public void setAuthToken(String in)
	{
		this.authToken = in;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String in)
	{
		this.username = in;
	}

	public String getPersonID()
	{
		return personID;
	}

	public void setPersonID(String in) 
	{
		this.personID = in;
	}

	public boolean getSuccess()
	{
		return success;
	}

	public void setSuccess(boolean b)
	{
		this.success = b;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String m)
	{
		this.message = m;
	}
}
