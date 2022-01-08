package utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiseFileManager {

    //To get the value of the property key at run time
    public static String GetKeyValue( String keyName, String filePath) throws IOException
    {
        Properties prop=new Properties();
        FileInputStream fis =new FileInputStream(System.getProperty("user.dir") + filePath);
        prop.load(fis);
        return prop.getProperty(keyName);
    }

    //To set the value of the property key at run time
    public static void SetKeyValue( String keyName,String keyValue, String filePath) throws IOException
    {
        Properties prop = new Properties();
        FileOutputStream fos = new FileOutputStream(System.getProperty("user.dir") + filePath);
        prop.setProperty(keyName, keyValue);
        prop.store(fos, null);
    }
}















