package Model;

import java.util.UUID;
/**
 * AuthToken class assigns user an authorization token to access server
 * @author golde3
 *
 */
public class AuthToken
{
	private String authToken;	/**Holds unique auth token for user*/
	private String personID;	/**Holds unique ID of user*/
	private String username;	/**Holds username of user*/
	
	/**
	 * AuthToken class constructor
	 */
	public AuthToken()
	{
		authToken = new String();
		personID = new String();
		username = new String();
	}
	
	/**
	 * Overloaded AuthToken class constructor
	 * @param user	Unique user to generate token for
	 */
	public AuthToken(User user)
	{
		authToken = UUID.randomUUID().toString();
		personID = user.getPersonID();
		username = user.getUsername();
	}
	
	public String getAuthToken() 
	{
		return authToken;
	}
	
	public void setAuthToken(String authToken) 
	{
		this.authToken = authToken;
	}
	
	public String getPersonID() 
	{
		return personID;
	}
	
	public void setPersonID(String personID)
	{
		this.personID = personID;
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public void setUsername(String username)
	{
		this.username = username;
	}
}