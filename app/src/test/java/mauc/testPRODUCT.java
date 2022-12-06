package mauc;

import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class testPRODUCT {
    
    @Test
    public void productGET() throws Exception {

        /*
         * Basic GET product request returns 200 statusCode
         */
        RestAssured.given()
        .header("x-v", "3")
        .get("https://public.cdr.tyro.com/cds-au/v1/banking/products")
        .then()
        .statusCode(200);
    }

    @Test
    public void detailedProductGET() throws Exception {

        /*
         * Basic GET detailed product request returns 200 statusCode
         */
        RestAssured.given()
        .header("x-v","3")
        .get("https://public.cdr.tyro.com/cds-au/v1/banking/products/b5ee1091-e3af-4517-8f8f-8cc52434472b")
        .then()
        .statusCode(200);
    }

    @Test
    public void incorrectHeader() throws Exception {

        /*
         * Missing header values returns 400 statusCode
         */
        RestAssured.given()
        .get("https://public.cdr.tyro.com/cds-au/v1/banking/products")
        .then()
        .statusCode(400);
    }

    @Test
    public void incorrectURL() throws Exception {

        /*
         * Incorrect URL request returns 404 statusCode
         */
        RestAssured.given()
        .header("x-v", "3")
        .get("https://public.cdr.tyro.com/cds-au/v1/bad_value")
        .then()
        .statusCode(404);
    }

    @Test
    public void termDepositsGET() throws Exception {

        /*
         * Extracts TERM_DEPOSITS option and checks the status code
         */
        RestAssured.given()
        .header("x-v", "3")
        .queryParam("product-category", "TERM_DEPOSITS")
        .get("https://public.cdr.tyro.com/cds-au/v1/banking/products")
        .then()
        .statusCode(200);
    }

    @Test
    public void negTermDepositsGET() throws Exception {

        /*
         * Incorrect value for accessing the TERM_DEPOSITS category returns a 400 statusCode
         */
        RestAssured.given()
        .header("x-v", "3")
        .queryParam("product-category", "TERM_DEPOSIT")
        .get("https://public.cdr.tyro.com/cds-au/v1/banking/products")
        .then()
        .statusCode(400);
    }

    @Test
    public void detailedEligibilityProductGET() throws Exception{

        /*
         * Accesses the Tyro BUSSINESS_LOANS product and compares the expected information by the actual information
         */
        String expected = "[{eligibilityType=BUSINESS, additionalInfo=You must have an active ABN., additionalInfoUri=null, additionalValue=null}, {eligibilityType=OTHER, additionalInfo=You must be a Tyro EFTPOS/eCommerce customer., additionalInfoUri=null, additionalValue=}]";
        ArrayList<String> actual = null;
        
        actual = RestAssured.given()
        .header("x-v", "3")
        .get("https://public.cdr.tyro.com/cds-au/v1/banking/products/b5ee1091-e3af-4517-8f8f-8cc52434472b")
        .then()
        .statusCode(200)
        .extract()
        .path("data.eligibility");

        if (actual != null) {
            Assert.assertEquals(actual.toString(), expected, "Data mismatch");
        }
    }

    @Test
    public void negDetailedProductGET() throws Exception {

        //Refine thiss
        RestAssured.given()
        .get("https://public.cdr.tyro.com/cds-au/v1/banking/products/b5ee1091-e3af-4517-8f8f-8cc52434472b")
        .then()
        .statusCode(400);
    }

    @Test
    public void effectiveProductGET() throws Exception {
        
    }


    @Test
    public void responseTimeProductGET() throws Exception {

        /*
         * Checks the response time for a basic GET request
         */
        long actual_time = RestAssured.given()
        .header("x-v", 3)
        .get("https://public.cdr.tyro.com/cds-au/v1/banking/products")
        .timeIn(TimeUnit.MILLISECONDS);

        if (actual_time > 10000) {
            throw new Exception ("Response took too long");
        }

    }
}
