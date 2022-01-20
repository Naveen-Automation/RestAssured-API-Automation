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

   public CreateSalesOrderReqPojo createSalesOrder_ReqPojoObj;
   public UpdateSalesOrderReqPojo updateSalesOrder_ReqPojoObj;

   public CreateSalesOrderReqPojo CreateSalesOrder_RequestBuilder(int iterations, DataTable dataTable) throws IOException {

      String resource;
      int statusCode;
      String expScope;
      String expServer;

      createSalesOrder_ReqPojoObj = new CreateSalesOrderReqPojo();
      LocationReqPojo lctn = new LocationReqPojo();

      List<Map<String, String>> table = dataTable.asMaps(String.class, String.class);
      String[] arr  = table.get(iterations).get("ReqBdy_Types").split("#,#");
      List<String> typ = new ArrayList<String>();
      typ.add(arr[0]);
      typ.add(arr[1]);
      createSalesOrder_ReqPojoObj.setTypes(typ);

      createSalesOrder_ReqPojoObj.setLocation(lctn);
      lctn.setLat(Integer.parseInt(table.get(iterations).get("ReqBdy_Lat")));
      lctn.setLng(Integer.parseInt(table.get(iterations).get("ReqBdy_Lng")));

      createSalesOrder_ReqPojoObj.setAccuracy(Integer.parseInt(table.get(iterations).get("ReqBdy_Accuracy")));
      createSalesOrder_ReqPojoObj.setName(table.get(iterations).get("ReqBdy_Name"));
      createSalesOrder_ReqPojoObj.setPhone_number(table.get(iterations).get("ReqBdy_PhoneNumber"));
      createSalesOrder_ReqPojoObj.setAddress(table.get(iterations).get("ReqBdy_Address"));
      createSalesOrder_ReqPojoObj.setWebsite(table.get(iterations).get("ReqBdy_Website"));
      createSalesOrder_ReqPojoObj.setLanguage(table.get(iterations).get("ReqBdy_Language"));

      return createSalesOrder_ReqPojoObj;
   }


   public UpdateSalesOrderReqPojo UpdateSalesOrder_RequestBuilder(int iterations, String place_id , DataTable dataTable) throws IOException {

      updateSalesOrder_ReqPojoObj = new UpdateSalesOrderReqPojo();

      List<Map<String, String>> table = dataTable.asMaps(String.class, String.class);

      updateSalesOrder_ReqPojoObj.setPlace_id(table.get(iterations).get(place_id));
      updateSalesOrder_ReqPojoObj.setKey(table.get(iterations).get("qaclick123"));
      updateSalesOrder_ReqPojoObj.setAddress(table.get(iterations).get("ReqBdy_Address"));

      return updateSalesOrder_ReqPojoObj;
   }
}
