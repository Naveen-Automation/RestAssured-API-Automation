package stepsDefinitions;

import assertions.Validations;
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
import utilities.IniFileManager;
import utilities.Util;


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
    private Validations validations = new Validations();
    private String reqPathParameters;
    private String reqHeaders;

    @Given("the user creates a json request body for {string} API with {string} http method using below details {int}")
    public void Creates_Post_Put_Delete_Request_Body( String apiName, String httpMethod, int iterations, DataTable dataTable) throws Exception
    {
        String fieldName;

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
        Util.ExecuteMethodWhenMethodNamePassedAsString (requestBuilder, apiName + "_RequestBuilder", methodArguments);

        fieldName = Util.ReplaceFirstUpperCaseCharacterOfTheStringWithLowerCaseOfSameCharacter(apiName) + "_ReqPojoObj";

//      Accessing class fields dynamically using reflection by passing the field name as a string to the 'GetClassFieldTypeOnPassingFieldName' method
        Field field = Util.GetClassFieldTypeOnPassingFieldName(requestBuilder, fieldName);

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
    public void ValidateResponseTimeIsUnderThreshold() throws IOException {
        long actResponseTime = response.getTime();
        long thresholdResponseTime =  Long.parseLong(IniFileManager.GetKeyValue("Jira", "ThresholdResponseTime", System.getProperty("user.dir") + environmentFilePath));
        if(actResponseTime < thresholdResponseTime)
        {
            assertTrue("Response Time is under threshold " + actResponseTime, true);
        }
    }


    @Then("the response json of {string} API should match below details {int}")
    public void Validate_Response_Body_With_Expected_Values(String apiName, int iterations, DataTable dataTable) throws Exception {
        String fieldName;
        resSpec = ResponseSpecification();
        response.then().spec(resSpec);

//      ArrayList of the Arguments of the 'Method' to be executed as a part of 'ExecuteMethodWhenMethodNamePassedAsString' method
        Object[] methodArgument = new Object[]{response};

//      Calling a method dynamically at run time via method name which is passed as a string value
        Util.ExecuteMethodWhenMethodNamePassedAsString (responseBuilder, apiName + "_ResponseBuilder", methodArgument);

        fieldName = Util.ReplaceFirstUpperCaseCharacterOfTheStringWithLowerCaseOfSameCharacter(apiName) + "_ResPojoObj";
//      Accessing class fields dynamically using reflection by passing the field name as a string to the 'GetClassFieldTypeOnPassingFieldName' method
        Field field = Util.GetClassFieldTypeOnPassingFieldName(responseBuilder, fieldName);

//      Calling a method dynamically at run time via method name which is passed as a string value
        Object[] methodArguments = new Object[]{field.get(responseBuilder), iterations, dataTable};

//      Calling a method dynamically at run time via method name which is passed as a string value
        Util.ExecuteMethodWhenMethodNamePassedAsString (validations, apiName + "_Validations", methodArguments);

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
