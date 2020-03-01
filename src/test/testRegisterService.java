package test;

import org.junit.*;

import DataAccess.*;

import Model.User;

import Request.RegisterRequest;

import Result.RegisterResult;
import Service.RegisterService;

import static org.junit.Assert.*;


public class testRegisterService 
{

    private RegisterService myRegisterService;

    @Before
    public void setUp()
    {
        myRegisterService = new RegisterService();
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
        myRegisterService = null;
    }

    @Test
    public void testRegister()
    {
    	User testUser = new User();
        testUser.setUsername("golde3");
        testUser.setPassword("Legion17");
        testUser.setEmail("jg@gmail.com");
        testUser.setFirstName("jordan");
        testUser.setLastName("golden");
        testUser.setGender("m");
        testUser.setPersonID("ID");

        RegisterRequest inputRequest = new RegisterRequest();

        inputRequest.setUserName(testUser.getUsername());
        inputRequest.setPassword(testUser.getPassword());
        inputRequest.setEmail(testUser.getEmail());
        inputRequest.setFirstName(testUser.getFirstName());
        inputRequest.setLastName(testUser.getLastName());
        inputRequest.setGender(testUser.getGender());

        RegisterResult expectedResponse = new RegisterResult();
        expectedResponse.setUsername(testUser.getUsername());
        expectedResponse.setPersonID(testUser.getPersonID());
        expectedResponse.setSuccess(true);

        RegisterResult outputResponse = myRegisterService.register(inputRequest);
        expectedResponse.setAuthToken(outputResponse.getAuthToken());
        expectedResponse.setPersonID(outputResponse.getPersonID());

        assertEquals(expectedResponse.getSuccess(), outputResponse.getSuccess());

        RegisterResult badExpectedResponse = new RegisterResult();
        badExpectedResponse.setSuccess(false);
        badExpectedResponse.setMessage("User already exists failed");

        RegisterResult badOutputResponse = myRegisterService.register(inputRequest);
        assertEquals(badExpectedResponse.getSuccess(), badOutputResponse.getSuccess());
    }
}