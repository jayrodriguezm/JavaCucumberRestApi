package org.example;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.List;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;

/**
 * Unit test for simple App.
 */
public class ApiTests
    extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ApiTests(String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( ApiTests.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testMartianSolPaging() {
        given().when().get("https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=1000&page=1&api_key=DEMO_KEY").then().assertThat().body("photos.size()", is(25));
        Response response = given().when().get("https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=1000&page=1&api_key=DEMO_KEY").then().extract().response();
        int sizeOfList = response.body().path("photos.size()");
        System.out.println(sizeOfList);
    }

    public void testMastCameraRover() {
        List<String> values = RestAssured.when().get("https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=1000&page=1&camera=mast&api_key=DEMO_KEY").then().extract().jsonPath().getList("photos.rover.name");
        for(int i = 0; i < values.size(); i++){
            assertEquals(values.get(i), "Curiosity");
        }
    }
}
