package Result;

import java.util.ArrayList;

import Model.Event;
/**
 * Returns a JSON object
 * that contains an array of Event objects
 * @author golde3
 *
 */
public class GetAllEventsResult
{
	private ArrayList<Event> data;
	private transient boolean success;
	private transient String message;
	
	/**
	 * Constructor for new GetAllEventsResult output object
	 */
	public GetAllEventsResult()
	{
	}

	public ArrayList<Event> getEvents()
	{
		return data;
	}

	public void setEvents(ArrayList<Event> arrayList)
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
