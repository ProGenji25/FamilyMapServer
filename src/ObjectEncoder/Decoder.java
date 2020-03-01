package ObjectEncoder;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

import Request.*;
import Result.*;

public class Decoder 
{
    public Decoder()
    {
    }

    public static LoginRequest decodeLoginRequest(Reader json)
    {
        LoginRequest out;
        Gson gson = new Gson();
        out = gson.fromJson(json, LoginRequest.class);
        return out;
    }

    public static LoadRequest decodeLoadRequest(Reader json)
    {
        LoadRequest out;
        Gson gson = new Gson();
        out = gson.fromJson(json, LoadRequest.class);
        return out;
    }

    public static LoginResult decodeLoginResponse(Reader json)
    {
        LoginResult out;
        Gson gson = new Gson();
        out = gson.fromJson(json, LoginResult.class);
        return out;
    }

    public static RegisterResult decodeRegisterResponse(Reader json)
    {
        RegisterResult out;
        Gson gson = new Gson();
        out = gson.fromJson(json, RegisterResult.class);
        return out;
    }

    public static GetAllPersonsResult decodePersonGetAllResponse(Reader json)
    {
    	GetAllPersonsResult out;
        Gson gson = new Gson();
        out = gson.fromJson(json, GetAllPersonsResult.class);
        return out;
    }

    public static GetAllEventsResult decodeEventGetAllResponse(Reader json)
    {
    	GetAllEventsResult out;
        Gson gson = new Gson();
        out = gson.fromJson(json, GetAllEventsResult.class);
        return out;
    }

    public static GetPersonIdResult decodePersonIDResponse(Reader json)
    {
    	GetPersonIdResult out;
        Gson gson = new Gson();
        out = gson.fromJson(json, GetPersonIdResult.class);
        return out;
    }


    public static RegisterRequest decodeRegisterRequest(Reader json) 
    {
        RegisterRequest out;
        Gson gson = new Gson();
        out = gson.fromJson(json, RegisterRequest.class);
        return out;
    }

    public static StringArray decodeNames(String file)
    {
        Gson gson = new Gson();
        try
        {
            StringArray temp = gson.fromJson(new FileReader(file), StringArray.class);
            return temp;
        } 
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static LocationArray decodeLocations(String file)
    {
        Gson gson = new Gson();
        try
        {
            LocationArray temp = gson.fromJson(new FileReader(file), LocationArray.class);
            return temp;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}