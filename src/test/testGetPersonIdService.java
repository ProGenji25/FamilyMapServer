package test;

import org.junit.*;


import DataAccess.*;
import Model.*;

import Result.GetPersonIdResult;
import Service.GetPersonIdService;

import static org.junit.Assert.*;

public class testGetPersonIdService
{

    private GetPersonIdService myPersonIDService;

    @Before
    public void setUp()
    {
        myPersonIDService = new GetPersonIdService();
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
        myPersonIDService = null;
    }

    @Test
    public void testPersonID()
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

        AuthToken testAuth = new AuthToken();
        testAuth.setAuthToken("auth");
        testAuth.setPersonID("ID");
        testAuth.setUsername("golde3");
        
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
        
        AuthToken testAuth2 = new AuthToken();
        testAuth2.setAuthToken("badauth");
        testAuth2.setPersonID("id");
        testAuth2.setUsername("jaina");

        try
        {
            Database db = new Database();
            db.openConnection();
            db.clearTables();
            PersonDao myPersonDAO = db.getMyPersonDAO();
            myPersonDAO.insertPerson(testPerson);

            AuthTokenDao myAuthDAO = db.getMyAuthTokenDAO();
            myAuthDAO.insertToken(testAuth);

            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
        	fail(e.getMessage());
        }

        GetPersonIdResult expectedResponse = new GetPersonIdResult(testPerson);
        expectedResponse.setSuccess(true);

        GetPersonIdResult outputResponse = myPersonIDService.personID(testPerson.getPersonID(), testAuth.getAuthToken());

        assertEquals(expectedResponse.getSuccess(), outputResponse.getSuccess());

        GetPersonIdResult badExpectedResponse = new GetPersonIdResult();
        badExpectedResponse.setSuccess(false);
        badExpectedResponse.setMessage("PersonID does not match given authToken");

        GetPersonIdResult badOutputResponse = myPersonIDService.personID(testPerson2.getPersonID(), testAuth2.getAuthToken());

        assertEquals(badExpectedResponse.getSuccess(), badOutputResponse.getSuccess());
    }
}    