package DataAccess;

import java.sql.*;
import java.util.*;

import Model.Event;
import Model.Person;
import Model.User;
import ObjectEncoder.*;
/**
 * EventDao object provides interaction between Event class and 
 * event database
 * @author golde3
 *
 */
public class EventDao
{
	private Connection conn;
	private LocationArray locationArray;

	/**
	 * Constructor for Event DAO object
	 */
	public EventDao() 
	{
		locationArray = Decoder.decodeLocations("data/locations.json");
	}
	
	/**
	 * Sets up database connection for Event DAO
	 * @param c SQL database connection
	 * @throws DataAccessException
	 */
	public void setConnection(Connection c) throws DataAccessException
	{
		conn = c;
	}
	
	/**
	 * Builds and clears event table in database
	 * @throws DataAccessException
	 */
	public void clear() throws DataAccessException
	{
		try 
		{
			Statement stmt = null;
			try 
			{
				stmt = conn.createStatement();
				stmt.executeUpdate("drop table if exists events");
				stmt.executeUpdate("create table events "
						+ "(eventID VARCHAR(25) UNIQUE PRIMARY KEY,  "
						+ "descendant VARCHAR(50) NOT NULL, "
						+ "personID VARCHAR(25) NOT NULL, "
						+ "latitude DOUBLE NOT NULL, "
						+ "longitude DOUBLE NOT NULL, "
						+ "country VARCHAR(50) NOT NULL, "
						+ "city VARCHAR(50) NOT NULL, "
						+ "eventType VARCHAR(50) NOT NULL, "
						+ "year INT NOT NULL, "
						+ "foreign key (descendant) references users(username), "
	                    + "foreign key (personID) references persons(personID)"
	                    + ")");
			}
			finally
			{
				if(stmt != null)
				{
					stmt.close();
					stmt = null;
				}
			}
		}
		catch (SQLException e)
		{
			throw new DataAccessException("Clear of event table failed.");
		}
	}
	
	/**
	 * Inserts new event into event table
	 * @param event new Event object
	 * @throws DataAccessException
	 */
	public void insertEvent(Event event) throws DataAccessException 
	{
        try
        {
            PreparedStatement stmt = null;
            try 
            {
                String sql = "insert into events (eventID, descendant, personID, latitude, longitude, country, city, eventType, year) values (?,?,?,?,?,?,?,?,?)";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1,event.getEventID());
                stmt.setString(2,event.getDescendant());
                stmt.setString(3,event.getPersonID());
                stmt.setDouble(4,event.getLatitude());
                stmt.setDouble(5,event.getLongitude());
                stmt.setString(6,event.getCountry());
                stmt.setString(7,event.getCity());
                stmt.setString(8, event.getEventType());
                stmt.setInt(9, event.getYear());
                stmt.executeUpdate();
            }
            finally 
            {
                if (stmt != null)
                {
                    stmt.close();
                }
            }
        }
        catch (SQLException e)
        {
            throw new DataAccessException("Error encountered during event insert");
        }
	}
	
	/**
	 * Assigns related events to a particular person
	 * @param person	Person object being assigned events to
	 * @return	number of events assigned
	 * @throws DataAccessException
	 */
	public int makeRootsEvents(Person root) throws DataAccessException
	{
        int rootBirthYear = 1993;

        //making root's birth
        Event birth = new Event();
        Random rand = new Random();

        birth.setEventID(UUID.randomUUID().toString());
        birth.setDescendant(root.getDescendant());
        birth.setPersonID(root.getPersonID());
        int r = rand.nextInt(locationArray.data.length);
        Location randLocation = locationArray.data[r];
        birth.setLatitude(randLocation.getLatitude());
        birth.setLongitude(randLocation.getLongitude());
        birth.setCountry(randLocation.getCountry());
        birth.setCity(randLocation.getCity());
        birth.setEventType("Birth");
        birth.setYear(rootBirthYear);

        insertEvent(birth);

        //Making root's baptism
        Event baptism = new Event();
        baptism.setEventID(UUID.randomUUID().toString());
        baptism.setDescendant(root.getDescendant());
        baptism.setPersonID(root.getPersonID());
        r = rand.nextInt(locationArray.data.length);
        randLocation = locationArray.data[r];
        baptism.setLatitude(randLocation.getLatitude());
        baptism.setLongitude(randLocation.getLongitude());
        baptism.setCountry(randLocation.getCountry());
        baptism.setCity(randLocation.getCity());
        baptism.setEventType("Baptism");
        baptism.setYear(rootBirthYear + 8);

        insertEvent(baptism);

        //Making root's graduation
        Event graduation = new Event();
        graduation.setEventID(UUID.randomUUID().toString());
        graduation.setDescendant(root.getDescendant());
        graduation.setPersonID(root.getPersonID());
        r = rand.nextInt(locationArray.data.length);
        randLocation = locationArray.data[r];
        graduation.setLatitude(randLocation.getLatitude());
        graduation.setLongitude(randLocation.getLongitude());
        graduation.setCountry(randLocation.getCountry());
        graduation.setCity(randLocation.getCity());
        graduation.setEventType("Graduation");
        graduation.setYear(rootBirthYear + 18);

        insertEvent(graduation);

        return rootBirthYear;
	}
	
	/**
	 * Recursive function to build events for parents of child
	 * @param mother	Mother person object of child's mother
	 * @param father	Father person object of child's father
	 * @param birthYear	birth year of child
	 * @return	number of events generated
	 * @throws DataAccessException
	 */
	public int generateParentEventData(Person mother, Person father, int birthYear) throws DataAccessException
	{
        Event birth = new Event(); //making mothers's birth
        Random rand = new Random();
        int parentsBirthDate = birthYear - 25;

        birth.setEventID(UUID.randomUUID().toString());
        birth.setDescendant(mother.getDescendant());
        birth.setPersonID(mother.getPersonID());
        int r = rand.nextInt(locationArray.data.length);
        Location randLocation = locationArray.data[r];
        birth.setLatitude(randLocation.getLatitude());
        birth.setLongitude(randLocation.getLongitude());
        birth.setCountry(randLocation.getCountry());
        birth.setCity(randLocation.getCity());
        birth.setEventType("Birth");
        birth.setYear(parentsBirthDate);

        insertEvent(birth); //inserted mother's birth

        birth.setEventID(UUID.randomUUID().toString()); //Making father's birth
        birth.setDescendant(father.getDescendant());
        birth.setPersonID(father.getPersonID());
        r = rand.nextInt(locationArray.data.length);
        randLocation = locationArray.data[r];
        birth.setLatitude(randLocation.getLatitude());
        birth.setLongitude(randLocation.getLongitude());
        birth.setCountry(randLocation.getCountry());
        birth.setCity(randLocation.getCity());
        birth.setEventType("Birth");
        birth.setYear(parentsBirthDate);

        insertEvent(birth); //inserts father's birth

        Event death = new Event();  //making mothers death

        death.setEventID(UUID.randomUUID().toString());
        death.setDescendant(mother.getDescendant());
        death.setPersonID(mother.getPersonID());
        r = rand.nextInt(locationArray.data.length);
        randLocation = locationArray.data[r];
        death.setLatitude(randLocation.getLatitude());
        death.setLongitude(randLocation.getLongitude());
        death.setCountry(randLocation.getCountry());
        death.setCity(randLocation.getCity());
        death.setEventType("Death");
        death.setYear(birthYear + 55);

        insertEvent(death); //inserts mothers death

        death.setEventID(UUID.randomUUID().toString()); //makes father's death
        death.setDescendant(father.getDescendant());
        death.setPersonID(father.getPersonID());
        r = rand.nextInt(locationArray.data.length);
        randLocation = locationArray.data[r];
        death.setLatitude(randLocation.getLatitude());
        death.setLongitude(randLocation.getLongitude());
        death.setCountry(randLocation.getCountry());
        death.setCity(randLocation.getCity());
        death.setEventType("Death");
        death.setYear(birthYear + 50);

        insertEvent(death); //inserts father's death into database

        Event marriage = new Event();  //making marriage event for mother

        marriage.setEventID(UUID.randomUUID().toString());
        marriage.setDescendant(mother.getDescendant());
        marriage.setPersonID(mother.getPersonID());
        r = rand.nextInt(locationArray.data.length);
        randLocation = locationArray.data[r];
        marriage.setLatitude(randLocation.getLatitude());
        marriage.setLongitude(randLocation.getLongitude());
        marriage.setCountry(randLocation.getCountry());
        marriage.setCity(randLocation.getCity());
        marriage.setEventType("Marriage");
        marriage.setYear(parentsBirthDate + 21);

        insertEvent(marriage); //inserts marriage in for mother

        marriage.setEventID(UUID.randomUUID().toString());
        marriage.setDescendant(father.getDescendant());
        marriage.setPersonID(father.getPersonID());
        marriage.setLatitude(randLocation.getLatitude());
        marriage.setLongitude(randLocation.getLongitude());
        marriage.setCountry(randLocation.getCountry());
        marriage.setCity(randLocation.getCity());
        marriage.setEventType("Marriage");
        marriage.setYear(parentsBirthDate + 21);

        insertEvent(marriage); //inserts marriage in for father

        return parentsBirthDate;
	}
	
	/**
	 * Selects single event from event table
	 * @param eventID 	unique ID of event
	 * @return event 	associated w/ event ID
	 * @throws DataAccessException
	 */
	public Event selectEvent(String eventID) throws DataAccessException
	{
        Event event = new Event();
        try 
        {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try
            {
                String sql = "select * from events WHERE eventID = '" + eventID +"'";
                stmt = conn.prepareStatement(sql);

                rs = stmt.executeQuery();
                while (rs.next())
                {
                    event.setEventID(rs.getString(1));
                    event.setDescendant(rs.getString(2));
                    event.setPersonID(rs.getString(3));
                    event.setLatitude(rs.getDouble(4));
                    event.setLongitude(rs.getDouble(5));
                    event.setCountry(rs.getString(6));
                    event.setCity(rs.getString(7));
                    event.setEventType(rs.getString(8));
                    event.setYear(rs.getInt(9));
                }
            }
            finally
            {
                if (rs != null) 
                {
                    rs.close();
                }
                if (stmt != null)
                {
                    stmt.close();
                }
            }
        }
        catch (SQLException e)
        {
            throw new DataAccessException("Select of single Event failed");
        }
        return event;
	}
	
	/**
	 * Selects all the events associated with a user
	 * @param username	User name of user that all events are being requested for
	 * @return All events associated with that person in an array
	 * @throws DataAccessException
	 */
	public ArrayList<Event> selectAllEvents(String username) throws DataAccessException
	{
        ArrayList<Event> eventArray = new ArrayList<Event>();
        try
        {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try
            {
                String sql = "select * from events WHERE descendant = '" + username + "'";
                stmt = conn.prepareStatement(sql);

                rs = stmt.executeQuery();
                while (rs.next())
                {
                    Event event = new Event();
                    event.setEventID(rs.getString(1));
                    event.setDescendant(rs.getString(2));
                    event.setPersonID(rs.getString(3));
                    event.setLatitude(rs.getDouble(4));
                    event.setLongitude(rs.getDouble(5));
                    event.setCountry(rs.getString(6));
                    event.setCity(rs.getString(7));
                    event.setEventType(rs.getString(8));
                    event.setYear(rs.getInt(9));
                    eventArray.add(event);
                }
            }
            finally
            {
                if (rs != null)
                {
                    rs.close();
                }
                if (stmt != null)
                {
                    stmt.close();
                }
            }
        }
        catch (SQLException e)
        {
            throw new DataAccessException("Select all events failed");
        }
        return eventArray;
	}
	
	/**
	 * Deletes all events associated with User
	 * @param user 		User object for finding associated events
	 * @throws DataAccessException
	 */
	public void deleteAllEventsOfUser(User user) throws DataAccessException
	{
        try
        {
            Statement stmt = null;
            try
            {
                stmt = conn.createStatement();
                stmt.executeUpdate("DELETE FROM events WHERE descendant = '" + user.getUsername() + "'");
            }
            finally
            {
                if (stmt != null)
                {
                    stmt.close();
                    stmt = null;
                }
            }
        }
        catch (SQLException e)
        {
            throw new DataAccessException("deleteAllEventsOfUser failed");
        }
	}
	
	/**
	 * Find if requested event exists in table
	 * @param eventID 	Unique ID of event
	 * @return if event is in table
	 * @throws DataAccessException
	 */
	public boolean doesEventExist(String eventID) throws DataAccessException
	{
        try
        {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try
            {
                String sql = "select * from events WHERE eventID = '" + eventID + "'";
                stmt = conn.prepareStatement(sql);
                rs = stmt.executeQuery();
                if (!rs.next())
                {
                    throw new DataAccessException("No such eventID");
                } 
                else
                {
                    return true;
                }
            }
            finally
            {
                if (rs != null)
                {
                    rs.close();
                }
                if (stmt != null)
                {
                    stmt.close();
                }
            }
        }
        catch (SQLException e)
        {
            throw new DataAccessException("No such eventID");
        }
	}
	
    public String tableToString() throws DataAccessException {
        StringBuilder out = new StringBuilder();
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "select * from events";
                stmt = conn.prepareStatement(sql);

                rs = stmt.executeQuery();
                while (rs.next()) {
                    String eventID = rs.getString(1);
                    String descendant = rs.getString(2);
                    String personId = rs.getString(3);
                    Double latitude = rs.getDouble(4);
                    Double longitude = rs.getDouble(5);
                    String country = rs.getString(6);
                    String city = rs.getString(7);
                    String eventType = rs.getString(8);
                    int year = rs.getInt(9);

                    out.append((eventID + "\t" + descendant + "\t" + personId + "\t" + latitude + "\t" + longitude + "\t" + country + "\t" + city + "\t" + eventType + "\t" + year + "\n"));
                }
            }
            finally {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
        catch (SQLException e) {
            throw new DataAccessException("Table to string events failed");
        }
        return out.toString();
    }
}
