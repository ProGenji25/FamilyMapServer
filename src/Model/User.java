package Model;

import java.util.UUID;
import Request.LoginRequest;
import Request.RegisterRequest;

/** User class builds a user account for the user
 * 
 * @author golde3
 *
 */
public class User 
{
	private String userName;	/**Holds username of user*/
	private String password;	/**Holds password of user*/
	private String email;		/**Holds e-mail address of user*/
	private String firstName;	/**Holds first name of user*/
	private String lastName;	/**Holds last name of user*/
	private String gender;		/**Holds gender of user*/
	private String personID;	/**Holds unique ID of user*/
	
	/**
	 * User class constructor
	 * 
	 */
	public User() 
	{
	}
	
	/**
	 * Overloaded User class constructor for Register Requests
	 * @param r		RegisterRequest object for creating new User account
	 */
	public User(RegisterRequest r) 
	{
        this.userName = r.getUsername();
        this.password = r.getPassword();
        this.email = r.getEmail();
        this.firstName = r.getFirstName();
        this.lastName = r.getLastName();
        this.gender = r.getGender();
        String personID = UUID.randomUUID().toString();
        this.personID = personID;
	}
	
	/**
	 * Overloaded User class constructor for Login Requests
	 * @param l		LoginRequest object for logging user into server
	 */
	public User(LoginRequest l) 
	{
		this.userName = l.getUsername();
		this.password = l.getPassword();
		email = new String();
		firstName = new String();
		lastName = new String();
		gender = new String();
		personID = new String();
	}
	
	public String getUsername() 
	{
		return userName;
	}
	
	public void setUsername(String username) 
	{
		this.userName = username;
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
	
	public String getPersonID()
	{
		return personID;
	}
	
	public void setPersonID(String personID) 
	{
		this.personID = personID;
	}
}
