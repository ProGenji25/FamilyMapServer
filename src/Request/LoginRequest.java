package Request;

/**
 * Inputs login request to the login service class.
 * @author golde3
 *
 */
public class LoginRequest
{
	private String userName;	/**Field for holding user name input*/
	private String password;	/**Field for holding password input*/
	private String serverHost;  /**Field for holding current server host ID*/
	private String serverPort;	/**Field for holding current server port ID*/

	/**
	 * Constructor for new LoadRequest input object
	 */
	public LoginRequest()
	{
	}

	public String getUsername() 
	{
		return userName;
	}

	public void setUsername(String in)
	{
		this.userName = in;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String in)
	{
		this.password = in;
	}

	public String getServerHost()
	{
		return serverHost;
	}

	public void setServerHost(String in)
	{
		this.serverHost = in;
	}

	public String getServerPort()
	{
		return serverPort;
	}

	public void setServerPort(String in)
	{
		this.serverPort = in;
	}
}