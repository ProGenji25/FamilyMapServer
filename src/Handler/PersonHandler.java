package Handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

import ObjectEncoder.Encoder;
import Service.GetPersonIdService;
import Result.GetPersonIdResult;
import Service.GetAllPersonsService;
import Result.GetAllPersonsResult;



public class PersonHandler implements HttpHandler 
{
    @Override
    public void handle(HttpExchange exchange) throws IOException
    {
    	GetPersonIdResult myGetPersonIdResult = new GetPersonIdResult();
        GetAllPersonsResult myGetAllPersonsResult = new GetAllPersonsResult();
        try
        {
            if (exchange.getRequestMethod().toLowerCase().equals("get"))
            {
                if(exchange.getRequestHeaders().containsKey("Authorization"))
                {
                    String authToken = exchange.getRequestHeaders().getFirst("Authorization");
                    String requestedURL = exchange.getRequestURI().toString();
                    StringBuilder url = new StringBuilder(requestedURL);
                    url.deleteCharAt(0);
                    String[] arguments = url.toString().split("/");
                    if (arguments.length > 2 || arguments.length < 1)
                    {
                    	myGetPersonIdResult.setSuccess(false);
                    	myGetPersonIdResult.setMessage("Invalid number of arguments");
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        String jsonStr = new String("{\"message\" : \"" + myGetPersonIdResult.getMessage() + "\"}");
                        OutputStream respBody = exchange.getResponseBody();
                        writeString(jsonStr, respBody);
                        respBody.close();
                    } 
                    else if (arguments.length == 2)  //personID
                    {
                    	GetPersonIdService myGetPersonIdService = new GetPersonIdService();
                    	myGetPersonIdResult = myGetPersonIdService.personID(arguments[1], authToken);
                        if (myGetPersonIdResult.getSuccess())
                        {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                            String jsonStr = Encoder.encode(myGetPersonIdResult);
                            OutputStream respBody = exchange.getResponseBody();
                            writeString(jsonStr, respBody);
                            respBody.close();
                        } 
                        else 
                        {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                            String jsonStr = new String("{\"message\" : \"" + myGetPersonIdResult.getMessage() + "\"}");
                            OutputStream respBody = exchange.getResponseBody();
                            writeString(jsonStr, respBody);
                            respBody.close();
                        }
                    } 
                    else if (arguments.length == 1)
                    {
                    	GetAllPersonsService myGetAllPersonsService = new GetAllPersonsService();
                    	myGetAllPersonsResult = myGetAllPersonsService.getPeople(authToken);
                        if (myGetAllPersonsResult.getSuccess())
                        {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                            String jsonStr = Encoder.encode(myGetAllPersonsResult);
                            OutputStream respBody = exchange.getResponseBody();
                            writeString(jsonStr, respBody);
                            respBody.close();
                        } 
                        else
                        {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                            String jsonStr = new String("{\"message\" : \"" + myGetAllPersonsResult.getMessage() + "\"}");
                            OutputStream respBody = exchange.getResponseBody();
                            writeString(jsonStr, respBody);
                            respBody.close();
                        }
                    }
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