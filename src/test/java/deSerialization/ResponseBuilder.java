package jsonToJavaObjects;

import io.cucumber.datatable.DataTable;
import io.restassured.response.Response;
import pojos.requestPojos.CreateSalesOrderReqPojo;
import pojos.requestPojos.LocationReqPojo;
import pojos.responsePojos.CreateSalesOrderResPojo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ResponseBuilder {

    public static void ValidateCreateSalesOrderResponse(Response response, int iterations, DataTable dataTable) {
        CreateSalesOrderResPojo resPojo = response.as(CreateSalesOrderResPojo.class);

        List<Map<String, String>> table = dataTable.asMaps(String.class, String.class);
        assertEquals(table.get(iterations).get("ResBdy_ExpScope"), resPojo.getScope());
        assertEquals(table.get(iterations).get("ResBdy_ExpStatus"), resPojo.getStatus());
    }
}
