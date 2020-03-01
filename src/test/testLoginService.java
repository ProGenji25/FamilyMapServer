package test;

import org.junit.*;

import DataAccess.*;
import Model.User;

import Request.LoginRequest;

import Result.LoginResult;
import Service.LoginService;

import static org.junit.Assert.*;

public class testLoginService 
{
    private LoginService myLoginService;

    @Before
    public void setUp() 
    {
        myLoginService = new LoginService();
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
        myLoginService = null;
    }

    @Test
    public void testLogin()
    {
    	User testUser = new User();
        testUser.setUsername("golde3");
        testUser.setPassword("Legion17");
        testUser.setEmail("jg@gmail.com");
        testUser.setFirstName("jordan");
        testUser.setLastName("golden");
        testUser.setGender("m");
        testUser.setPersonID("ID");
        
        try
        {
            Database db = new Database();
            db.openConnection();
            db.clearTables();
            UserDao myUserDAO = db.getMyUserDAO();
            myUserDAO.insertUser(testUser);
            db.closeConnection(true);
        }
        catch (DataAccessException e)
        {
        	fail(e.getMessage());
        }

        LoginRequest inputRequest = new LoginRequest();
        inputRequest.setUsername(testUser.getUsername());
        inputRequest.setPassword(testUser.getPassword());

        LoginResult expectedResponse = new LoginResult();
        expectedResponse.setSuccess(true);
        expectedResponse.setUsername(testUser.getUsername());
        expectedResponse.setPersonID(testUser.getPersonID());

        LoginResult outputResponse = myLoginService.login(inputRequest);
        expectedResponse.setAuthToken(outputResponse.getAuthToken());

        assertEquals(expectedResponse.getSuccess(), outputResponse.getSuccess());

        LoginResult badExpectedResponse = new LoginResult();
        badExpectedResponse.setSuccess(false);
        badExpectedResponse.setMessage("no such username and/or password");

        inputRequest.setUsername("Bogus");
        LoginResult badOutputResponse = myLoginService.login(inputRequest);

        assertEquals(badExpectedResponse.getSuccess(), badOutputResponse.getSuccess());
    }
}