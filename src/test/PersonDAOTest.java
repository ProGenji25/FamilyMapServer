package test;

import org.junit.* ;

import java.util.ArrayList;

import static org.junit.Assert.* ;
import Model.Person;
import Model.User;
import DataAccess.*;


public class PersonDAOTest {

        private PersonDao myPersonDAO;
        private Database db;

        @Before
        public void setUp() throws DataAccessException {
            db = new Database();
            db.openConnection();
            db.clearTables();
            myPersonDAO = db.getMyPersonDAO();
        }

        @After
        public void tearDown() throws DataAccessException {
            db.closeConnection(false);
            myPersonDAO = null;
            db = null;
        }

        @Test
        public void testinsertPerson() 
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

                String out = new String("ID" + "\t" + "golde3" +  "\t" + "jordan" + "\t" + "golden" + "\t" +  "m" + "\t" + "\t" + "\t" +"\n");

                Person p = new Person(testUser);
                myPersonDAO.insertPerson(p);

                assertEquals(myPersonDAO.tableToString(), out);
            } 
            catch (DataAccessException e)
            {
            	fail(e.getMessage());
            }
        }

        @Test
        public void testResetPersonTable()
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

                Person testPerson1 = new Person(testUser);
                myPersonDAO.insertPerson(testPerson1); //inserts person into person table

                String answer = new String("ID" + "\t" + "golde3" +  "\t" + "jordan" + "\t" + "golden" + "\t" +  "m" + "\t" + "\t" + "\t" +"\n");
                assertEquals(answer, myPersonDAO.tableToString()); //asserts the table isn't empty

                myPersonDAO.clear(); //resets table

                assertEquals("",myPersonDAO.tableToString());
            }
            catch (DataAccessException e)
            {
            	fail(e.getMessage());
            }

        }

    @Test
    public void testUpdateMother()
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

            Person testPerson1 = new Person(testUser);
            myPersonDAO.insertPerson(testPerson1);

            String answer = new String("ID" + "\t" + "golde3" +  "\t" + "jordan" + "\t" + "golden" + "\t" +  "m" + "\t" + "\t" + "Janet" + "\t" +"\n");

            assertNotEquals(answer, myPersonDAO.tableToString()); //asserts the person has no mother

            myPersonDAO.updateMother(testPerson1, "Janet");

            assertEquals(answer, myPersonDAO.tableToString()); //assert the person now has a mother
        }
        catch (DataAccessException e)
        {
        	fail(e.getMessage());
        }
    }

    @Test
    public void testUpdateFather()
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

            Person testPerson1 = new Person(testUser);
            myPersonDAO.insertPerson(testPerson1);

            String answer = new String("ID" + "\t" + "golde3" +  "\t" + "jordan" + "\t" + "golden" + "\t" +  "m" + "\t" + "John" + "\t" + "\t" +"\n");

            assertNotEquals(answer, myPersonDAO.tableToString()); //asserts the person has no father

            myPersonDAO.updateFather(testPerson1, "John");

            assertEquals(answer, myPersonDAO.tableToString()); //assert the person now has a father           
        }
        catch (DataAccessException e)
        {
        	fail(e.getMessage());
        }
    }

    @Test
    public void testUpdateSpouse()
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

            Person testPerson1 = new Person(testUser);
            myPersonDAO.insertPerson(testPerson1);

            String answer = new String("ID" + "\t" + "golde3" +  "\t" + "jordan" + "\t" + "golden" + "\t" +  "m" + "\t" + "\t" + "\t" + "Tali" + "\n");

            assertNotEquals(answer, myPersonDAO.tableToString()); //asserts the person has no spouse

            myPersonDAO.updateSpouse(testPerson1, "Tali");

            assertEquals(answer, myPersonDAO.tableToString()); //assert the person now has a spouse
        } 
        catch (DataAccessException e)
        {
        	fail(e.getMessage());
        }
    }

    @Test
    public void testMakeMother()
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

            Person testPerson1 = new Person(testUser);
            myPersonDAO.insertPerson(testPerson1);

            String answer = new String("ID" + "\t" + "golde3" +  "\t" + "jordan" + "\t" + "golden" + "\t" +  "m" + "\t" + "\t" + "\t" +"\n");

            assertEquals(answer, myPersonDAO.tableToString()); //shows that table has no mother at all

            Person mother = myPersonDAO.makeMother(testPerson1);

            boolean didItWork = myPersonDAO.doesPersonExist(mother.getPersonID());
            assertTrue(didItWork); //mother now in database
        } 
        catch (DataAccessException e)
        {
        	fail(e.getMessage());
        }
    }

    @Test
    public void testMakeFather()
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

            Person testPerson1 = new Person(testUser);
            myPersonDAO.insertPerson(testPerson1);

            String answer = new String("ID" + "\t" + "golde3" +  "\t" + "jordan" + "\t" + "golden" + "\t" +  "m" + "\t" + "\t" + "\t" +"\n");
            
            assertEquals(answer, myPersonDAO.tableToString()); //shows that table has no father at all
            
            Person father = myPersonDAO.makeFather(testPerson1);

            boolean didItWork = myPersonDAO.doesPersonExist(father.getPersonID());
            assertTrue(didItWork); //mother now in database
        }
        catch (DataAccessException e)
        {
        	fail(e.getMessage());
        }
    }

    @Test
    public void testGenerateGenerations()
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

            Person testPerson1 = new Person(testUser);
            myPersonDAO.insertPerson(testPerson1);

            String answer = new String("ID" + "\t" + "golde3" +  "\t" + "jordan" + "\t" + "golden" + "\t" +  "m" + "\t" + "\t" + "\t" +"\n");
            
            assertEquals(answer, myPersonDAO.tableToString()); //shows that table is just the one person
            EventDao myEventDAO = db.getMyEventDAO();

            //test person fill logic
            double numG = 4;	
            double numPeople = (Math.pow(2.0, (numG + 1.0)) - 1.0);
            int finalNumPeople = (int) numPeople; //final
            int finalNumEvents = (finalNumPeople * 3) - 3;

            myPersonDAO.generateGenerations(testPerson1, 4, myEventDAO, 2017);


            String output = myPersonDAO.tableToString();
            String[] people = output.split("\n");

            assertEquals(finalNumPeople, people.length);

            output = myEventDAO.tableToString();
            String[] events = output.split("\n");

            assertEquals(finalNumEvents, events.length);
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

            Person testPerson1 = new Person(testUser);
            testPerson1.setSpouse("Tali");
            testPerson1.setMother("Janet");
            testPerson1.setFather("John");

            String answer = new String("ID" + "\t" + "golde3" +  "\t" + "jordan" + "\t" + "golden" + "\t" +  "m" + "\t" + "John" + "\t" + "Janet" +  "\t" + "Tali" + "\n");

            assertNotEquals(answer,myPersonDAO.tableToString());

            myPersonDAO.insertPerson(testPerson1);

            assertEquals(answer, myPersonDAO.tableToString()); //shows that table is all the fields of a person
        } 
        catch (DataAccessException e)
        {
        	fail(e.getMessage());
        }

    }

    @Test
    public void testSelectAllPersons()
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

            myPersonDAO.insertPerson(testPerson);
            myPersonDAO.insertPerson(testPerson2);
            myPersonDAO.insertPerson(testPerson3);

            ArrayList<Person> expected = new ArrayList<Person>();
            expected.add(testPerson);
            expected.add(testPerson3);

            ArrayList<Person> output = myPersonDAO.selectAllPersons(testUser.getUsername()); //should only include person 1 and 3

            for (int i = 0; i < output.size(); i++)
            {
                assertEquals(expected.get(i).getFirstName(), output.get(i).getFirstName());
            }

        } 
        catch (DataAccessException e)
        {
        	fail(e.getMessage());
        }
    }

    @Test
    public void testDeleteAllPeopleOfUser()
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

            Person testPerson = new Person(testUser);
            testPerson.setSpouse("Tali");
            testPerson.setMother("Janet");
            testPerson.setFather("John");

            Person testPerson2 = new Person(); //person 2
            testPerson2.setDescendant("golde3");
            testPerson2.setPersonID("p3id");
            testPerson2.setFather("Admiral Han'Gerrel");
            testPerson2.setSpouse("jordan");
            testPerson2.setMother("Admiral Raan");
            testPerson2.setFirstName("Tali'Zorah");
            testPerson2.setLastName("nar'Rayaa");
            testPerson2.setGender("f");

            myPersonDAO.insertPerson(testPerson);
            myPersonDAO.insertPerson(testPerson2);

            String answer = "";

            assertNotEquals(answer, myPersonDAO.tableToString());

            myPersonDAO.deleteAllPeopleOfUser(testUser);

            assertEquals(answer, myPersonDAO.tableToString());

        } 
        catch (DataAccessException e)
        {
        	fail(e.getMessage());
        }
    }

    @Test
    public void testSelectSinglePerson()
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

            Person testPerson = new Person(testUser);
            testPerson.setSpouse("Tali");
            testPerson.setMother("Janet");
            testPerson.setFather("John");

            myPersonDAO.insertPerson(testPerson);

            Person output = myPersonDAO.selectPerson(testPerson.getPersonID());

            assertEquals(testPerson.getPersonID(), output.getPersonID());

        } catch (DataAccessException e)
        {
        	fail(e.getMessage());
        }
    }

    @Test
    public void testDoesPersonExist()
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

            Person testPerson = new Person(testUser);
            testPerson.setSpouse("Tali");
            testPerson.setMother("Janet");
            testPerson.setFather("John");
            
            myPersonDAO.insertPerson(testPerson);

            try
            {
                boolean output = myPersonDAO.doesPersonExist(testPerson.getPersonID());
                assertEquals(true,output);
                output = myPersonDAO.doesPersonExist("not a valid personID");
                assertEquals(true,false);
            } catch (DataAccessException e)
            {
                String answer = "No such personID";
                assertEquals(answer, e.getMessage());
            }

        } catch (DataAccessException e){
        	fail(e.getMessage());
        }
    }

}
