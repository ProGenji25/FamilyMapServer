package DataAccess;

@SuppressWarnings("serial")
public class DataAccessException extends Exception
{
	private String message;

    public DataAccessException(){
        message = new String();
    }
    public DataAccessException(String message){
        this.message = message;
    }
    public String getMessage(){
        return message;
    }
}
