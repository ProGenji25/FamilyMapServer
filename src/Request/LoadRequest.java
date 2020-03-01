package Request;

import Model.User;
import Model.Person;
import Model.Event;

/**
 * Inputs load request to the load service class.
 * @author golde3
 *
 */
public class LoadRequest
{
	private User[] users;
	private Person[] persons;
	private Event[] events;
	
	/**
	 * Constructor for new LoadRequest input object
	 */
	public LoadRequest()
	{
	}

	public User[] getUsers() 
	{
		return users;
	}

	public void setUsers(User[] input)
	{
		this.users = new User[input.length];
		for(int i = 0; i < input.length; i++) 
		{
			users[i] = input[i];
		}
	}

	public Person[] getPersons()
	{
		return persons;
	}

	public void setPersons(Person[] input)
	{
		this.persons = new Person[input.length];
		for(int i = 0; i < input.length; i++)
		{
			persons[i] = input[i];
		}
	}

	public Event[] getEvents()
	{
		return events;
	}

	public void setEvents(Event[] input)
	{
		this.events = new Event[input.length];
		for(int i = 0; i < input.length; i++)
		{
			events[i] = input[i];
		}
	}
}