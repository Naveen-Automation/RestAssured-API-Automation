package stepsDefinitions;

import bsh.EvalError;
import static org.junit.Assert.*;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import io.cucumber.java.en.*;
import io.cucumber.datatable.DataTable;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import static io.restassured.RestAssured.given;
import static java.lang.Integer.parseInt;
import enums.ServiceEndPoints;
import pojoClasses.requestPojos.CreateSalesOrderReqPojo;
import pojoClasses.requestPojos.UpdateSalesOrderReqPojo;
import pojoClasses.responsePojos.CreateSalesOrderResPojo;
import serialization.RequestBuilder;
import deSerialization.ResponseBuilder;
import utilities.Utils;


public class DXPESteps extends BaseSteps{
    private RequestSpecification reqSpec;
    private ResponseSpecification resSpec;
    private RequestSpecification requestBody;
    private Response response;
    private CreateSalesOrderReqPojo reqPojoObjCreateSalesOrder;
    private CreateSalesOrderResPojo resPojoObjCreateSalesOrder;
    private UpdateSalesOrderReqPojo reqPojoObjUpdateSalesOrder;

    private RequestBuilder requestBuilder = new RequestBuilder();
    private ResponseBuilder responseBuilder = new ResponseBuilder();
    private String reqPathParameters;
    private String reqHeaders;

    @Given("the user creates a json request body for {string} API with {string} http method using below details {int}")
    public void Creates_Post_Put_Delete_Request_Body( String apiName, String httpMethod, int iterations, DataTable dataTable) throws Exception
    {

        List<Map<String, String>> table = dataTable.asMaps(String.class, String.class);
        reqPathParameters = table.get(iterations).get("ReqPpm");
        reqHeaders = table.get(iterations).get("ReqHdr");

        if(reqHeaders == null || reqHeaders.equalsIgnoreCase("N/A") || reqHeaders.equalsIgnoreCase("NA"))
        {
            reqSpec = RequestSpecification(httpMethod);
        }
        else if (reqHeaders.equalsIgnoreCase(""))
        {
//            reqSpec = AccessKeyRequestSpecification(httpMethod);
        }

        Object[] methodArguments = new Object[]{iterations,dataTable};
        Utils.ExecuteMethodWhenMethodNamePassedAsString (requestBuilder, apiName, methodArguments);
        Field field = Utils.GetClassFieldTypeOnPassingFieldName(requestBuilder,apiName + "ReqPojoObject");
        requestBody = given().spec(reqSpec).body(field.get(requestBuilder));
    }


    @When("the user calls {string} API with {string} http request")
    public void Hit_EndPoint_With_Http_CRUD_Methods(String apiName, String httpMethod) throws IOException
    {
        //Getting the endpoint from the enum
        ServiceEndPoints serviceEndPoint = ServiceEndPoints.valueOf(apiName);
        serviceEndPoint.getEndPoint();
        if(reqPathParameters == null || reqPathParameters.equalsIgnoreCase("N/A") || reqPathParameters.equalsIgnoreCase("NA"))
        {
            reqPathParameters = "";
        }

        switch(httpMethod.toUpperCase()) {
            case "POST":
                response = requestBody.when().post(serviceEndPoint.getEndPoint() + reqPathParameters);
                break;
            case "PUT":
                response = requestBody.when().put(serviceEndPoint.getEndPoint()+ reqPathParameters);
                break;
            case "GET":
                response = requestBody.when().get(serviceEndPoint.getEndPoint()+ reqPathParameters);
                break;
            case "DELETE":
                response = requestBody.when().delete(serviceEndPoint.getEndPoint()+ reqPathParameters);
                break;
        }
    }


    @Then("the user should get the status code as {int}")
    public void Validate_Http_Status_Code(int expstatusCode)
    {
        int actResponseCode = response.getStatusCode();
        assertEquals(expstatusCode, actResponseCode);
    }

    @Then("the elapsed response time should be less than threshold")
    public void ValidateResponseTimeIsUnderThreshold()
    {
        long actResponseTime = response.getTime();
        if(actResponseTime < 6000L)
        {
            assertTrue("Response Time is under threshold " + actResponseTime, true);
        }
    }


    @Then("the response json of {string} API should match below details {int}")
    public void Validate_Response_Body_With_Expected_Values(String apiName, int iterations, DataTable dataTable) throws Exception {
        resSpec = ResponseSpecification();
        response.then().spec(resSpec);
        Object[] methodArguments = new Object[]{response, iterations, dataTable};
        Utils.ExecuteMethodWhenMethodNamePassedAsString (responseBuilder, apiName, methodArguments);
    }

    @Given("the user creates the http {string} request")
    public void GetRequest_Without_Query_Parameters(String string)
    {
        requestBody = given().spec(reqSpec).queryParam(resPojoObjCreateSalesOrder.getPlace_id());
    }


    @Given("the user creates the http {string}} request using below details")
    public void GetRequest_With_Query_Parameters(String string)
    {
        requestBody = given().spec(reqSpec);
    }

}
