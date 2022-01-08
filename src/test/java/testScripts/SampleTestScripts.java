package testScripts;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;
import requestPojos.CreateIssuePojo;
import requestPojos.LocationPojo;
import utilities.IniFileManager;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static java.lang.Integer.parseInt;
import static org.hamcrest.Matchers.equalTo;

public class SampleTestScripts {

    @Test
    public void AddIssue() throws IOException {
         String environmentFilePath = "//src//test/resources//config//environment.ini" ;
         String resourcesFilePath= "//src//test/resources//globalVariables//resourcePath.ini";

        //RestAssured.baseURI = IniFileManager.GetKeyValue("Jira" , "baseURI" , System.getProperty("user.dir") + environmentFilePath);
        String resource     = IniFileManager.GetKeyValue("Jira Resources" , "createIssue" , System.getProperty("user.dir") + resourcesFilePath);
        String expKey       = IniFileManager.GetKeyValue("Jira" , "accessKey" , System.getProperty("user.dir") + environmentFilePath );

        //Serialization
        CreateIssuePojo ci = new CreateIssuePojo();
        LocationPojo lctn = new LocationPojo();
       // List<String> typ = List.of("shoe park","shop");
        List<String> typ = new ArrayList<String>();
        typ.add("shoe park");
        typ.add("shop");
        lctn.setLat(35);
        lctn.setLng(40);
        ci.setLocation(lctn);
        ci.setAccuracy(50);
        ci.setName("Naveen");
        ci.setPhone_number("9911574224");
        ci.setAddress("29, side layout, cohen 09");
        ci.setTypes(typ);
        ci.setWebsite("http://google.com");
        ci.setLanguage("French-IN");

        //Below values would be passed from the feature file 'Then' step
        String expScope = "APP";
        String expServer = "Apache/2.4.18 (Ubuntu)";
        String contentType = "application/json";

        // Need to be moved to a base class
        RequestSpecification req = new RequestSpecBuilder().setContentType(ContentType.JSON).setBaseUri(IniFileManager.GetKeyValue("Jira" , "SITBaseURI" , System.getProperty("user.dir") + environmentFilePath)).addQueryParam("key",expKey).build();

        // Need to be moved to a base class
        ResponseSpecification res = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).expectHeader("server" ,expServer).build();

        //
        String response = given().spec(req).body(ci)
                          .when().post(resource)
                          .then().assertThat().statusCode(200).body("scope", equalTo(expScope)).header("server" ,expServer ).extract().response().asString();


        System.out.println("====================================\n RESPONSE\n ====================================\n" + response );
    }


}
