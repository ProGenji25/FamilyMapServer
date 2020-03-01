package Result;

/**
 * Return result to load service indicating
 * the associated persons and events data 
 * loaded for user.
 * @author golde3
 *
 */
public class LoadResult
{
	private boolean success;
	private String message;
	private int numEvents;
	private int numPersons;
	private int numUsers;
	
	/**
	 * Constructor for new LoadResult output object
	 */
	public LoadResult()
	{
		 message = new String();
	     numUsers = 0;
	     numPersons = 0;
	     numEvents = 0;
	     success = false;
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

	public int getNumEvents()
	{
		return numEvents;
	}

	public void setNumEvents(int i)
	{
		this.numEvents = i;
	}

	public int getNumPersons()
	{
		return numPersons;
	}

	public void setNumPersons(int i)
	{
		this.numPersons = i;
	}

	public int getNumUsers()
	{
		return numUsers;
	}

	public void setNumUsers(int i)
	{
		this.numUsers = i;
	}
}
