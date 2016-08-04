package utils;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigurationData {

    private static final Logger log = Logger.getLogger(ConfigurationData.class);
    /*path where to store locators*/
    private static final String UI_MAPPING_PATH = "src/main/resources/UIMapping.properties";
    private static ConfigurationData config;
    private final Properties properties;
    private Map<String, String> propertiesMap;

    /**
     * static method for return configuration data
     *
     * @return configuration data {@link ConfigurationData}
     */
    public static ConfigurationData getConfigData() {

        if (config == null) {
            config = new ConfigurationData();
        }
        return config;

    }

    /**
     * private constructor
     *
     * @throws IOException
     */
    private ConfigurationData() {

        this.properties = new Properties();
        log.info("created properties in configuration data");
        try {
            this.propertiesMap = loadPropertiesToMap();
            log.info("created map properties in configuration data");
        } catch (IOException e) {
            e.printStackTrace();
            log.error(String.format("EXCEPTION < %s >", e.getStackTrace()));
        }

    }

    /**
     * private method for loaded properties to map
     *
     * @return hash map, where stored locators,
     * where key - string, value - string {@link HashMap<String, String>}
     */
    private Map<String, String> loadPropertiesToMap() throws NoSuchElementException, IOException {

        if (Files.exists(Paths.get(UI_MAPPING_PATH))) {
            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream(UI_MAPPING_PATH);
                properties.load(fileInputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new NoSuchElementException(
                    String.format("< %s > not found EXCEPTION", UI_MAPPING_PATH.substring(13)));
        }
        return propertiesMap = new HashMap<String, String>((Map) properties);

    }

    /**
     * private method for return value property
     *
     * @return key from properties map
     */
    private String getPropertyValue(String key) {
        return propertiesMap.get(key);
    }

    /**
     * this method get locator
     *
     * @return element {@link By}
     * @throws NoSuchElementException when the locator is not found by key
     */
    public By getLocator(String key) throws NoSuchElementException {

        String[] partsOfLocators = getPropertyValue(key).split("\"");
        String findMethod = partsOfLocators[0].substring(0, partsOfLocators[0].length() - 1);
        String locator = partsOfLocators[1];

        switch (findMethod) {
            case "id":
                return By.id(locator);
            case "name":
                return By.name(locator);
            case "class":
                return By.className(locator);
            case "cssSelector":
                return By.cssSelector(locator);
            case "xpath":
                return By.xpath(locator);
            default:
                throw new NoSuchElementException(
                        String.format("locator < %s > not defined!", locator));
        }

    }

}