package utils;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

public class PropertyLoader {

    /**
     * path where to store properties
     */
    private static final String PROPERTY_FILE = "/application.properties";
    private static Map propertiesMap;

    /**
     * public static synchronized method for load property from property file
     */
    public static synchronized String loadProperty(String name) {

        if (propertiesMap == null) {
            loadData();
        }
        return String.valueOf(propertiesMap.get(name));

    }

    /**
     * private static synchronized method for load data from properties
     */
    private static void loadData() {

        Properties properties = new Properties();
        try {
            properties.load(PropertyLoader.class.getResourceAsStream(PROPERTY_FILE));
            propertiesMap = properties;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}