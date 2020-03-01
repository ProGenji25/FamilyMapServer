package ObjectEncoder;

import Request.LoginRequest;
import Request.RegisterRequest;
import Result.ClearResult;
import Result.GetAllEventsResult;
import Result.GetAllPersonsResult;
import Result.GetEventIdResult;
import Result.GetPersonIdResult;
import Result.LoginResult;
import Result.RegisterResult;

import com.google.gson.Gson;

public class Encoder
{
    public Encoder()
    {
    }

    public static String encode(LoginResult response)
    {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(response);
        return jsonStr;
    }

    public static String encode(RegisterResult response) 
    {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(response);
        return jsonStr;
    }

    public static String encode(ClearResult response)
    {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(response);
        return jsonStr;
    }

    public static String encode(GetPersonIdResult response)
    {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(response);
        return jsonStr;
    }

    public static String encode(GetAllPersonsResult response)
    {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(response);
        return jsonStr;
    }

    public static String encode(GetEventIdResult response)
    {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(response);
        return jsonStr;
    }

    public static String encode(GetAllEventsResult response)
    {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(response);
        return jsonStr;
    }

    public static String encode(LoginRequest request)
    {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(request);
        return jsonStr;
    }

    public static String encode (RegisterRequest request)
    {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(request);
        return jsonStr;
    }
}