package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

import DataAccess.*;
import Model.Event;
import Model.Person;
import Model.User;

public class EventDAOTest 
{
    private EventDao myEventDAO;
    private Database db;

    @Before
    public void setUp() throws DataAccessException
    {
        db = new Database();
        db.openConnection();
        db.clearTables();
        myEventDAO = db.getMyEventDAO();
    }

    @After
    public void tearDown() throws DataAccessException
    {
        db.closeConnection(false);
        myEventDAO = null;
        db = null;
    }

    @Test
    public void testResetEventTable()
    {
        try
        {
        	Event testEvent1 = new Event();
            testEvent1.setEventID("uniqueEventID");
            testEvent1.setDescendant("golde3");
            testEvent1.setPersonID("ID");
            testEvent1.setLatitude(-1.0);
            testEvent1.setLongitude(1.0);
            testEvent1.setCountry("North pole");
            testEvent1.setCity("Santa's house");
            testEvent1.setEventType("Cookies at santa's");
            testEvent1.setYear(2017);


            myEventDAO.insertEvent(testEvent1); //inserts event into event table

            String answer = new String("uniqueEventID" + "\t" + "golde3" + "\t" + "ID" + "\t" + "-1.0" + "\t" + "1.0" + "\t" 
            				+ "North pole" + "\t" + "Santa's house" + "\t" + "Cookies at santa's" + "\t" + "2017"+ "\n");
            assertEquals(answer, myEventDAO.tableToString()); //asserts the table isn't empty

            myEventDAO.clear(); //resets table

            assertEquals("",myEventDAO.tableToString());


        } catch (DataAccessException e){
        	fail(e.getMessage());
        }
    }

    @Test
    public void testInsertEventIntoTable()
    {
        try
        {
        	Event testEvent1 = new Event();
            testEvent1.setEventID("uniqueEventID");
            testEvent1.setDescendant("golde3");
            testEvent1.setPersonID("ID");
            testEvent1.setLatitude(-1.0);
            testEvent1.setLongitude(1.0);
            testEvent1.setCountry("North pole");
            testEvent1.setCity("Santa's house");
            testEvent1.setEventType("Cookies at santa's");
            testEvent1.setYear(2017);

            String answer = new String("uniqueEventID" + "\t" + "golde3" + "\t" + "ID" + "\t" + "-1.0" + "\t" + "1.0" + "\t" 
    				+ "North pole" + "\t" + "Santa's house" + "\t" + "Cookies at santa's" + "\t" + "2017"+ "\n");

            assertNotEquals(answer, myEventDAO.tableToString()); //asserts that the table is not hard coded just to pass the test
            myEventDAO.insertEvent(testEvent1);
            assertEquals(answer, myEventDAO.tableToString());

        }
        catch (DataAccessException e)
        {
        	fail(e.getMessage());
        }
    }

    @Test
    public void testMakeRootsEvents()
    {
        try
        {
        	User testUser = new User();
            testUser.setUsername("golde3");
            testUser.setPassword("Legion17");
            testUser.setEmail("jg@gmail.com");
            testUser.setFirstName("jordan");
            testUser.setLastName("golden");
            testUser.setGender("m");
            testUser.setPersonID("ID");
             
            Person testPerson = new Person(testUser);
            testPerson.setSpouse("Tali");
            testPerson.setMother("Janet");
            testPerson.setFather("John");

            int expectedNumEvents = 3;
            int birthYear = 0;

            String eventTablePreliminary = myEventDAO.tableToString();
            String[] eventsPreliminary = eventTablePreliminary.split("\n");

            assertNotEquals(expectedNumEvents, eventsPreliminary.length);
            assertNotEquals(birthYear,1993);

            birthYear = myEventDAO.makeRootsEvents(testPerson);

            String eventTable = myEventDAO.tableToString();
            String[] events = eventTable.split("\n");

            assertEquals(expectedNumEvents, events.length);
            assertEquals(birthYear,1993);

        } 
        catch (DataAccessException e)
        {
        	fail(e.getMessage());
        }
    }

    @Test
    public void testGenerateEventDataParents()
    {
        try
        {
        	User testUser = new User();
            testUser.setUsername("golde3");
            testUser.setPassword("Legion17");
            testUser.setEmail("jg@gmail.com");
            testUser.setFirstName("jordan");
            testUser.setLastName("golden");
            testUser.setGender("m");
            testUser.setPersonID("ID");
             
            Person testPerson = new Person(testUser);
            testPerson.setSpouse("Tali");
            testPerson.setMother("Janet");
            testPerson.setFather("John");

            User testUser2 = new User(); //user 2
            testUser2.setUsername("jaina");
            testUser2.setPassword("frost");
            testUser2.setEmail("jaina@gmail.com");
            testUser2.setFirstName("jaina");
            testUser2.setLastName("proudmoore");
            testUser2.setGender("f");
            testUser2.setPersonID("id");

            Person testPerson2 = new Person(testUser2);
            testPerson2.setSpouse("Daniel");
            testPerson2.setFather("Jeffrey");
            testPerson2.setMother("Nelly");

            //test event fill logic
            int expectedNumEvents = 6;
            int exptectedBirthDateOfParents = 1935;
            int actualBirhtYearOFparents = 0;

            String eventsOutput = myEventDAO.tableToString();
            String[] eventsArray = eventsOutput.split("\n");

            assertNotEquals(expectedNumEvents, eventsArray.length);
            assertNotEquals(exptectedBirthDateOfParents, actualBirhtYearOFparents);

            actualBirhtYearOFparents = myEventDAO.generateParentEventData(testPerson2,testPerson,1960);

            eventsOutput = myEventDAO.tableToString();
            String[] outputArray = eventsOutput.split("\n");

            assertEquals(expectedNumEvents, outputArray.length);
            assertEquals(exptectedBirthDateOfParents, actualBirhtYearOFparents);


        } 
        catch (DataAccessException e)
        {
        	fail(e.getMessage());
        }
    }

    @Test
    public void testDeleteAllEventsOfuser()
    {
        try
        {
        	User testUser = new User();
            testUser.setUsername("golde3");
            testUser.setPassword("Legion17");
            testUser.setEmail("jg@gmail.com");
            testUser.setFirstName("jordan");
            testUser.setLastName("golden");
            testUser.setGender("m");
            testUser.setPersonID("ID");
            
            Event testEvent1 = new Event();
            testEvent1.setEventID("uniqueEventID");
            testEvent1.setDescendant("golde3");
            testEvent1.setPersonID("ID");
            testEvent1.setLatitude(-1.0);
            testEvent1.setLongitude(1.0);
            testEvent1.setCountry("North pole");
            testEvent1.setCity("Santa's house");
            testEvent1.setEventType("Cookies at santa's");
            testEvent1.setYear(2017);

            myEventDAO.insertEvent(testEvent1);

            String answer = new String("uniqueEventID" + "\t" + "golde3" + "\t" + "ID" + "\t" + "-1.0" + "\t" + "1.0" + "\t" 
    					+ "North pole" + "\t" + "Santa's house" + "\t" + "Cookies at santa's" + "\t" + "2017"+ "\n");

            myEventDAO.deleteAllEventsOfUser(testUser);

            assertNotEquals(answer, myEventDAO.tableToString());

        } 
        catch (DataAccessException e)
        {
        	fail(e.getMessage());
        }
    }

    @Test
    public void testSelectSingleEvent()
    {
        try
        {
        	Event testEvent1 = new Event();
            testEvent1.setEventID("uniqueEventID");
            testEvent1.setDescendant("golde3");
            testEvent1.setPersonID("ID");
            testEvent1.setLatitude(-1.0);
            testEvent1.setLongitude(1.0);
            testEvent1.setCountry("North pole");
            testEvent1.setCity("Santa's house");
            testEvent1.setEventType("Cookies at santa's");
            testEvent1.setYear(2017);

            

            myEventDAO.insertEvent(testEvent1);
           

            Event output = myEventDAO.selectEvent(testEvent1.getEventID());

            assertEquals(testEvent1.getEventID(), output.getEventID());

        } catch (DataAccessException e){
        	fail(e.getMessage());
        }
    }

    @Test
    public void testDoesEventExist()
    {
        try
        {
        	Event testEvent1 = new Event();
            testEvent1.setEventID("uniqueEventID");
            testEvent1.setDescendant("golde3");
            testEvent1.setPersonID("ID");
            testEvent1.setLatitude(-1.0);
            testEvent1.setLongitude(1.0);
            testEvent1.setCountry("North pole");
            testEvent1.setCity("Santa's house");
            testEvent1.setEventType("Cookies at santa's");
            testEvent1.setYear(2017);

            myEventDAO.insertEvent(testEvent1);

            boolean output = myEventDAO.doesEventExist(testEvent1.getEventID());
            assertEquals(output, true);

            try
            {
                output = myEventDAO.doesEventExist("BogusEventID");
                assertEquals(true,false);
            }
            catch (DataAccessException e)
            {
                String answer = "No such eventID";
                assertEquals(answer, e.getMessage());
            }

        }
        catch (DataAccessException e)
        {
        	fail(e.getMessage());
        }
    }

    @Test
    public void testSelectAllEvents()
    {
        try
        {
        	Event testEvent1 = new Event();
            testEvent1.setEventID("uniqueEventID");
            testEvent1.setDescendant("golde3");
            testEvent1.setPersonID("ID");
            testEvent1.setLatitude(-1.0);
            testEvent1.setLongitude(1.0);
            testEvent1.setCountry("North pole");
            testEvent1.setCity("Santa's house");
            testEvent1.setEventType("Cookies at santa's");
            testEvent1.setYear(2017);

            Event testEvent2 = new Event();
            testEvent2.setEventID("uniqueEventID2");
            testEvent2.setDescendant("jaina");
            testEvent2.setPersonID("id");
            testEvent2.setLatitude(99.0);
            testEvent2.setLongitude(-99.0);
            testEvent2.setCountry("Equador");
            testEvent2.setCity("Quito");
            testEvent2.setEventType("eatin lunch");
            testEvent2.setYear(1990);

            Event testEvent3 = new Event();
            testEvent3.setEventID("uniqueEventID3");
            testEvent3.setDescendant("golde3");
            testEvent3.setPersonID("ID");
            testEvent3.setLatitude(55.0);
            testEvent3.setLongitude(-55.0);
            testEvent3.setCountry("Brazil");
            testEvent3.setCity("Brasilia");
            testEvent3.setEventType("mission lunch");
            testEvent3.setYear(2014);

            myEventDAO.insertEvent(testEvent1);
            myEventDAO.insertEvent(testEvent2);
            myEventDAO.insertEvent(testEvent3);

            ArrayList<Event> expectedEvents = new ArrayList<Event>();
            expectedEvents.add(testEvent1);
            expectedEvents.add(testEvent3);

            ArrayList<Event> output = myEventDAO.selectAllEvents("golde3");

            for (int i = 0; i < output.size(); i++)
            {
                assertEquals(expectedEvents.get(i).getEventID(), output.get(i).getEventID());
            }


        }
        catch (DataAccessException e)
        {
        	fail(e.getMessage());
        }
    }

    @Test
    public void testTableToString()
    {
        try
        {
            String answer = "";
            assertEquals(answer,myEventDAO.tableToString());

            Event testEvent1 = new Event();
            testEvent1.setEventID("uniqueEventID");
            testEvent1.setDescendant("golde3");
            testEvent1.setPersonID("ID");
            testEvent1.setLatitude(-1.0);
            testEvent1.setLongitude(1.0);
            testEvent1.setCountry("North pole");
            testEvent1.setCity("Santa's house");
            testEvent1.setEventType("Cookies at santa's");
            testEvent1.setYear(2017);

            answer = new String("uniqueEventID" + "\t" + "golde3" + "\t" + "ID" + "\t" + "-1.0" + "\t" + "1.0" + "\t" 
    				+ "North pole" + "\t" + "Santa's house" + "\t" + "Cookies at santa's" + "\t" + "2017"+ "\n");
            myEventDAO.insertEvent(testEvent1);
            assertEquals(answer, myEventDAO.tableToString());


        }
        catch (DataAccessException e)
        {
        	fail(e.getMessage());
        }
    }
}    