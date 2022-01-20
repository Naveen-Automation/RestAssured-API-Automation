package serialization;

import io.cucumber.datatable.DataTable;
import pojoClasses.requestPojos.CreateSalesOrderReqPojo;
import pojoClasses.requestPojos.LocationReqPojo;
import pojoClasses.requestPojos.UpdateSalesOrderReqPojo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;

public class RequestBuilder {

   public CreateSalesOrderReqPojo CreateSalesOrder_ReqPojoObj;
   public UpdateSalesOrderReqPojo UpdateSalesOrder_ReqPojoObj;

   public CreateSalesOrderReqPojo CreateSalesOrder_RequestBuilder(int iterations, DataTable dataTable) throws IOException {

      String resource;
      int statusCode;
      String expScope;
      String expServer;

      CreateSalesOrder_ReqPojoObj = new CreateSalesOrderReqPojo();
      LocationReqPojo lctn = new LocationReqPojo();

      List<Map<String, String>> table = dataTable.asMaps(String.class, String.class);
      String[] arr  = table.get(iterations).get("ReqBdy_Types").split("#,#");
      List<String> typ = new ArrayList<String>();
      typ.add(arr[0]);
      typ.add(arr[1]);
      CreateSalesOrder_ReqPojoObj.setTypes(typ);

      CreateSalesOrder_ReqPojoObj.setLocation(lctn);
      lctn.setLat(Integer.parseInt(table.get(iterations).get("ReqBdy_Lat")));
      lctn.setLng(Integer.parseInt(table.get(iterations).get("ReqBdy_Lng")));

      CreateSalesOrder_ReqPojoObj.setAccuracy(Integer.parseInt(table.get(iterations).get("ReqBdy_Accuracy")));
      CreateSalesOrder_ReqPojoObj.setName(table.get(iterations).get("ReqBdy_Name"));
      CreateSalesOrder_ReqPojoObj.setPhone_number(table.get(iterations).get("ReqBdy_PhoneNumber"));
      CreateSalesOrder_ReqPojoObj.setAddress(table.get(iterations).get("ReqBdy_Address"));
      CreateSalesOrder_ReqPojoObj.setWebsite(table.get(iterations).get("ReqBdy_Website"));
      CreateSalesOrder_ReqPojoObj.setLanguage(table.get(iterations).get("ReqBdy_Language"));

      return CreateSalesOrder_ReqPojoObj;
   }


   public UpdateSalesOrderReqPojo UpdateSalesOrder_RequestBuilder(int iterations, String place_id , DataTable dataTable) throws IOException {

      UpdateSalesOrder_ReqPojoObj = new UpdateSalesOrderReqPojo();

      List<Map<String, String>> table = dataTable.asMaps(String.class, String.class);

      UpdateSalesOrder_ReqPojoObj.setPlace_id(table.get(iterations).get(place_id));
      UpdateSalesOrder_ReqPojoObj.setKey(table.get(iterations).get("qaclick123"));
      UpdateSalesOrder_ReqPojoObj.setAddress(table.get(iterations).get("ReqBdy_Address"));

      return UpdateSalesOrder_ReqPojoObj;
   }
}
