package stepsDefinitions;

import bsh.EvalError;

import enums.ServiceEndPoints;
import io.cucumber.java.en.*;
import io.cucumber.datatable.DataTable;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojoClasses.requestPojos.CreateSalesOrderReqPojo;
import pojoClasses.requestPojos.UpdateSalesOrderReqPojo;
import pojoClasses.responsePojos.CreateSalesOrderResPojo;
import serialization.RequestBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static java.lang.Integer.parseInt;
import deSerialization.ResponseBuilder;
import static org.junit.Assert.*;

public class DXPESteps extends BaseSteps{
    private RequestSpecification reqSpec;
    private ResponseSpecification resSpec;
    private RequestSpecification requestBody;
    private Response response;
    private CreateSalesOrderReqPojo reqPojoObjCreateSalesOrder;
    private CreateSalesOrderResPojo resPojoObjCreateSalesOrder;
    private UpdateSalesOrderReqPojo reqPojoObjUpdateSalesOrder;
    //To be part of DataTable
    private String expScope;

    private String resource;

    private RequestBuilder requestBuilder = new RequestBuilder();

    @Given("the user authenticates using {string}")
    public void the_user_authenticates_using(String string)
    {
    }


    @Given("the user creates a json body for {string} API with {string} http request using below details {int}")
    public void the_user_creates_a_http_request_body_using_below_details( String apiName, String httpMethod, int iterations, DataTable dataTable) throws IOException
    {
        reqSpec = RequestSpecification(httpMethod);

        switch(apiName.toUpperCase()) {
            case "CREATESALESORDER":
                reqPojoObjCreateSalesOrder = requestBuilder.CreateSalesOrder(iterations, dataTable);
                requestBody = given().spec(reqSpec).body(reqPojoObjCreateSalesOrder);
                break;
            case "UPDATESALESORDER":
                reqPojoObjUpdateSalesOrder = requestBuilder.UpdateSalesOrder(iterations, resPojoObjCreateSalesOrder.getPlace_id(), dataTable);
                requestBody = given().spec(reqSpec).body(reqPojoObjUpdateSalesOrder);
                break;
        }
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


    @Then("the response json of {string} API should match below details {int}")
    public void response_should_match_below_details(String apiName, int iterations, DataTable dataTable) throws EvalError {
        resSpec = ResponseSpecification();
        response.then().spec(resSpec);

        List<Map<String, String>> table = dataTable.asMaps(String.class, String.class);

        switch(apiName.toUpperCase()) {
            case "CREATESALESORDER":
                resPojoObjCreateSalesOrder = ResponseBuilder.CreateSalesOrderResponse(response);
                assertEquals(table.get(iterations).get("ResBdy_ExpScope"), resPojoObjCreateSalesOrder.getScope());
                assertEquals(table.get(iterations).get("ResBdy_ExpStatus"), resPojoObjCreateSalesOrder.getStatus());
                break;
            case "GETSALESORDER":
                assertEquals(table.get(iterations).get("ReqBdy_Name"), reqPojoObjCreateSalesOrder.getName());
                assertEquals(table.get(iterations).get("ReqBdy_Address"), reqPojoObjCreateSalesOrder.getAddress());
                assertEquals(table.get(iterations).get("ReqBdy_PhoneNumber"), reqPojoObjCreateSalesOrder.getPhone_number());
                break;

        }

//        Interpreter interpreter = new Interpreter();
//        Object classObject = interpreter.eval( apiName + "ResPojo.class");
//        Object resPojo = interpreter.eval("Object resPojo = response.as((Type) classObject)");
//        Object assertObject = interpreter.eval ( "assertEquals(expScope, resPojo.getScope())");

    }

    @Given("the user creates the http {string} request")
    public void GetRequest_Without_Query_Parameters(String string) {
        requestBody = given().spec(reqSpec).queryParam(resPojoObjCreateSalesOrder.getPlace_id());
    }


    @Given("the user creates the http {string}} request using below details")
    public void GetRequest_With_Query_Parameters(String string) {
        requestBody = given().spec(reqSpec);
    }

}
