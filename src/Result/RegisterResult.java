package Result;

import Model.AuthToken;
/**
 * Return result to register service indicating
 * registration status of user.
 * @author golde3
 *
 */
public class RegisterResult
{
    private String authToken;
    private String userName;
    private String personId;
    private transient boolean success;
    private transient String message;
	
	/**
	 * Constructor for new RegisterResult output object
	 */
	public RegisterResult()
	{
        success = false;
        authToken = new String();
        userName = new String();
        personId = new String();
	}
	
	/**
	 * Overloaded constructor for new RegisterResult output object
	 * @param auth	Authentication token authorizing user's registration
	 */
	public RegisterResult(AuthToken auth)
	{
		   authToken = auth.getAuthToken();
	        userName = auth.getUsername();
	        personId = auth.getPersonID();
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
		return userName;
	}

	public void setUsername(String in)
	{
		this.userName = in;
	}

	public String getPersonID()
	{
		return personId;
	}

	public void setPersonID(String in)
	{
		this.personId = in;
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
