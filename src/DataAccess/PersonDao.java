package DataAccess;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;


import Model.Person;
import Model.User;
import ObjectEncoder.*;

/**
 * PersonDao object provides interaction between Person class and 
 * person database
 * @author golde3
 *
 */
public class PersonDao
{
	private Connection conn;
	private StringArray femaleNames;
	private StringArray maleNames;
	private StringArray lastNames;
	
	/**
	 * Constructor for Person DAO object
	 */
	public PersonDao()
	{
		femaleNames = Decoder.decodeNames("data/fnames.json");
        maleNames = Decoder.decodeNames("data/mnames.json");
        lastNames = Decoder.decodeNames("data/snames.json");
	}
	
	/**
	 * Sets up database connection for Person DAO
	 * @param c SQL database connection
	 * @throws DataAccessException
	 */
	public void setConnection(Connection c) throws DataAccessException
	{
		conn = c;
	}
	
	/**
	 * Builds and clears person table in database
	 * @throws DataAccessException
	 */
	public void clear() throws DataAccessException
	{
		try
		{
            Statement stmt = null;
            try
            {
                stmt = conn.createStatement();
                stmt.executeUpdate("drop table if exists persons");
                stmt.executeUpdate("create table persons "
                		+ "(personID VARCHAR(25) UNIQUE PRIMARY KEY, "
                		+ "descendant VARCHAR(50) NOT NULL, "
                		+ "firstName VARCHAR(50) NOT NULL, "
                		+ "lastName VARCHAR(50) NOT NULL, "
                		+ "gender CHAR(1) NOT NULL, "
                		+ "father VARCHAR(50), "
                		+ "mother VARCHAR(50), "
                		+ "spouse VARCHAR(50), "
                		+ "foreign key (descendant) references users(username))");
            }
            finally
            {
                if (stmt != null)
                {
                    stmt.close();
                    stmt = null;
                }
            }
        }
        catch (SQLException e)
		{
            throw new DataAccessException("reset Person table failed");
        }
	}
	
	/**
	 * Inserts new person into person table
	 * @param person 	Person object to be inserted
	 * @throws DataAccessException
	 */
	public void insertPerson(Person person) throws DataAccessException
	{
		try
		{
			PreparedStatement stmt = null;
			try
			{
				String sql = "insert into persons (personID, descendant, firstName, lastName, gender, father, mother, spouse) values (?,?,?,?,?,?,?,?)";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, person.getPersonID());
				stmt.setString(2, person.getDescendant());
				stmt.setString(3, person.getFirstName());
				stmt.setString(4, person.getLastName());
				
				if(person.getGender().length() != 1 || (!person.getGender().equals("m") && !person.getGender().equals("f")))
				{	
					throw new DataAccessException("Incorrect gender format for person.");
				}
				stmt.setString(5, person.getGender());
				stmt.setString(6, person.getFather());
				stmt.setString(7, person.getMother());
				stmt.setString(8, person.getSpouse());
				stmt.executeUpdate();
			}
			finally
			{
				if(stmt != null)
				{
					stmt.close();
				}
			}
		}
		catch(SQLException e)
		{
			throw new DataAccessException("Error encountered during person insert.");
		}
	}
	
	/**
	 * Function for updating mother of queried Person object
	 * @param person	Person object to be updated
	 * @param mother	Name of new mother
	 * @throws DataAccessException
	 */
	public void updateMother(Person person, String mother) throws DataAccessException
	{
		try
		{
			Statement stmt = null;
			try
			{
				String sql = "UPDATE persons SET mother = '" + mother + "' WHERE personID = '" + person.getPersonID() + "'";
				stmt = conn.createStatement();
				stmt.executeUpdate(sql);
			}
			finally
			{
				if(stmt != null)
				{
					stmt.close();
				}
			}
		}
		catch(SQLException e)
		{
			throw new DataAccessException("Failed to update mother.");
		}
	}
	
	/**
	 * Function for updating father of queried Person object
	 * @param person	Person object to be updated
	 * @param father	Name of new father
	 * @throws DataAccessException
	 */
	public void updateFather(Person person, String father) throws DataAccessException
	{
		try
		{
			Statement stmt = null;
			try
			{
				String sql = "UPDATE persons SET father = '" + father + "' WHERE personID = '" + person.getPersonID() + "'";
				stmt = conn.createStatement();
				stmt.executeUpdate(sql);
			}
			finally
			{
				if(stmt != null)
				{
					stmt.close();
				}
			}
		}
		catch(SQLException e)
		{
			throw new DataAccessException("Failed to update father.");
		}
	}
	
	/**
	 * Function for updating spouse of queried Person object
	 * @param person	Person object to be updated
	 * @param spouse	Name of new spouse
	 * @throws DataAccessException
	 */
	public void updateSpouse(Person person, String spouse) throws DataAccessException
	{
		try
		{
			Statement stmt = null;
			try
			{
				String sql = "UPDATE persons SET spouse = '" + spouse + "' WHERE personID = '" + person.getPersonID() + "'";
				stmt = conn.createStatement();
				stmt.executeUpdate(sql);
			}
			finally
			{
				if(stmt != null)
				{
					stmt.close();
				}
			}
		}
		catch(SQLException e)
		{
			throw new DataAccessException("Failed to update spouse.");
		}
	}
	
	/**
	 * Generates the person's immediate family tree.
	 * @param person	Person to generate family tree for
	 * @param numGenerations	Number of generations tree extends to.
	 * @param myEventDAO	Event DAO object passed in for generating events for people in the tree
	 * @param birthYear		Person's birth year
	 * @throws DataAccessException
	 */
	public void generateGenerations(Person orphan, int numGenerations, EventDao myEventDAO, int birthYear) throws DataAccessException
	{
		Person mother = makeMother(orphan);
		Person father = makeFather(orphan);
		updateSpouse(father, mother.getPersonID());
		updateSpouse(mother, father.getPersonID());
		
		int birthdayForParents = myEventDAO.generateParentEventData(mother, father, birthYear);
		numGenerations--;
		if(numGenerations > 0)
		{
			generateGenerations(mother, numGenerations, myEventDAO, birthdayForParents);
			generateGenerations(father, numGenerations, myEventDAO, birthdayForParents);
		}
	}
	
	/**
	 * Function for initializing mother for person
	 * @param person	Initial child of mother
	 * @return	new Person object for mother
	 * @throws DataAccessException
	 */
	public Person makeMother(Person orphan) throws DataAccessException
	{
        Random rand = new Random();
        int r = rand.nextInt(femaleNames.data.length);
        String motherID = UUID.randomUUID().toString();
        String descedantOfMother = orphan.getDescendant();
        
        String motherFirstName = femaleNames.getValueAt(r);
        r = rand.nextInt(lastNames.data.length);
        String motherLastName = lastNames.getValueAt(r);
        String gender = "f";

        //Updates orphan's mother
        updateMother(orphan, motherID);

        //Make mother model
        Person mother = new Person();
        mother.setPersonID(motherID);
        mother.setDescendant(descedantOfMother);
        mother.setFirstName(motherFirstName);
        mother.setLastName(motherLastName);
        mother.setGender(gender);

        //insert mother into table
        insertPerson(mother);
		return mother;
	}
	
	/**
	 * Function for initializing father for person
	 * @param person	Initial child of father
	 * @return	new Person object for father
	 * @throws DataAccessException
	 */
	public Person makeFather(Person orphan) throws DataAccessException
	{
        Random rand = new Random();
        int r = rand.nextInt(maleNames.data.length);

        String fatherID = UUID.randomUUID().toString();
        String descedantOfFather = orphan.getDescendant();
        String fatherFirstName = maleNames.getValueAt(r);
        String fatherLastName = orphan.getLastName();
        String gender = "m";

        //Updates orphan's father
        updateFather(orphan, fatherID);

        //Make father model
        Person father = new Person();
        father.setPersonID(fatherID);
        father.setDescendant(descedantOfFather);
        father.setFirstName(fatherFirstName);
        father.setLastName(fatherLastName);
        father.setGender(gender);

        //insert father model into table
        insertPerson(father);
		return father;
	}
	
	
	/**
	 * Selects single person from person table
	 * @param personID 	Unique ID associated w/ person
	 * @return Person object associated w/ personID in table
	 * @throws DataAccessException
	 */
	public Person selectPerson(String personID) throws DataAccessException
	{
		Person person = new Person();
		try
		{
			PreparedStatement stmt = null;
			ResultSet rs = null;
			try
			{
				String sql = "select * from persons WHERE personID = '" + personID +"'";
				stmt = conn.prepareStatement(sql);
				rs = stmt.executeQuery();
				while(rs.next())
				{
					person.setPersonID(rs.getString(1));
					person.setDescendant(rs.getString(2));
					person.setFirstName(rs.getString(3));
					person.setLastName(rs.getString(4));
					person.setGender(rs.getString(5));
					person.setFather(rs.getString(6));
					person.setMother(rs.getString(7));
					person.setSpouse(rs.getString(8));
				}
			}
			finally
			{
				if(rs != null)
				{
					rs.close();
				}
				if(stmt != null)
				{
					stmt.close();
				}
			}
		}
		catch(SQLException e)
		{
			throw new DataAccessException("Error encountered while finding person.");
		}
		return person;
	}
	
	/**
	 * Selects all persons associated with user in persons table
	 * @param username	User name for user that associated persons are being retrieved for
	 * @return	All associated Person objects of user in an array
	 * @throws DataAccessException
	 */
	public ArrayList<Person> selectAllPersons(String username) throws DataAccessException
	{
        ArrayList<Person> personArray = new ArrayList<Person>();
        try
        {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try
            {
                String sql = "select * from persons WHERE descendant = '" + username + "'";
                stmt = conn.prepareStatement(sql);
                rs = stmt.executeQuery();
                while (rs.next())
                {
                    Person person = new Person();
                    person.setPersonID(rs.getString(1));
                    person.setDescendant(rs.getString(2));
                    person.setFirstName(rs.getString(3));
                    person.setLastName(rs.getString(4));
                    person.setGender(rs.getString(5));
                    person.setFather(rs.getString(6));
                    person.setMother(rs.getString(7));
                    person.setSpouse(rs.getString(8));
                    personArray.add(person);
                    person = null;
                }
            }
            finally
            {
                if (rs != null)
                {
                    rs.close();
                }
                if (stmt != null)
                {
                    stmt.close();
                }
            }
        }
        catch (SQLException e)
        {
            throw new DataAccessException("selectAllPersons failed");
        }
        return personArray;
	}
	
	/**
	 * Deletes all Person objects associated with user
	 * @param user	User object for which all Person object relations are being removed
	 * @throws DataAccessException
	 */
	public void deleteAllPeopleOfUser(User user) throws DataAccessException
	{
		try
		{
			Statement stmt = null;
			try
			{
				stmt = conn.createStatement();
				String sql = "DELETE FROM persons WHERE descendant = '" + user.getUsername() + "'";
				stmt.executeUpdate(sql);
			}
			finally
			{
				if(stmt != null)
				{
					stmt.close();
					stmt = null;
				}
			}
		}
		catch(SQLException e)
		{
			throw new DataAccessException("Delete failed.");
		}
	}
	
	/**
	 * Find if requested person exists in table
	 * @param personID 	Unique ID of person
	 * @return if person is in table
	 * @throws DataAccessException
	 */
	public boolean doesPersonExist(String personID) throws DataAccessException
	{
        try
        {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try
            {
                String sql = "select * from persons WHERE personID = '" + personID + "'";
                stmt = conn.prepareStatement(sql);
                rs = stmt.executeQuery();
                if (!rs.next())
                {
                    throw new DataAccessException("No such personID");
                } 
                else
                {
                    return true;
                }
            }
            finally
            {
                if (rs != null)
                {
                    rs.close();
                }
                if (stmt != null)
                {
                    stmt.close();
                }
            }
        }
        catch (SQLException e)
        {
            throw new DataAccessException("No such personID");
        }
	}
	
    public String tableToString() throws DataAccessException
    {
        StringBuilder out = new StringBuilder();
        try
        {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try 
            {
                String sql = "select * from persons";
                stmt = conn.prepareStatement(sql);
                rs = stmt.executeQuery();
                while (rs.next())
                {
                    String personID = rs.getString(1);
                    String descendant = rs.getString(2);
                    String firstName = rs.getString(3);
                    String lastName = rs.getString(4);
                    String gender = rs.getString(5);
                    String father = rs.getString(6);
                    String mother = rs.getString(7);
                    String spouse = rs.getString(8);
                    out.append((personID + "\t" + descendant + "\t" + firstName + "\t" + lastName + "\t" + gender + "\t" + father + "\t" + mother + "\t" + spouse + "\n"));
                }
            }
            finally
            {
                if (rs != null)
                {
                    rs.close();
                }
                if (stmt != null)
                {
                    stmt.close();
                }
            }
        }
        catch (SQLException e)
        {
            throw new DataAccessException("seePeopleTable failed");
        }
        return out.toString();
    }
}
