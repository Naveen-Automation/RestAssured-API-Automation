package steps;

import io.cucumber.java.en.*;
import io.cucumber.datatable.DataTable;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import requestPojos.CreateIssuePojo;
import JavaObjectsToJson.RequestBuilder;
import utilities.IniFileManager;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static java.lang.Integer.parseInt;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

public class DXPESteps extends BaseSteps{
    private RequestSpecification reqSpec;
    private ResponseSpecification resSpec;
    private RequestSpecification requestBody;
    private Response response;
    private CreateIssuePojo requestPojoObject;

    //To be part of DataTable
    private String expScope;

    private String resource;

    private RequestBuilder reqbuild = new RequestBuilder();

    @Given("the user authenticates using {string}")
    public void the_user_authenticates_using(String string) {
    }


    @Given("the user creates a http post request using below details {int}")
    public void the_user_creates_a_http_post_request_using_below_details(int iterations, DataTable dataTable) throws IOException {

        reqSpec = RequestSpecification();

        //Need to use reflection concept to call the API method from the string passed from the Feature file
        requestPojoObject  = reqbuild.createIssue(iterations, dataTable);
        requestBody = given().spec(reqSpec).body(requestPojoObject);
    }


    @When("the user calls {string} API by hitting below end point")
    public void the_user_calls_api_by_hitting_below_end_point(String string, DataTable dataTable) throws IOException {
        resource     = IniFileManager.GetKeyValue("Jira Resources" , "createIssue" , System.getProperty("user.dir") + resourcesFilePath);
        response = requestBody.when().post(resource);
        //System.out.println("====================================\n RESPONSE\n ====================================\n" + response );
    }


    @Then("the user should get the status code as {int}")
    public void the_user_should_get_the_status_code_as(int expstatusCode) {
        int actResponseCode = response.getStatusCode();
        assertEquals(expstatusCode, actResponseCode);
    }


    @Then("response {string} should match below details")
    public void response_should_match_below_details(String string, DataTable dataTable) {
        expScope = "APP";

        resSpec = ResponseSpecification();
        response.then().spec(resSpec);

        response.then().spec(resSpec).body("scope", equalTo(expScope));
        //OR
        String res=response.asString();
        JsonPath js=new JsonPath(res);
        assertEquals(expScope, js.getString("scope"));
    }

}
