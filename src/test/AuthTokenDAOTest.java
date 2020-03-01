package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Model.AuthToken;
import DataAccess.*;

public class AuthTokenDAOTest 
{
    private AuthTokenDao myAuthDAO;
    private Database db;

    @Before
    public void setUp() throws DataAccessException
    {
        db = new Database();
        db.openConnection();
        db.clearTables();
        myAuthDAO = db.getMyAuthTokenDAO();
    }

    @After
    public void tearDown() throws DataAccessException
    {
        db.closeConnection(false);
        myAuthDAO = null;
        db = null;
    }

    @Test
    public void testResetTable()
    {
        try
        {
            AuthToken testAuth1 = new AuthToken();
            testAuth1.setAuthToken("token");
            testAuth1.setPersonID("jordan");
            testAuth1.setUsername("golde3");

            String expected = "token\tgolde3\tjordan\n";

            myAuthDAO.insertToken(testAuth1);

            assertEquals(expected, myAuthDAO.tableToString());

            expected = "";
            assertNotEquals(expected, myAuthDAO.tableToString());
            myAuthDAO.clear();
            assertEquals(expected,myAuthDAO.tableToString());


        }
        catch (DataAccessException e)
        {
        	fail(e.getMessage());
        }


    }

    @Test 
    public void testInsertPass()
    {
        try
        {
            AuthToken testAuth1 = new AuthToken();
            testAuth1.setAuthToken("token");
            testAuth1.setPersonID("jordan");
            testAuth1.setUsername("golde3");

            String expected = "token\tgolde3\tjordan\n";

            assertNotEquals(expected, myAuthDAO.tableToString());

            myAuthDAO.insertToken(testAuth1);

            assertEquals(expected, myAuthDAO.tableToString());

        }
        catch (DataAccessException e)
        {
        	fail(e.getMessage());
        }
    }
    
    @Test
    public void testInsertFail()
    {
    	boolean didItWork = true;
        try
        {
            AuthToken testAuth1 = new AuthToken();
            testAuth1.setAuthToken("token");
            testAuth1.setPersonID("jordan");
            testAuth1.setUsername("golde3");

            String expected = "token\tgolde3\tjordan\n";

            assertNotEquals(expected, myAuthDAO.tableToString());

            myAuthDAO.insertToken(testAuth1);
            myAuthDAO.insertToken(testAuth1);

            assertEquals(expected, myAuthDAO.tableToString());

        }
        catch (DataAccessException e)
        {
            didItWork = false;
        }
        assertFalse(didItWork);
    }

    @Test 
    public void testTableToString()
    {
        try
        {
            AuthToken testAuth1 = new AuthToken();
            testAuth1.setAuthToken("token");
            testAuth1.setPersonID("jordan");
            testAuth1.setUsername("golde3");


            assertEquals("",myAuthDAO.tableToString());
            myAuthDAO.insertToken(testAuth1);

            String expected = "token\tgolde3\tjordan\n";

            assertEquals(expected, myAuthDAO.tableToString());

        }
        catch (DataAccessException e)
        {
        	fail(e.getMessage());
        }
    }

    @Test 
    public void testDoesAuthTokenExistPass()
    {
        try
        {
            AuthToken testAuth1 = new AuthToken();
            testAuth1.setAuthToken("token");
            testAuth1.setPersonID("jordan");
            testAuth1.setUsername("golde3");

            myAuthDAO.insertToken(testAuth1);

            boolean output = myAuthDAO.doesAuthTokenExist(testAuth1.getAuthToken());
            assertEquals(true, output);

           

        }
        catch (DataAccessException e)
        {
        	fail(e.getMessage());
        }
    }
    
    @Test 
    public void testDoesAuthTokenExistFail()
    {
    	 try 
    	 {
             boolean output = myAuthDAO.doesAuthTokenExist("bogus");
             assertEquals(true, output);
         } 
    	 catch(DataAccessException e)
    	 {
             assertEquals("No such authToken exists.", e.getMessage());
         }
    }

    @Test 
    public void testSelectAuthTokenPass(){
        try
        {
            AuthToken testAuth1 = new AuthToken();
            AuthToken compare = null;
            testAuth1.setAuthToken("token");
            testAuth1.setPersonID("jordan");
            testAuth1.setUsername("golde3");

            myAuthDAO.insertToken(testAuth1);
            
            compare = myAuthDAO.selectAuthToken(testAuth1.getAuthToken());
            assertNotNull(compare);

        }
        catch (DataAccessException e)
        {
        	fail(e.getMessage());
        }
    }
    
    @Test 
    public void testSelectAuthTokenFail()
    {
        try
        {
            AuthToken testAuth1 = new AuthToken();
            AuthToken compare = null;
            testAuth1.setAuthToken("token");
            testAuth1.setPersonID("jordan");
            testAuth1.setUsername("golde3");
            
            compare = myAuthDAO.selectAuthToken(testAuth1.getAuthToken());
            assertNotNull(compare);

        }
        catch (DataAccessException e)
        {
        	fail(e.getMessage());
        }
    }
}
