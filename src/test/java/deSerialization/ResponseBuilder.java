package deSerialization;

import io.restassured.response.Response;
import pojoClasses.responsePojos.CreateSalesOrderResPojo;

public class ResponseBuilder {
    public CreateSalesOrderResPojo createSalesOrder_ResPojoObj;
    public CreateSalesOrderResPojo CreateSalesOrder_ResponseBuilder(Response response) {
        createSalesOrder_ResPojoObj =  response.as(CreateSalesOrderResPojo.class);
        return createSalesOrder_ResPojoObj;
  }
}
