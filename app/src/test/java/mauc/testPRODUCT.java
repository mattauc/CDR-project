package mauc;

import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class testPRODUCT {
    
    @org.testng.annotations.Test
    public void termDepositsGET() throws Exception {

        RestAssured.given()
        .header("x-v", "3")
        .queryParam("product-category", "TERM_DEPOSITS")
        .get("https://public.cdr.tyro.com/cds-au/v1/banking/products")
        .then()
        .statusCode(200);
    }

    @Test
    public void negTermDepositsGET() throws Exception {

        RestAssured.given()
        .header("x-v", "3")
        .queryParam("product-category", "TERM_DEPOSIT")
        .get("https://public.cdr.tyro.com/cds-au/v1/banking/products")
        .then()
        .statusCode(400);
    }

    @Test
    public void detailedProductGET() throws Exception{

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

        RestAssured.given()
        .get("https://public.cdr.tyro.com/cds-au/v1/banking/products/b5ee1091-e3af-4517-8f8f-8cc52434472b")
        .then()
        .statusCode(400);
    }

    @Test
    public void responseTimeProductGET() throws Exception {

        long actual_time = RestAssured.given()
        .header("x-v", 3)
        .get("https://public.cdr.tyro.com/cds-au/v1/banking/products")
        .timeIn(TimeUnit.MILLISECONDS);

        if (actual_time > 10000) {
            throw new Exception ("Response took too long");
        }

    }
}
