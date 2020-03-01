package Handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

import ObjectEncoder.Encoder;
import Service.GetAllEventsService;
import Service.GetEventIdService;
import Result.GetAllEventsResult;
import Result.GetEventIdResult;


public class EventHandler implements HttpHandler
{
    @Override
    public void handle(HttpExchange exchange) throws IOException 
    {
    	GetEventIdResult myGetEventIDResult = new GetEventIdResult();
    	GetAllEventsResult myGetAllEventsResult = new GetAllEventsResult();

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
                    	myGetEventIDResult.setSuccess(false);
                    	myGetEventIDResult.setMessage("Invalid number of arguments");
                    	myGetAllEventsResult.setSuccess(false);
                    	myGetAllEventsResult.setMessage("Bad Request");
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                        String jsonStr = new String("{\"message\" : \"" + myGetEventIDResult.getMessage() + "\"}");
                        OutputStream respBody = exchange.getResponseBody();
                        writeString(jsonStr, respBody);
                        respBody.close();
                    } 
                    else if (arguments.length == 2) //personID
                    {
                    	GetEventIdService myGetEventIdService = new GetEventIdService();
                    	myGetEventIDResult = myGetEventIdService.eventID(arguments[1], authToken);
                        if (myGetEventIDResult.getSuccess())
                        {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                            String jsonStr = Encoder.encode(myGetEventIDResult);
                            OutputStream respBody = exchange.getResponseBody();
                            writeString(jsonStr, respBody);
                            respBody.close();
                        } 
                        else
                        {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                            String jsonStr = new String("{\"message\" : \"" + myGetEventIDResult.getMessage() + "\"}");
                            OutputStream respBody = exchange.getResponseBody();
                            writeString(jsonStr, respBody);
                            respBody.close();
                        }
                    } 
                    else if (arguments.length == 1) //all events
                    {
                    	GetAllEventsService myGetAllEventsService = new GetAllEventsService();
                    	GetAllEventsResult out = myGetAllEventsService.events(authToken);
                    	myGetAllEventsResult.setEvents(out.getEvents());
                    	myGetAllEventsResult.setSuccess(out.getSuccess());

                        if (myGetAllEventsResult.getSuccess())
                        {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                            String jsonStr = Encoder.encode(myGetAllEventsResult);
                            OutputStream respBody = exchange.getResponseBody();
                            writeString(jsonStr, respBody);
                            respBody.close();
                        } 
                        else
                        {
                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                            String jsonStr = new String("{\"message\" : \"" + myGetAllEventsResult.getMessage() + "\"}");
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

    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}