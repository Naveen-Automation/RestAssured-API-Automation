package utilities;

import org.ini4j.Ini;
import java.io.FileReader;
import java.io.IOException;

public class IniFileManager {
    private static Ini ini;
    private static FileReader file;


    public static String GetKeyValue(String pageName, String keyName, String filePath) throws IOException
    {
        ini = new Ini();
        file = new FileReader(filePath);
        ini.load(file);
        return ini.get(pageName, keyName);
    }
//
//    //Custom made method to get the value of the text
//    public static String GetNestedKeyValue(String pageName, String keyName, String filePath) throws IOException {
//        String a = null;
//        String str = GetKeyValue(pageName, keyName, System.getProperty("user.dir") + filePath);
//        String[] arr = str.split("\\|", -1);
//        for (String value : arr) {
//            if (value.contains(PropertiseFileManager.GetKeyValue("Language", "//src//test//resources//config//environments//TestRegion.properties") + "-")) {
//                a = value.trim().split("'")[1];
//                return a;
//            }
//        }
//        return a;
//    }
}