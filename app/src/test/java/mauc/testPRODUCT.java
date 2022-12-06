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
    public void negTermDepositsProductGET() throws Exception {

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
         * Accesses the Tyro BUSSINESS_LOANS product and compares the expected information of eligibility by the actual information
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

        /*
         * Accesses the Tyro BUSINESS_LOANS product and returns a 400 status code
         */
        RestAssured.given()
        .get("https://public.cdr.tyro.com/cds-au/v1/banking/products/b5ee1091-e3af-4517-8f8f-8cc52434472b")
        .then()
        .statusCode(400)
        .extract()
        .path("bad_value");
    }

    @Test
    public void effectiveProductGET() throws Exception {

        /*
         * Product GET request returning a 200 statusCode for effective "FUTURE" products
         */
        RestAssured.given()
        .header("x-v", "3")
        .queryParam("effective", "FUTURE")
        .get("https://public.cdr.tyro.com/cds-au/v1/banking/products")
        .then()
        .statusCode(200);
    }

    @Test
    public void missingProductGET() throws Exception {

        /*
         * Attempts to request a missing product. Expected 200 statusCode
         */
        RestAssured.given()
        .header("x-v", "3")
        .queryParam("product-category", "TRADE_FINANCE")
        .get("https://public.cdr.tyro.com/cds-au/v1/banking/products")
        .then()
        .statusCode(200);
    }

    @Test
    public void unknownBrandProductGET() throws Exception {

        /*
         * Attemps to request an unknown brand. Expected 200 statusCode
         */
        RestAssured.given()
        .header("x-v", "3")
        .queryParam("brand", "Unknown")
        .get("https://public.cdr.tyro.com/cds-au/v1/banking/products")
        .then()
        .statusCode(200);
    }

    @Test
    public void queryOnUnknownBrandProductGET() throws Exception {

        /*
         * Requesting TERM_DEPOSITS on an unknown brand. Expected 200 statusCode
         */
        RestAssured.given()
        .header("x-v", "3")
        .queryParam("brand", "Unknown")
        .queryParam("product-category", "TERM_DEPOSITS")
        .get("https://public.cdr.tyro.com/cds-au/v1/banking/products")
        .then()
        .statusCode(200);
    }

    @Test
    public void badQueryOnUnknownBrandProductGET() throws Exception {

        /*
         * Requesting an unknown product-category on an unknown brand. Expected 400 statusCode
         */
        RestAssured.given()
        .header("x-v", "3")
        .queryParam("brand", "Unknown")
        .queryParam("product-category", "TERM_DEPOSIT")
        .get("https://public.cdr.tyro.com/cds-au/v1/banking/products")
        .then()
        .statusCode(400);
    }

    @Test
    public void responseDataProductGET() throws Exception {

        /*
         * Checks whether the response data is structured correctly (JSON)
         */
        String expected = "application/json";

        String actual = RestAssured.given()
        .header("x-v", "3")
        .get("https://public.cdr.tyro.com/cds-au/v1/banking/products")
        .then()
        .extract()
        .contentType();
        
        Assert.assertEquals(actual, expected, "Data mismatch");
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
