package utilities;

import stepsDefinitions.BaseSteps;

import java.beans.Statement;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Util {
    public static String logsFolderName;
    public static String logsFolderPath;

    public static String GetRandomNumber()
    {
        String randomString = String.valueOf(Math.random());
        randomString = randomString.replace(".", String.valueOf(Math.random()));
        return randomString.replace("." , "1");
    }


    public static void ExecuteMethodWhenMethodNamePassedAsString(Object targetObject, String methodName, Object[] methodArguments) throws Exception {
        Statement statement = new Statement(targetObject, methodName, methodArguments);
        statement.execute();
    }


    public static Field GetClassFieldTypeOnPassingFieldName(Object targetObject, String fieldName) throws NoSuchFieldException {
        Class ftClass = targetObject.getClass();
        return ftClass.getField(fieldName);
    }


    public static String ReplaceFirstUpperCaseCharacterOfTheStringWithLowerCaseOfSameCharacter(String targetString)
    {
        String firstCharacter = targetString.substring(0,1);
        String replaceString = targetString.replaceFirst("(?:" + firstCharacter +")+" , firstCharacter.toLowerCase());
        return replaceString;
    }

    public static String TimeStamp(String format)
    {
        String timeStamp;
        SimpleDateFormat timeStampObj = new SimpleDateFormat(format);
        Timestamp timeStampWithMilliSecObj = new Timestamp(System.currentTimeMillis());

        timeStamp = timeStampObj.format(timeStampWithMilliSecObj);
        timeStamp = timeStamp.replace("-" , "_");
        timeStamp = timeStamp.replace(":" , "_");
        return timeStamp;
    }

    public static void CreateLogsFolderIfNotPresent() throws IOException {
        if(logsFolderName == null)
        {
            File folderObj = new File(GetLogsFolderPath());
            if(!folderObj.exists())
            {
                folderObj.mkdir();
            }
        }
    }

    private static String GetLogsFolderPath() throws IOException
    {
        if(logsFolderName == null)
        {
            String projectFolderPath = System.getProperty("user.dir");
            String logsFolderRelativePath = IniFileManager.GetKeyValue("Jira", "LogsPath",  projectFolderPath + BaseSteps.environmentFilePath);
            logsFolderName = Util.TimeStamp("yyyy-MM-dd");
            logsFolderPath = projectFolderPath + logsFolderRelativePath + logsFolderName;
        }
        return logsFolderPath;
    }




    public static List<Map<String,String>> MakeAListOfHashMapFromTwoArrayLists(List<String> header, List<List<String>> valueList)
    {
        Map<String,String> hashMap = new HashMap<>();
        List<Map<String, String>> listOfMaps = new ArrayList<Map<String,String>>();
        for(int i=0; i< valueList.size(); i++)
        {
            for(int j=0; j<valueList.size(); j++)
            {
                hashMap.put(header.get(j), valueList.get(i).get(j));
            }
            listOfMaps.add(hashMap);
        }
        return listOfMaps;
    }


    public static Map<String,String> MakeAHashMapFromTwoArrayLists(List<String> header, List<String> valueList)
    {
        Map<String,String> hashMap = new HashMap<>();
            for(int j=0; j<header.size(); j++)
            {
                hashMap.put(header.get(j), valueList.get(j));
            }
        return hashMap;
    }
}
