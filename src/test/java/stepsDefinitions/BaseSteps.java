package stepsDefinitions;

import hooks.SetUp;
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

    public static String environmentFilePath = "//src//test/resources//config//environment.ini" ;
    public  String resourcesFilePath= "//src//test/resources//globalVariables//resourcePath.ini";

    public static RequestSpecification reqSpec; // Static allows to retain the value of reqSpec till the execution is live
    public static ResponseSpecification resSpec;


    public RequestSpecification RequestSpecification(String httpMethod) throws IOException {

//            if (httpMethod.equalsIgnoreCase("GET"))
//            {
//                if (reqSpec == null)
//                {
//                    String baseUri = IniFileManager.GetKeyValue("Jira", "SITBaseURI", System.getProperty("user.dir") + environmentFilePath);
//                    PrintStream log = new PrintStream(new FileOutputStream(System.getProperty("user.dir") + "//src//test//resources//Logs//" + SetUp.scenario.getName() + ".txt"));
//                    expKey = IniFileManager.GetKeyValue("Jira", "accessKey", System.getProperty("user.dir") + environmentFilePath);
//
//                    reqSpec = new RequestSpecBuilder()
//                            .setBaseUri(baseUri).addQueryParam("key", expKey)
//                            .addFilter(RequestLoggingFilter.logRequestTo(log))
//                            .addFilter((ResponseLoggingFilter.logResponseTo(log)))
//                            .build();
//                    return reqSpec;
//                }
//            }
//            else
//            {
//                if (reqSpec == null)
//                {
//                    String baseUri = IniFileManager.GetKeyValue("Jira", "SITBaseURI", System.getProperty("user.dir") + environmentFilePath);
//                    PrintStream log = new PrintStream(new FileOutputStream(System.getProperty("user.dir") + "//src//test//resources//Logs//" + SetUp.scenario.getName() + ".txt"));
//                    expKey = IniFileManager.GetKeyValue("Jira", "accessKey", System.getProperty("user.dir") + environmentFilePath);
//
//                    reqSpec = new RequestSpecBuilder()
//                            .setContentType(ContentType.JSON).setBaseUri(baseUri).addQueryParam("key", expKey)
//                            .addFilter(RequestLoggingFilter.logRequestTo(log))
//                            .addFilter((ResponseLoggingFilter.logResponseTo(log)))
//                            .build();
//                    return reqSpec;
//                }
//            }
//            return reqSpec;


        if (reqSpec == null)
        {
            Util.CreateLogsFolderIfNotPresent();
            String baseUri = IniFileManager.GetKeyValue("Jira", "SITBaseURI", System.getProperty("user.dir") + environmentFilePath);
            PrintStream log = new PrintStream(new FileOutputStream(System.getProperty("user.dir") + "//src//test//resources//Logs//" + Util.logsFolderName + "//" + SetUp.scenario.getName() + "_" + Util.TimeStamp("yyyy-MM-dd HH:mm:ss") + ".txt"));
            expKey = IniFileManager.GetKeyValue("Jira", "accessKey", System.getProperty("user.dir") + environmentFilePath);
            reqSpec = new RequestSpecBuilder()
                    .setContentType(ContentType.JSON).setBaseUri(baseUri).addQueryParam("key", expKey)
                    .addFilter(RequestLoggingFilter.logRequestTo(log))
                    .addFilter((ResponseLoggingFilter.logResponseTo(log)))
                    .build();
            return reqSpec;
        }
        return reqSpec;
    }


    public ResponseSpecification ResponseSpecification()
    {
        expServer = "Apache/2.4.18 (Ubuntu)";
        return resSpec = new ResponseSpecBuilder().expectContentType(ContentType.JSON).expectHeader("server" ,expServer).build();
    }
}
