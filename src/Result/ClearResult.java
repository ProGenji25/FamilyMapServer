package Result;

/**
 * Returns if database cleared successfully of all data.
 * @author golde3
 *
 */
public class ClearResult
{
	private String message;
	
	/**
	 * Constructor for new ClearResult output object
	 */
	public ClearResult()
	{	
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
