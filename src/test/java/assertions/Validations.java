package assertions;

import io.cucumber.datatable.DataTable;
import pojoClasses.responsePojos.CreateSalesOrderResPojo;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class Validations {
    public void CreateSalesOrder_Validations(CreateSalesOrderResPojo resPojoObj, int iterations, DataTable dataTable)
    {
        List<Map<String, String>> table = dataTable.asMaps(String.class, String.class);
        //All assertions to be moved to new class files.
        assertEquals(table.get(iterations).get("ResBdy_ExpScope"), resPojoObj.getScope());
        assertEquals(table.get(iterations).get("ResBdy_ExpStatus"), resPojoObj.getStatus());
    }
}
