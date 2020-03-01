package Request;

/**
 * Inputs registration request to the register service class.
 * @author golde3
 *
 */
public class RegisterRequest
{
	private String userName;	/**Field for holding user name input*/
	private String password;	/**Field for holding password input*/
	private String email;		/**Field for holding new user email*/
	private String firstName;	/**Field for holding new user's first name*/
	private String lastName;	/**Field for holding new user's last name*/
	private String gender;		/**Field for holding new user's gender*/
	private String serverHost;	/**Field for holding current server host ID*/
	private String serverPort;	/**Field for holding current server port ID*/

	/**
	 * Constructor for new RegisterRequest input object
	 */
	public RegisterRequest()
	{
	}

	public String getUsername()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
        this.userName = userName;
    }

	public String getPassword()
	{
		return password;
	}

    public void setPassword(String password)
    {
        this.password = password;
    }

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email) 
	{
		this.email = email;
	}

	public String getFirstName()
	{
		return firstName;
	}

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

	public String getLastName()
	{
		return lastName;
	}

    public void setLastName(String lastName) 
    {
        this.lastName = lastName;
    }

	public String getGender()
	{
		return gender;
	}

    public void setGender(String gender) 
    {
        this.gender = gender;
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