package stepsDefinitions;

import hooks.SetUp;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import utilities.IniFileManager;
import utilities.Util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class BaseSteps {

    public String expServer;
    public String expKey;
    protected String jsonSpecificationFolderPath;
    private ContentType contentType;
    public static String environmentFilePath ;
    public  String resourcesFilePath= "//src//test/resources//globalVariables//resourcePath.ini";

    public  BaseSteps()
    {
        environmentFilePath= "//src//test/resources//config//environment.ini" ;
        jsonSpecificationFolderPath = "//scr//test/resources//jsonSchemas";
    }


    public RequestSpecification reqSpec; // Static allows to retain the value of reqSpec till the execution is live
    public ResponseSpecification resSpec;

    public static String getEnvironmentName() throws IOException {
        return IniFileManager.GetKeyValue("Config" , "EnvironmentName", System.getProperty("user.dir") + environmentFilePath);
    }

    public RequestSpecification GetRequestSpecification(String reqHeaders) throws IOException {

        if (reqSpec == null)
        {
            GetBasicRequestSpecification();
        }
        switch (reqHeaders.toUpperCase())
        {
            case "":
                expKey = IniFileManager.GetKeyValue("Config", "accessKey", System.getProperty("user.dir") + environmentFilePath);
                return reqSpec.queryParam("key", expKey);
            case "XCRF":
                String xCRSFToken = IniFileManager.GetKeyValue("Config", "xCSRFToken", System.getProperty("user.dir") + environmentFilePath);
                return reqSpec.header("x-crf-token", xCRSFToken).cookie("CSRF_TOKEN", xCRSFToken);
            default:
                return reqSpec;
        }
    }

public RequestSpecification GetBasicRequestSpecification() throws IOException {
    Util.CreateLogsFolderIfNotPresent();
    String environmentName = getEnvironmentName();
    String requestType = IniFileManager.GetKeyValue("Config", "ContentType",System.getProperty("user.dir") + environmentFilePath);
    String baseUri = IniFileManager.GetKeyValue(environmentName.toUpperCase(), "BaseURI", System.getProperty("user.dir") + environmentFilePath);
    String relativeLogsPath = IniFileManager.GetKeyValue("Config" , "LogsPath" ,System.getProperty("user.dir") + environmentFilePath);
    PrintStream log = new PrintStream(new FileOutputStream(System.getProperty("user.dir") + relativeLogsPath + environmentName.toLowerCase() + "//" + Util.logsFolderName + "//" + SetUp.scenario.getName() + "_" + Util.TimeStamp("yyyy-MM-dd HH:mm:ss") + ".txt"));


    if (requestType.equalsIgnoreCase("JSON"))
    {
        contentType = ContentType.JSON;
    }
    else if (requestType.equalsIgnoreCase("XML"))
    {
        contentType = ContentType.XML;
    }

    reqSpec = new RequestSpecBuilder()
            .setContentType(ContentType.JSON).setBaseUri(baseUri)
            .addFilter(new AllureRestAssured())
            .addFilter(RequestLoggingFilter.logRequestTo(log))
            .addFilter((ResponseLoggingFilter.logResponseTo(log)))
            .build();
    return reqSpec;
}


    public ResponseSpecification ResponseSpecification(String apiName)
    {
        //Need to find a better solution  to eliminate this if else condition
        expServer = "Apache/2.4.18 (Ubuntu)";

        if (apiName.equalsIgnoreCase("CreateSalesOrder")) {
            return resSpec = new ResponseSpecBuilder().expectContentType(ContentType.JSON).expectHeader("server", expServer).build();
        }
        else
        {
            return resSpec = new ResponseSpecBuilder().expectContentType(ContentType.JSON).build();
        }
    }
}
