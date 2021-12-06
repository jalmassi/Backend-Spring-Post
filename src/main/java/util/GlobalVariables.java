package util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GlobalVariables {


    //    public PostComparator sortBy;
//    final public String url;
    public String url;
    private static String directory;

    @Value("${filesystem.directory}")
    public void setDirectory(String value) {
        this.directory = value;
    }
//
//    private static GlobalVariables instance;
//
//    public static GlobalVariables getInstance() {
//        if (instance == null) instance = new GlobalVariables();
//        return instance;
//    }
}
