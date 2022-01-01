package utilities;

import org.ini4j.Ini;

public class IniReader {

    public static String GetKeyValue(){
        Ini ini = new Ini(new File(filename));
        java.util.prefs.Preferences prefs = new IniPreferences(ini);
        System.out.println("grumpy/homePage: " + prefs.node("grumpy").get("homePage", null));
    }
}
