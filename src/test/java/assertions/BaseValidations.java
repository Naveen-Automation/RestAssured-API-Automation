package assertions;

import io.restassured.response.Response;
import stepsDefinitions.BaseSteps;
import utilities.IniFileManager;

import java.io.IOException;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class BaseValidations
{
    public void SchemaValidations(String jsonSchemaFileName, Response response){
        //using Hamcrest assertions library
        response.then().body(matchesJsonSchemaInClasspath(jsonSchemaFileName));
    }

    public void ResponseCodeValidation(int expStatusCode, Response response)
    {
     int actResponseCode = response.getStatusCode();
     //Using Junit assertions
     assertEquals(expStatusCode,actResponseCode);
    }

    public void ResponseTimeValidations(Response response) throws IOException
    {
        long actResponseTime = response.getTime();
        long thresholdTimeOut = Long.parseLong(IniFileManager.GetKeyValue("Config", "ThresholdResponseTime", System.getProperty("user.dir")+ BaseSteps.environmentFilePath));
        if(actResponseTime < thresholdTimeOut)
        {
            assertTrue("Response Time is under threshold " + actResponseTime, true);
        }
        else
        {
            assertFalse("Response Time is not under threshold " + actResponseTime, false);
        }
    }
}
