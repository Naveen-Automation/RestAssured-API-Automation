package steps;

import bsh.EvalError;

import enums.ServiceEndPoints;
import io.cucumber.java.en.*;
import io.cucumber.datatable.DataTable;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojos.requestPojos.CreateSalesOrderReqPojo;
import serialization.RequestBuilder;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static java.lang.Integer.parseInt;
import deSerialization.ResponseBuilder;
import static org.junit.Assert.*;

public class DXPESteps extends BaseSteps{
    private RequestSpecification reqSpec;
    private ResponseSpecification resSpec;
    private RequestSpecification requestBody;
    private Response response;
    private CreateSalesOrderReqPojo requestPojoObject;

    //To be part of DataTable
    private String expScope;

    private String resource;

    private RequestBuilder reqbuild = new RequestBuilder();

    @Given("the user authenticates using {string}")
    public void the_user_authenticates_using(String string)
    {
    }


    @Given("the user creates a json body for the http {string} request using below details {int}")
    public void the_user_creates_a_http_request_body_using_below_details( String requestType, int iterations, DataTable dataTable) throws IOException
    {
        reqSpec = RequestSpecification();

        //Need to use reflection to call the API method from the string passed from the Feature file
        requestPojoObject  = reqbuild.CreateSalesOrder(iterations, dataTable);
        requestBody = given().spec(reqSpec).body(requestPojoObject);
    }


    @When("the user calls {string} API with {string} http request")
    public void the_user_calls_api(String apiName, String httpMethod) throws IOException
    {
        //Getting the endpoint from the enum
        ServiceEndPoints serviceEndPoint = ServiceEndPoints.valueOf(apiName);
        serviceEndPoint.getEndPoint();

        switch(httpMethod.toUpperCase()) {
            case "POST":
                response = requestBody.when().post(serviceEndPoint.getEndPoint());
                break;
            case "PUT":
                response = requestBody.when().put(serviceEndPoint.getEndPoint());
                break;
            case "GET":
                response = requestBody.when().get(serviceEndPoint.getEndPoint());
                break;
            case "DELETE":
                response = requestBody.when().delete(serviceEndPoint.getEndPoint());
                break;
        }
    }


    @Then("the user should get the status code as {int}")
    public void the_user_should_get_the_status_code_as(int expstatusCode)
    {
        int actResponseCode = response.getStatusCode();
        assertEquals(expstatusCode, actResponseCode);
    }


    @Then("the response {string} of {string} API should match below details {int}")
    public void response_should_match_below_details(String responseType, String apiName, int iterations, DataTable dataTable) throws EvalError {
//        expScope = "APP";
        resSpec = ResponseSpecification();
        response.then().spec(resSpec);

        switch(apiName.toUpperCase()) {
            case "CREATESALESORDER":
                ResponseBuilder.ValidateCreateSalesOrderResponse(response, iterations, dataTable);
                break;
            case "A":

                break;
            case "B":

                break;
            case "":

                break;
        }


//        Interpreter interpreter = new Interpreter();
//        Object classObject = interpreter.eval( apiName + "ResPojo.class");
//        Object resPojo = interpreter.eval("Object resPojo = response.as((Type) classObject)");
//        Object assertObject = interpreter.eval ( "assertEquals(expScope, resPojo.getScope())");

    }

}
