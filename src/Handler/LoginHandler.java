package Handler;

import ObjectEncoder.Decoder;
import ObjectEncoder.Encoder;
import Service.LoginService;
import Result.LoginResult;
import Request.LoginRequest;
import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;


public class LoginHandler implements HttpHandler 
{
    @Override
    public void handle(HttpExchange exchange) throws IOException 
    {
    	LoginResult myLoginResult = new LoginResult();
        try 
        {
            if (exchange.getRequestMethod().toLowerCase().equals("post"))
            {
                LoginService myLoginService = new LoginService();
                Reader reader = new InputStreamReader(exchange.getRequestBody());
                LoginRequest myLoginRequest = Decoder.decodeLoginRequest(reader);
                myLoginResult = myLoginService.login(myLoginRequest);
                if (myLoginResult.getSuccess())
                {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    String jsonStr = Encoder.encode(myLoginResult);
                    OutputStream respBody = exchange.getResponseBody();
                    writeString(jsonStr, respBody);
                    respBody.close();
                } 
                else
                {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    String jsonStr = new String("{\"message\" : \"" + myLoginResult.getMessage() + "\"}");
                    OutputStream respBody = exchange.getResponseBody();
                    writeString(jsonStr, respBody);
                    respBody.close();
                }
            }
        }
        catch (IOException e)
        {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            String jsonStr = new String("{\"message\": \"Internal server error\"}");
            OutputStream respBody = exchange.getResponseBody();
            writeString(jsonStr, respBody);
            respBody.close();
            e.printStackTrace();
        }
    }

    private void writeString(String str, OutputStream os) throws IOException 
    {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}