package test;

import org.junit.*;


import DataAccess.*;
import Model.*;

import Result.GetEventIdResult;
import Service.GetEventIdService;

import static org.junit.Assert.*;


public class testGetEventIdService
{
    private GetEventIdService myEventIDService;

    @Before
    public void setUp() 
    {
        myEventIDService = new GetEventIdService();
        Database db = new Database();
        try
        {
            db.openConnection();
            db.clearTables();
            db.closeConnection(true);
            db = null;
        } catch (DataAccessException e)
        {
        	fail(e.getMessage());
        }
    }

    @After
    public void tearDown() 
    {
        myEventIDService = null;
    }


    @Test
    public void testEventID()
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
            myAuthDAO.insertToken(testAuth);
            myAuthDAO.insertToken(testAuth2);
            db.closeConnection(true);

            GetEventIdResult expectedResponse = new GetEventIdResult(testEvent1);
            expectedResponse.setSuccess(true);

            GetEventIdResult outputResponse = myEventIDService.eventID(testEvent1.getEventID(), testAuth.getAuthToken());

            assertEquals(expectedResponse.getSuccess(), outputResponse.getSuccess());

            GetEventIdResult badExpectedResponse = new GetEventIdResult();
            badExpectedResponse.setSuccess(false);
            badExpectedResponse.setMessage("Descendant of event and username of auth token do not match");

            GetEventIdResult badOutputResponse = myEventIDService.eventID(testEvent1.getEventID(), testAuth2.getAuthToken());

            assertEquals(badExpectedResponse.getSuccess(), badOutputResponse.getSuccess());

        }
        catch (DataAccessException e)
        {
        	fail(e.getMessage());
        }
    }
}    