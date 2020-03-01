package Result;

import Model.Event;
/**
 * Returns a JSON object
 * that contains an Event object associated with
 * the passed in identification
 * @author golde3
 *
 */
public class GetEventIdResult
{
	private String eventID;		/**Holds unique ID of event*/
	private String personID;	/**Holds unique ID of person*/
	private String descendant;	/**Holds user name to which this person belongs*/
	private Double latitude;	/**Holds latitude of event location*/
	private Double longitude;	/**Holds longitude of event location*/
	private String country;		/**Holds country in which event occurred*/
	private String city;		/**Holds city in which event occurred*/		
	private String eventType;	/**Holds event type (birth, baptism, christening, marriage, death, etc.*/
	private int year;			/**Holds year where event occurred*/
	private transient boolean success;
	private transient String message;
	
	/**
	 * Constructor for new GetEventIdResult output object
	 */
	public GetEventIdResult()
	{
	}
	
	/**
	 * Overloaded constructor for new GetEventIdResult output object
	 * @param event The event ID associated with requested event
	 */
	public GetEventIdResult(Event event) 
	{
		this.descendant = event.getDescendant();
        this.eventID = event.getEventID();
        this.personID = event.getPersonID();
        this.latitude = event.getLatitude();
        this.longitude = event.getLongitude();
        this.country = event.getCountry();
        this.city = event.getCity();
        this.eventType = event.getEventType();
        this.year = event.getYear();
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
