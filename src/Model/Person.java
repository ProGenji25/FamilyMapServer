package Model;

/**
 * Person class builds person object
 * @author golde3
 *
 */
public class Person
{
	private String personID;	/**Holds unique ID of person*/
	private String descendant;	/**Holds user name to which this person belongs*/
	private String firstName;	/**Holds first name of person*/
	private String lastName;	/**Holds last name of person*/
	private String gender;		/**Holds gender of person*/
	private String father;		/**Holds father of person*/
	private String mother;		/**Holds mother of person*/
	private String spouse;		/**Holds spouse of person*/
	
	/**
	 * Person class constructor
	 */
	public Person()
	{	
	}
	
	/**
	 * Overloaded Person class constructor
	 * @param user	User to generate unique Person object for
	 */
	public Person(User user)
	{
		this.personID = user.getPersonID();
		this.descendant = user.getUsername();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.gender = user.getGender();
		this.father = new String();
		this.mother = new String();
		this.spouse = new String();
	}
	
	public String getPersonID() 
	{
		return personID;
	}
	
	public void setPersonID(String personID)
	{
		this.personID = personID;
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
	
	public String getFather()
	{
		return father;
	}
	
	public void setFather(String father)
	{
		this.father = father;
	}
	
	public String getMother()
	{
		return mother;
	}
	
	public void setMother(String mother)
	{
		this.mother = mother;
	}
	
	public String getSpouse()
	{
		return spouse;
	}
	
	public void setSpouse(String spouse)
	{
		this.spouse = spouse;
	}
	
	public String getDescendant()
	{
		return descendant;
	}
	
	public void setDescendant(String descendant)
	{
		this.descendant = descendant;
	}
	
	public String getName() {
		String name = new String(getFirstName() + " " + getLastName());
		return name;
	}
}