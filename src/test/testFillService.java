package test;

import org.junit.*;

import DataAccess.*;

import Model.User;

import Result.FillResult;
import Service.FillService;

import static org.junit.Assert.*;

public class testFillService
{
    private FillService myFillService;

    @Before
    public void setUp() {
        myFillService = new FillService();
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
        myFillService = null;
    }

    @Test
    public void testFill()
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
            
            Database db = new Database();
            db.openConnection();
            db.clearTables();
            UserDao myUserDAO = db.getMyUserDAO();
            myUserDAO.insertUser(testUser);
            db.closeConnection(true);


            FillResult outputResponse = myFillService.fill(testUser.getUsername(), 2);
            assertNotNull(outputResponse);

            FillResult badFillExpectedResponse = new FillResult();
            badFillExpectedResponse.setSuccess(false);
            badFillExpectedResponse.setMessage("This username does not exist.");

            FillResult badOutputResponse = myFillService.fill("bogus", 2);

            assertEquals(badFillExpectedResponse.getMessage(), badOutputResponse.getMessage());
        } 
        catch (DataAccessException e)
        {
        	fail(e.getMessage());
        }
    }
}    