package Model;

/**
 * Event class creates new event object
 * @author golde3
 *
 */
public class Event
{
	private String eventID;		/**Holds unique ID of event*/
	private String descendant;	/**Holds user name to which this person belongs*/
	private String personID;	/**Holds unique ID of person*/
	private Double latitude;	/**Holds latitude of event location*/
	private Double longitude;	/**Holds longitude of event location*/
	private String country;		/**Holds country in which event occurred*/
	private String city;		/**Holds city in which event occurred*/		
	private String eventType;	/**Holds event type (birth, baptism, christening, marriage, death, etc.*/
	private int year;			/**Holds year where event occurred*/
	
	/**
	 * Event class constructor
	 */
	public Event() 
	{
	}
	
	public String getEventID()
	{
		return eventID;
	}
	
	public void setEventID(String eventID)
	{
		this.eventID = eventID;
	}
	
	public String getPersonID()
	{
		return personID;
	}
	
	public void setPersonID(String personID)
	{
		this.personID = personID;
	}
	
	public String getDescendant()
	{
		return descendant;
	}
	
	public void setDescendant(String descendant)
	{
		this.descendant = descendant;
	}
	
	public Double getLatitude() 
	{
		return latitude;
	}
	
	public void setLatitude(Double latitude)
	{
		this.latitude = latitude;
	}
	
	public Double getLongitude()
	{
		return longitude;
	}
	
	public void setLongitude(Double longitude)
	{
		this.longitude = longitude;
	}
	
	public String getCountry()
	{
		return country;
	}
	
	public void setCountry(String country)
	{
		this.country = country;
	}
	
	public String getCity()
	{
		return city;
	}
	
	public void setCity(String city) 
	{
		this.city = city;
	}
	
	public String getEventType()
	{
		return eventType;
	}
	
	public void setEventType(String eventType)
	{
		this.eventType = eventType;
	}
	
	public int getYear() 
	{
		return year;
	}
	
	public void setYear(int year)
	{
		this.year = year;
	}
	
	public String getEvent()
	{
		String event = new String(getEventType() + ": " + getCity() + ", " + getCountry() + "(" + getYear() + ")");
		return event;
	}
}