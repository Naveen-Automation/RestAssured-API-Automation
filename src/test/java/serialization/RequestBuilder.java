package serialization;

import io.cucumber.datatable.DataTable;
import pojoClasses.requestPojos.CreateSalesOrderReqPojo;
import pojoClasses.requestPojos.LocationReqPojo;
import pojoClasses.requestPojos.UpdateSalesOrderReqPojo;
import stepsDefinitions.DXPESteps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;

public class RequestBuilder {

   public CreateSalesOrderReqPojo CreateSalesOrder(int iterations, DataTable dataTable) throws IOException {

      String resource;
      int statusCode;
      String expScope;
      String expServer;

      CreateSalesOrderReqPojo ci = new CreateSalesOrderReqPojo();
      LocationReqPojo lctn = new LocationReqPojo();

      List<Map<String, String>> table = dataTable.asMaps(String.class, String.class);
      String[] arr  = table.get(iterations).get("ReqBdy_Types").split("#,#");
      List<String> typ = new ArrayList<String>();
      typ.add(arr[0]);
      typ.add(arr[1]);
      ci.setTypes(typ);

      ci.setLocation(lctn);
      lctn.setLat(Integer.parseInt(table.get(iterations).get("ReqBdy_Lat")));
      lctn.setLng(Integer.parseInt(table.get(iterations).get("ReqBdy_Lng")));

      ci.setAccuracy(Integer.parseInt(table.get(iterations).get("ReqBdy_Accuracy")));
      ci.setName(table.get(iterations).get("ReqBdy_Name"));
      ci.setPhone_number(table.get(iterations).get("ReqBdy_PhoneNumber"));
      ci.setAddress(table.get(iterations).get("ReqBdy_Address"));
      ci.setWebsite(table.get(iterations).get("ReqBdy_Website"));
      ci.setLanguage(table.get(iterations).get("ReqBdy_Language"));

      return ci;
   }


   public UpdateSalesOrderReqPojo UpdateSalesOrder(int iterations, String place_id , DataTable dataTable) throws IOException {

      UpdateSalesOrderReqPojo pojoObject = new UpdateSalesOrderReqPojo();

      List<Map<String, String>> table = dataTable.asMaps(String.class, String.class);

      pojoObject.setPlace_id(table.get(iterations).get(place_id));
      pojoObject.setKey(table.get(iterations).get("qaclick123"));
      pojoObject.setAddress(table.get(iterations).get("ReqBdy_Address"));

      return pojoObject;

   }
}
