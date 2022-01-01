package Utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiseFileManager {
    private static Properties prop;
    private FileInputStream fis;
    private FileOutputStream fos;

    //To set the value of the property key at run time
    public static String GetKeyValue( String keyName, String filePath) throws IOException {
        prop=new Properties();
        FileInputStream file =new FileInputStream(System.getProperty("user.dir") + filePath);
        prop.load(file);
        return prop.getProperty(keyName);
    }

    //To set the value of the property key at run time
    public static void SetKeyValue( String keyName,String keyValue, String filePath) throws IOException {
        prop = new Properties();
        FileOutputStream fos = new FileOutputStream(System.getProperty("user.dir") + filePath);
        //prop.load(fos);
        prop.setProperty(keyName, keyValue);
        prop.store(fos, null);
    }

}















