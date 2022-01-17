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

   public CreateSalesOrderReqPojo CreateSalesOrderReqPojoObject;
   public UpdateSalesOrderReqPojo UpdateSalesOrderReqPojoObject;

   public CreateSalesOrderReqPojo CreateSalesOrder(int iterations, DataTable dataTable) throws IOException {

      String resource;
      int statusCode;
      String expScope;
      String expServer;

      CreateSalesOrderReqPojoObject = new CreateSalesOrderReqPojo();
      LocationReqPojo lctn = new LocationReqPojo();

      List<Map<String, String>> table = dataTable.asMaps(String.class, String.class);
      String[] arr  = table.get(iterations).get("ReqBdy_Types").split("#,#");
      List<String> typ = new ArrayList<String>();
      typ.add(arr[0]);
      typ.add(arr[1]);
      CreateSalesOrderReqPojoObject.setTypes(typ);

      CreateSalesOrderReqPojoObject.setLocation(lctn);
      lctn.setLat(Integer.parseInt(table.get(iterations).get("ReqBdy_Lat")));
      lctn.setLng(Integer.parseInt(table.get(iterations).get("ReqBdy_Lng")));

      CreateSalesOrderReqPojoObject.setAccuracy(Integer.parseInt(table.get(iterations).get("ReqBdy_Accuracy")));
      CreateSalesOrderReqPojoObject.setName(table.get(iterations).get("ReqBdy_Name"));
      CreateSalesOrderReqPojoObject.setPhone_number(table.get(iterations).get("ReqBdy_PhoneNumber"));
      CreateSalesOrderReqPojoObject.setAddress(table.get(iterations).get("ReqBdy_Address"));
      CreateSalesOrderReqPojoObject.setWebsite(table.get(iterations).get("ReqBdy_Website"));
      CreateSalesOrderReqPojoObject.setLanguage(table.get(iterations).get("ReqBdy_Language"));

      return CreateSalesOrderReqPojoObject;
   }


   public UpdateSalesOrderReqPojo UpdateSalesOrder(int iterations, String place_id , DataTable dataTable) throws IOException {

      UpdateSalesOrderReqPojoObject = new UpdateSalesOrderReqPojo();

      List<Map<String, String>> table = dataTable.asMaps(String.class, String.class);

      UpdateSalesOrderReqPojoObject.setPlace_id(table.get(iterations).get(place_id));
      UpdateSalesOrderReqPojoObject.setKey(table.get(iterations).get("qaclick123"));
      UpdateSalesOrderReqPojoObject.setAddress(table.get(iterations).get("ReqBdy_Address"));

      return UpdateSalesOrderReqPojoObject;

   }
}
