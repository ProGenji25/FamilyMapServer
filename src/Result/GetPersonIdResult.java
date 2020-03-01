package Result;

import Model.Person;
/**
 * Returns a JSON object
 * that contains an Person object associated with
 * the passed in identification
 * @author golde3
 *
 */
public class GetPersonIdResult
{
	private String descendant;	/**Holds user name to which this person belongs*/
	private String personID;	/**Holds unique ID of person*/
	private String firstName;	/**Holds first name of person*/
	private String lastName;	/**Holds last name of person*/
	private String gender;		/**Holds gender of person*/
	private String father;		/**Holds father of person*/
	private String mother;		/**Holds mother of person*/
	private String spouse;		/**Holds spouse of person*/
	private transient boolean success;
	private transient String message;
	/**
	 * Constructor for new GetPersonIdResult output object
	 */
	public GetPersonIdResult() 
	{
	}
	
	/**
	 * Overloaded Constructor for new GetPersonIdResult output object
	 * @param person person ID associated with that person object
	 */
	public GetPersonIdResult(Person person) 
	{
		 this.descendant = person.getDescendant();
	     this.personID = person.getPersonID();
	     this.firstName = person.getFirstName();
	     this.lastName = person.getLastName();
	     this.gender = person.getGender();
	     this.father = person.getFather();
	     this.mother = person.getMother();
	     this.spouse = person.getSpouse();
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

	public String getFirstName()
	{
		return firstName;
	}

	public String getLastName() 
	{
		return lastName;
	}
}
