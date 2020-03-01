package Result;

/**
 * Return result to fill service indicating
 * number of persons and events added for user.
 * @author golde3
 *
 */
public class FillResult
{
	private transient boolean success;
	private transient String message;
	private transient int numPersons = 0;
	private transient int numEvents = 0;
	
	/**
	 * Constructor for new FillResult output object
	 */
	public FillResult()
	{
		success = false;
		message = new String();
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

	public int getNumPersons()
	{
		return numPersons;
	}

	public void setNumPersons(int i)
	{
		this.numPersons = i;
	}

	public int getNumEvents()
	{
		return numEvents;
	}

	public void setNumEvents(int i)
	{
		this.numEvents = i;
	}
}
