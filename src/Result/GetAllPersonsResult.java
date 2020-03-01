package Result;

import java.util.ArrayList;

import Model.Person;
/**
 * Returns a JSON object
 * that contains an array of Person objects
 * @author golde3
 *
 */
public class GetAllPersonsResult
{
	private ArrayList<Person> data;
	private transient boolean success;
	private transient String message;
	
	/**
	 * Constructor for new GetAllPersonsResult output object
	 */
	public GetAllPersonsResult()
	{	
	}
	
	public GetAllPersonsResult (ArrayList<Person> in) 
	{
		data = in;
		success = true;
		message = new String();
	}
	
	public ArrayList<Person> getPersons() 
	{
		return data;
	}
	
	public void setPersons(ArrayList<Person> arrayList)
	{
		this.data = arrayList;
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
