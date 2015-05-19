package halive.visualsort.local;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Localizer {

    private static Properties langFile;

    public static void init(InputStream in)throws IOException{
        langFile = new Properties();
        langFile.load(in);
    }

    public static void init(String filename) {

    }

    public static void test() {
        langFile.list(System.out);
    }

    public static String localize(String key) {
        return langFile.getProperty(key);
    }

}
