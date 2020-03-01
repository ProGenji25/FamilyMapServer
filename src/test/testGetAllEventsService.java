package test;

import org.junit.*;
import DataAccess.*;
import Model.*;

import Result.GetAllEventsResult;
import Service.GetAllEventsService;

import static org.junit.Assert.*;

import java.util.ArrayList;


public class testGetAllEventsService 
{
    private GetAllEventsService myEventGetAllService;

    @Before
    public void setUp() 
    {
        myEventGetAllService = new GetAllEventsService();
        Database db = new Database();
        try
        {
            db.openConnection();
            db.clearTables();
            db.closeConnection(true);
            db = null;
        }
        catch (DataAccessException e)
        {
        	fail(e.getMessage());
        }

    }

    @After
    public void tearDown()
    {
        myEventGetAllService = null;
    }

    @Test
    public void testEventGetALl()
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

            User testUser2 = new User(); //user 2
            testUser2.setUsername("jaina");
            testUser2.setPassword("frost");
            testUser2.setEmail("jaina@gmail.com");
            testUser2.setFirstName("jaina");
            testUser2.setLastName("proudmoore");
            testUser2.setGender("f");
            testUser2.setPersonID("id");

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

            AuthToken testAuth = new AuthToken();
            testAuth.setAuthToken("auth");
            testAuth.setPersonID("ID");
            testAuth.setUsername("golde3");

            AuthToken testAuth2 = new AuthToken();
            testAuth2.setAuthToken("badauth");
            testAuth2.setPersonID("id");
            testAuth2.setUsername("jaina");
            
            Database db = new Database();
            db.openConnection();
            db.clearTables();
            UserDao myUserDAO = db.getMyUserDAO();
            EventDao myEventDAO = db.getMyEventDAO();
            AuthTokenDao myAuthDAO = db.getMyAuthTokenDAO();

            myUserDAO.insertUser(testUser);
            myUserDAO.insertUser(testUser2);
            myEventDAO.insertEvent(testEvent1);
            myEventDAO.insertEvent(testEvent2);
            myEventDAO.insertEvent(testEvent3);
            myAuthDAO.insertToken(testAuth);
            myAuthDAO.insertToken(testAuth2);
            db.closeConnection(true);

            GetAllEventsResult expectedResponse = new GetAllEventsResult();
            ArrayList<Event> input = new ArrayList<Event>();
            input.add(testEvent1);
            input.add(testEvent3);
            expectedResponse.setSuccess(true);
            expectedResponse.setEvents(input);

            GetAllEventsResult outputResponse = myEventGetAllService.events(testAuth.getAuthToken());

            assertEquals(expectedResponse.getSuccess(), outputResponse.getSuccess());


            GetAllEventsResult badExpectedResponse = new GetAllEventsResult();
            badExpectedResponse.setSuccess(false);
            badExpectedResponse.setMessage("no such authToken");

            GetAllEventsResult badOutputResponse = myEventGetAllService.events("bugos");

            assertEquals(badExpectedResponse.getSuccess(), badOutputResponse.getSuccess());

        }
        catch (DataAccessException e)
        {
        	fail(e.getMessage());
        }
    }
}