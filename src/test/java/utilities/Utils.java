package utilities;

import java.beans.Statement;
import java.lang.reflect.Field;


public class Utils {
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
}
