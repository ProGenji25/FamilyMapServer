package test;

import org.junit.*;
import DataAccess.*;
import Model.*;

import Result.ClearResult;
import Service.ClearService;

import static org.junit.Assert.*;

public class testClearService
{
    private ClearService myClearService;

    @Before
    public void setUp() throws DataAccessException
    {
        myClearService = new ClearService();
    }

    @After
    public void tearDown() throws DataAccessException 
    {
        myClearService = null;
    }

    @Test
    public void testClear()
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

            Person testPerson = new Person(testUser);
            testPerson.setSpouse("Tali");
            testPerson.setMother("Janet");
            testPerson.setFather("John");

            AuthToken testAuth = new AuthToken();
            testAuth.setAuthToken("auth");
            testAuth.setPersonID("ID");
            testAuth.setUsername("golde3");


            Database db = new Database();
            db.openConnection();
            db.clearTables();
            UserDao myUserDAO = db.getMyUserDAO();
            PersonDao myPersonDAO = db.getMyPersonDAO();
            EventDao myEventDAO = db.getMyEventDAO();
            AuthTokenDao myAuthDAO = db.getMyAuthTokenDAO();

            myUserDAO.insertUser(testUser);

            myPersonDAO.insertPerson(testPerson);

            myEventDAO.insertEvent(testEvent1);

            myAuthDAO.insertToken(testAuth);

            db.closeConnection(true);

            ClearResult expectedResponse = new ClearResult();
            expectedResponse.setMessage("Clear succeeded");

            ClearResult output = myClearService.clear();

            assertEquals(expectedResponse.getMessage(), output.getMessage());
        }
        catch (DataAccessException e)
        {
        	fail(e.getMessage());
        }
    }
}    