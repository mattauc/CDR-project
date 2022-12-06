package mauc;

import java.net.URLEncoder;

import javax.management.RuntimeErrorException;

import org.json.JSONArray;
import org.json.simple.parser.JSONParser;

import com.mashape.unirest.*;
import com.mashape.unirest.http.HttpResponse;

import java.net.*;
import java.util.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.BodyHandlers;

//import io.restassured.RestAssured.*;
//import io.restassured.matcher.RestAssuredMatchers.*;
//import org.hamcrest.Matchers.*;

/**
 * Hello world!
 *
 */
public class App 
{

    final static String url = "https://public.cdr.tyro.com/cds-au/v1";

    public static void main( String[] args ) throws Exception
    {
        //System.out.println( "Hello World!" );

        //URL url = new URL("https://public.cdr.tyro.com/cds-au/v1/banking/products");
        //URL url = new URL("https://api.cdr.tyro.com/cds-au/v1/banking/products");

        //HttpClient
        /*HttpRequest getRequest = HttpRequest.newBuilder()
            .uri(new URI("https://public.cdr.tyro.com/cds-au/v1/banking/products"))
            .header("x-v", "3")
            .GET().build();

        HttpClient client = HttpClient.newHttpClient();
        java.net.http.HttpResponse<String> response = client.send(getRequest, BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("HttpResponseCode: " + response.statusCode());
        }
        //System.out.println("\n" + response.body());

        JSONParser parser = new JSONParser();
        JSONArray dataObject = new JSONArray();
        dataObject.put(parser.parse(String.valueOf(response.body())));
        //(JSONArray) parser.parse(String.valueOf(response.body()));
    
        System.out.print(dataObject.get(1));*/

        //given("x-z",3).when().get("https://public.cdr.tyro.com/cds-au/v1/banking/products").then().log().all();
    }
}
