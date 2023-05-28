package stepDefinitions;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.List;

import static com.jayway.restassured.RestAssured.given;
import static junit.framework.TestCase.assertEquals;

public class Steps {
    private static final String BASE_URI = "https://api.nasa.gov";
    private static final String API_PATH = "/mars-photos/api/v1/rovers/curiosity/photos?";
    private static final String API_KEY_PARAM = "api_key=";
    private static final String API_KEY_VALUE = "oKFOwjgRZdcNLY3luLdodvz1EVZe6eixbAUGReSi";
    private static final String PAGE_PARAM = "page=";
    private static final String PAGE_ONE_VALUE = "1";
    private static final String SOL_PARAM = "sol=";
    private static final String SOL_VALUE = "1000";
    private static final String CAMERA_PARAM = "camera=";
    private static final String CAMERA_MAST_VALUE = "mast";
    private static int sizeOfList;
    private static List<String> mastCameraList;


    @When("I request the first page")
    public void iRequestTheFirstPage() {
        Response response = given().when().get(BASE_URI + API_PATH + SOL_PARAM + SOL_VALUE + "&" + PAGE_PARAM + PAGE_ONE_VALUE + "&" + API_KEY_PARAM + API_KEY_VALUE).then().extract().response();
        sizeOfList = response.body().path("photos.size()");
    }

    @Then("The number of photos is {int}")
    public void theNumberOfPhotosIs(int expectedSize) {
        Assert.assertEquals(expectedSize, sizeOfList);
    }

    @When("I request the photos from the MAST camera")
    public void iRequestThePhotosFromTheMASTCamera() {
        mastCameraList = RestAssured.when().get(BASE_URI + API_PATH + SOL_PARAM + SOL_VALUE + "&" + PAGE_PARAM + PAGE_ONE_VALUE + "&" + CAMERA_PARAM + CAMERA_MAST_VALUE + "&" + API_KEY_PARAM + API_KEY_VALUE).then().extract().jsonPath().getList("photos.rover.name");
    }

    @Then("I confirm all photos were taken by the Curiosity Rover")
    public void iConfirmAllPhotosWereTakenByTheCuriosityRover() {
        for(int i = 0; i < mastCameraList.size(); i++){
            assertEquals(mastCameraList.get(i), "Curiosity");
        }
    }
}
