package test;

import org.junit.* ;

import DataAccess.DataAccessException;
import DataAccess.Database;
import DataAccess.UserDao;

import static org.junit.Assert.* ;
import Model.User;


public class UserDAOTest 
{
    private UserDao myUserDAO;
    private Database db;

    @Before
    public void setUp() throws DataAccessException 
    {
        myUserDAO = new UserDao();
        db = new Database();
        db.openConnection();
        db.clearTables();
        myUserDAO = db.getMyUserDAO();

    }

    @After
    public void tearDown() throws DataAccessException
    {
        db.closeConnection(false);
        myUserDAO = null;
        db = null;
    }

    @Test
    public void testInsertUserIntoDatabaseDAO()
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

            myUserDAO.insertUser(testUser);

            String out = new String("golde3" + "\t" + "Legion17" + "\t" + "jg@gmail.com" + "\t" + "jordan" + "\t" + "golden" + "\t" + "m" + "\t" + "ID" + "\n");

            assertEquals(myUserDAO.tableToString(), out);
        }
        catch (DataAccessException e)
        {
        	fail(e.getMessage());
        }
    }

    @Test
    public void testResetTable()
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

            myUserDAO.insertUser(testUser);

            String expected = new String("golde3" + "\t" + "Legion17" + "\t" + "jg@gmail.com" + "\t" + "jordan" + "\t" + "golden" + "\t" + "m" + "\t" + "ID" + "\n");
            assertEquals(expected, myUserDAO.tableToString());

            expected = "";
            myUserDAO.clear();
            assertEquals(expected,myUserDAO.tableToString());

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
        	 User testUser = new User();
             testUser.setUsername("golde3");
             testUser.setPassword("Legion17");
             testUser.setEmail("jg@gmail.com");
             testUser.setFirstName("jordan");
             testUser.setLastName("golden");
             testUser.setGender("m");
             testUser.setPersonID("ID");

             String expected = new String("golde3" + "\t" + "Legion17" + "\t" + "jg@gmail.com" + "\t" + "jordan" + "\t" + "golden" + "\t" + "m" + "\t" + "ID" + "\n");
            myUserDAO.insertUser(testUser);
            assertEquals(expected,myUserDAO.tableToString());

        }
        catch (DataAccessException e)
        {
        	fail(e.getMessage());
        }
    }

    @Test
    public void testDoesUserExist()
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

            boolean output = myUserDAO.doesUsernameExist(testUser.getUsername());
            assertEquals(output, true);

            try
            {
                output = myUserDAO.doesUsernameExist("bogus");
            }
            catch(DataAccessException e)
            {
                assertEquals("This username does not exist.", e.getMessage());
            }

        } catch (DataAccessException e)
        {
        	assertEquals("This username does not exist.", e.getMessage());
        }
    }

    @Test
    public void testDeleteUser()
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

            myUserDAO.insertUser(testUser);


            String answer = new String("golde3" + "\t" + "Legion17" + "\t" + "jg@gmail.com" + "\t" + "jordan" + "\t" + "golden" + "\t" + "m" + "\t" + "ID" + "\n");

            assertEquals(answer,myUserDAO.tableToString());
        } 
        catch (DataAccessException e) 
        {
        	fail(e.getMessage());
        }
    }

    @Test
    public void testSelectUserModel()
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

            myUserDAO.insertUser(testUser);

            User output = myUserDAO.selectUser(testUser.getUsername());
            output.setPersonID(testUser.getPersonID());
            assertEquals(testUser.getUsername(), output.getUsername());
        } 
        catch (DataAccessException e)
        {
        	fail(e.getMessage());
        }

    }

    @Test
    public void testDoesUsernameAndPasswordExist()
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
            myUserDAO.insertUser(testUser);

            boolean output = myUserDAO.doesUsernameAndPasswordExist(testUser);
            assertEquals(true, output);

            try
            {
                User bogus = new User();
                bogus.setUsername("bogus");
                bogus.setPassword("bogus");
                output = myUserDAO.doesUsernameAndPasswordExist(bogus);
                assertEquals(true,false);
            }
            catch(DataAccessException e)
            {
                assertEquals("This username and/or password does not exist.",e.getMessage());
            }

        }
        catch (DataAccessException e)
        {
        	fail(e.getMessage());
        }
    }

    @Test
    public void testGetPersonIDofUser()
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
            myUserDAO.insertUser(testUser);

            String expected = "ID";

            String output = myUserDAO.getUserID(testUser);

            assertEquals(expected,output);
        }
        catch (DataAccessException e)
        {
        	fail(e.getMessage());
        }
    }
}