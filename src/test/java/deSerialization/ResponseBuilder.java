package deSerialization;

import io.cucumber.datatable.DataTable;
import io.restassured.response.Response;
import pojoClasses.responsePojos.CreateSalesOrderResPojo;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ResponseBuilder {
    public static void  CreateSalesOrder(Response response,int iterations, DataTable dataTable ) {
        CreateSalesOrderResPojo resPojo =  response.as(CreateSalesOrderResPojo.class);
        List<Map<String, String>> table = dataTable.asMaps(String.class, String.class);

        //All assertions to be moved to new class files.
        assertEquals(table.get(iterations).get("ResBdy_ExpScope"), resPojo.getScope());
        assertEquals(table.get(iterations).get("ResBdy_ExpStatus"), resPojo.getStatus());
  }
}



//All assertions to be moved to new class files.
//        assertEquals(table.get(iterations).get("ReqBdy_Name"), resPojo.getName());
//        assertEquals(table.get(iterations).get("ReqBdy_Address"), resPojo.getAddress());
//        assertEquals(table.get(iterations).get("ReqBdy_PhoneNumber"), resPojo.getPhone_number());

