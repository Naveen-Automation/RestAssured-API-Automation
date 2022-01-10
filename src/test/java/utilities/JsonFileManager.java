package utilities;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class JsonFileManager {
    public String GetJsonKeyValue(String key, Response response )
    {
        String res = response.asString();
        JsonPath js = new JsonPath(res);
        return js.get(key).toString();
    }
}
