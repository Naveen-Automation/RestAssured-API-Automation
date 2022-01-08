package JavaObjectsToJson;

import io.cucumber.datatable.DataTable;
import requestPojos.CreateIssuePojo;
import requestPojos.LocationPojo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;

public class RequestBuilder {

   public CreateIssuePojo createIssue(int iterations, DataTable dataTable) throws IOException {

      String resource;
      int statusCode;
      String expScope;
      String expServer;

      List<Map<String, String>> table = dataTable.asMaps(String.class, String.class);

      CreateIssuePojo ci = new CreateIssuePojo();
      LocationPojo lctn = new LocationPojo();

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
}
