package test;

import org.junit.*;


import DataAccess.*;
import Model.*;
import Request.LoadRequest;
import Result.LoadResult;
import Service.LoadService;

import static org.junit.Assert.*;


public class testLoadService
{
    private LoadService myLoadService;

    @Before
    public void setUp() 
    {
        myLoadService = new LoadService();
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
        myLoadService = null;
    }

    @Test
    public void testLoad()
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
         testPerson1.setSpouse("Druzinha");
         testPerson1.setMother("Nelly");
         testPerson1.setFather("Jeffrey");

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


         User[] usersInput = new User[2];
         usersInput[0] = testUser;
         usersInput[1] = testUser2;
            
         Person[] personsInput = new Person[2];
         personsInput[0] = testPerson1;
         personsInput[1] = testPerson2;
            
         Event[] eventsInput = new Event[2];
         eventsInput[0] = testEvent1;
         eventsInput[1] = testEvent2;

         LoadRequest inputRequest = new LoadRequest();
         inputRequest.setUsers(usersInput);
         inputRequest.setEvents(eventsInput);
         inputRequest.setPersons(personsInput);

         LoadResult expectedResponse = new LoadResult();
         expectedResponse.setSuccess(true);
         expectedResponse.setNumEvents(2);
         expectedResponse.setNumPersons(2);
         expectedResponse.setNumUsers(2);

         LoadResult outputResponse = myLoadService.load(inputRequest);

         assertEquals(expectedResponse.getSuccess(), outputResponse.getSuccess());
    }
}
