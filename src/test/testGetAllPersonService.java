package test;

import org.junit.*;


import DataAccess.*;
import Model.*;

import Result.GetAllPersonsResult;
import Service.GetAllPersonsService;

import static org.junit.Assert.*;

import java.util.ArrayList;


public class testGetAllPersonService
{
    private GetAllPersonsService myPersonGetAllService;

    @Before
    public void setUp()
    {
        myPersonGetAllService = new GetAllPersonsService();
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
        myPersonGetAllService = null;
    }

    @Test
    public void testPersonGetAll()
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

        Person testPerson3 = new Person(); //person 3
        testPerson3.setDescendant("golde3");
        testPerson3.setPersonID("p3id");
        testPerson3.setFather("Admiral Han'Gerrel");
        testPerson3.setSpouse("jordan");
        testPerson3.setMother("Admiral Raan");
        testPerson3.setFirstName("Tali'Zorah");
        testPerson3.setLastName("nar'Rayaa");
        testPerson3.setGender("f");

        AuthToken testAuth = new AuthToken();
        testAuth.setAuthToken("auth");
        testAuth.setPersonID("ID");
        testAuth.setUsername("golde3");

        AuthToken testAuth2 = new AuthToken();
        testAuth2.setAuthToken("badauth");
        testAuth2.setPersonID("id");
        testAuth2.setUsername("jaina");

        try
        {
            Database db = new Database();
            db.openConnection();
            db.clearTables();
            UserDao myUserDAO = db.getMyUserDAO();
            myUserDAO.insertUser(testUser);
            myUserDAO.insertUser(testUser2);
            PersonDao myPersonDAO = db.getMyPersonDAO();
            myPersonDAO.insertPerson(testPerson);
            myPersonDAO.insertPerson(testPerson2);
            myPersonDAO.insertPerson(testPerson3);
            AuthTokenDao myAuthDAO = db.getMyAuthTokenDAO();
            myAuthDAO.insertToken(testAuth);
            myAuthDAO.insertToken(testAuth2);
            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
        	fail(e.getMessage());
        }
        
        ArrayList<Person> inputPersons = new ArrayList<Person>();
        inputPersons.add(testPerson);
        inputPersons.add(testPerson3);

        GetAllPersonsResult expectedResponse = new GetAllPersonsResult();
        expectedResponse.setPersons(inputPersons);
        expectedResponse.setSuccess(true);

        GetAllPersonsResult outputResponse = myPersonGetAllService.getPeople(testAuth.getAuthToken());

        assertEquals(expectedResponse.getSuccess(), outputResponse.getSuccess());
        
        GetAllPersonsResult badExpectedResponse = new GetAllPersonsResult();
        badExpectedResponse.setSuccess(false);
        badExpectedResponse.setMessage("no such authToken");

        GetAllPersonsResult badOutputResponse = myPersonGetAllService.getPeople("bogus");

        assertEquals(badExpectedResponse.getSuccess(), badOutputResponse.getSuccess());
    }
}