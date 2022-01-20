package deSerialization;

import io.restassured.response.Response;
import pojoClasses.responsePojos.CreateSalesOrderResPojo;

public class ResponseBuilder {
    public CreateSalesOrderResPojo CreateSalesOrder_ResPojoObj;
    public CreateSalesOrderResPojo CreateSalesOrder_ResponseBuilder(Response response) {
        CreateSalesOrder_ResPojoObj =  response.as(CreateSalesOrderResPojo.class);
        return CreateSalesOrder_ResPojoObj;
  }
}



//All assertions to be moved to new class files.
//        assertEquals(table.get(iterations).get("ReqBdy_Name"), resPojo.getName());
//        assertEquals(table.get(iterations).get("ReqBdy_Address"), resPojo.getAddress());
//        assertEquals(table.get(iterations).get("ReqBdy_PhoneNumber"), resPojo.getPhone_number());

