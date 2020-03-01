package DataAccess;

import java.sql.*;
import Model.AuthToken;

/**
 * AuthTokenDao object provides interaction between AuthToken class and 
 * authorize database
 * @author golde3
 *
 */
public class AuthTokenDao 
{
	private Connection conn;
	
	/**
	 * Constructor for AuthToken DAO object
	 */
	public AuthTokenDao() 
	{
	}
	
	/**
	 * Sets up database connection for AuthToken DAO
	 * @param c 	SQL database connection
	 * @throws DataAccessException
	 */
	public void setConnection(Connection c) throws DataAccessException 
	{
		conn = c;
	}
	
	/**
	 * Builds and clears AuthToken table in database
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
                stmt.executeUpdate("drop table if exists auth");
                stmt.executeUpdate("create table auth ("
                		+ "authToken VARCHAR(25) UNIQUE PRIMARY KEY, "
                		+ "username VARCHAR(50) NOT NULL, "
                		+ "personID VARCHAR(25) NOT NULL)");
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
            throw new DataAccessException("Reset of AUTH table failed.");
        }
	}
	
	/**
	 * Inserts authorization token into authorize table
	 * @param token 	Unique auth token of user
	 * @throws DataAccessException
	 */
	public void insertToken(AuthToken token) throws DataAccessException 
	{
		 try 
		 {
			 PreparedStatement stmt = null;
	         try 
	         {
	        	 String sql = "insert into auth (authToken, username, personID) values (?,?,?)";
	             stmt = conn.prepareStatement(sql);
	             stmt.setString(1, token.getAuthToken());
	             stmt.setString(2, token.getUsername());
	             stmt.setString(3, token.getPersonID());
	             stmt.executeUpdate();
	         }
	         finally 
	         {
	        	 if (stmt != null) 
	        	 {
	        		 stmt.close();
	             }
	         }
	     }
	     catch (SQLException e) 
		 {
	    	 throw new DataAccessException("Error occurred during token insert.");
	     }
	}
	
	/**
	 * Finds requested auth token in authorize table
	 * @param auth Authentication token to be retrieved
	 * @return	AuthToken object with token data
	 * @throws DataAccessException
	 */
	public AuthToken selectAuthToken(String auth) throws DataAccessException 
	{
        AuthToken token = new AuthToken();
        try 
        {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try 
            {
                String sql = "select * from auth WHERE authToken = '" + auth +"'";
                stmt = conn.prepareStatement(sql);
                rs = stmt.executeQuery();
                while (rs.next())
                {
                    token.setAuthToken(rs.getString(1));
                    token.setUsername(rs.getString(2)); 
                    token.setPersonID(rs.getString(3)); 
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
            throw new DataAccessException("Error encountered while getting auth token.");
        }
        return token;
	}
	
	/**
	 * Finds if auth token is in the table
	 * @param authToken		Unique authorization token of current user
	 * @return if token found
	 * @throws DataAccessException
	 */
	public boolean doesAuthTokenExist(String authToken) throws DataAccessException
	{
        try
        {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try
            {
                String sql = "select * from auth WHERE authToken = '" + authToken + "'";
                stmt = conn.prepareStatement(sql);
                rs = stmt.executeQuery();
                if (!rs.next())
                {
                    throw new DataAccessException("No such authToken exists.");
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
            throw new DataAccessException("No such authToken exists.");
        }
	}
	
    public String tableToString() throws DataAccessException{
        StringBuilder out = new StringBuilder();
        try {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "select * from auth";
                stmt = conn.prepareStatement(sql);

                rs = stmt.executeQuery();
                while (rs.next()) {
                    String authtoken = rs.getString(1);
                    String username = rs.getString(2);
                    String personID = rs.getString(3);

                    out.append((authtoken + "\t" + username + "\t" + personID + "\n"));
                }
            }
            finally {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
        catch (SQLException e) {
            throw new DataAccessException("seeTable auth failed");
        }
        return out.toString();
    }
}
