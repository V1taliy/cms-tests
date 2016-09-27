package utils;

import net.anthavio.phanbedder.Phanbedder;
import org.apache.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class WebDriverFactory {

    private static final Logger log = Logger.getLogger(WebDriverFactory.class);

    public static final String BROWSER_NAME = PropertyLoader.loadProperty("browser.name");
    public static final String BROWSER_VERSION = PropertyLoader.loadProperty("browser.version");
    public static final String PLATFORM = PropertyLoader.loadProperty("browser.platform");

    public static final String GRID_HUB = PropertyLoader.loadProperty("grid.hub");
    public static final boolean GRID_STATUS = Boolean.parseBoolean(PropertyLoader.loadProperty("grid.status"));

    /*Platform constants*/
    public static final String WINDOWS = PropertyLoader.loadProperty("platform.windows");
    public static final String LINUX = PropertyLoader.loadProperty("platform.linux");
    public static final String MAC = PropertyLoader.loadProperty("platform.mac");

    /*
    * Browsers constants
    * if necessary, you can add new browsers
    * */
    private static final String FIREFOX = "firefox";
    private static final String CHROME = "chrome";
    private static final String INTERNET_EXPLORER = "internet explorer";
    private static final String HTML_UNIT = "htmlunit";
    private static final String MOBILE_EMULATOR = "mobileEmulator";
    private static final String PHANTOMJS = "phantomjs";

    private static final String FIREFOX_PATH = PropertyLoader.loadProperty("firefox.path");
    private static final String GECKO_PATH = PropertyLoader.loadProperty("geckodriver.path");
    private static final String CHROME_PATH = PropertyLoader.loadProperty("chromedriver.path");
    private static final String PHANTOMJS_PATH = PropertyLoader.loadProperty("phantomjsdriver.path");
    private static final String INTERNET_EXPLORER_PATH = PropertyLoader.loadProperty("internetExplorer.path");
    private static final String DEVICE_NAME = PropertyLoader.loadProperty("device.name");

    public static WebDriver driver;
    public static WebDriverWrapper driverWrapper;
    public static DesiredCapabilities capabilities;
    private static GridInitialization gridInit = null;

    public WebDriverFactory() {
    }

    public static WebDriver getGridInstance() {
        gridInit = new GridInitialization(BROWSER_NAME, BROWSER_VERSION, PLATFORM);
        capabilities = new DesiredCapabilities();
        capabilities.setJavascriptEnabled(true);
        log.info("<--- start work web_driver factory --->");
        setBrowserAndVersion(capabilities);
        log.info(String.format("<--- successful set up browser & version = %s --->", capabilities));
        setPlatform(capabilities);
        log.info(String.format("<---successful set up PLATFORM = %s --->", capabilities));
        driver = new RemoteWebDriver(getHubURL(), capabilities);
        driver.manage().deleteAllCookies();
//        driver.manage().window().setSize(new Dimension(1920, 1080));
        driver.manage().window().maximize();
        log.info(String.format("Screen resolution - %s", driver.manage().window().getSize()));
        return driver;
    }

    /**
     * static method that returns the WebDriver
     *
     * @return new web driver
     */
    public static WebDriverWrapper initDriver() {

        capabilities = new DesiredCapabilities();

        if (FIREFOX.equals(BROWSER_NAME)) {

//            System.setProperty("webdriver.firefox.bin", FIREFOX_PATH);
            System.setProperty("webdriver.gecko.driver", GECKO_PATH);
            DesiredCapabilities desiredCapabilities = DesiredCapabilities.firefox();
            desiredCapabilities.setCapability("marionette", true);
            driverWrapper = new WebDriverWrapper(new FirefoxDriver(desiredCapabilities));

        } else if (CHROME.equals(BROWSER_NAME)) {

            System.setProperty("webdriver.chrome.driver", CHROME_PATH);
            ChromeOptions options = new ChromeOptions();
            driverWrapper = new WebDriverWrapper(new ChromeDriver(options));

        } else if (MOBILE_EMULATOR.equals(BROWSER_NAME)) {

            System.setProperty("webdriver.chrome.driver", CHROME_PATH);

            Map<String, String> mobileEmulation = new HashMap<String, String>();
            mobileEmulation.put("deviceName", DEVICE_NAME);

            Map<String, Object> chromeOptions = new HashMap<String, Object>();
            chromeOptions.put("mobileEmulation", mobileEmulation);

            capabilities = DesiredCapabilities.chrome();
            capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

            driverWrapper = new WebDriverWrapper(new ChromeDriver(WebDriverFactory.capabilities));

        } else {
            Assert.fail("invalid driver name");
        }

        driverWrapper.manage().deleteAllCookies();
        driverWrapper.manage().window().maximize();
        return driverWrapper;

    }

    /**
     * Factory method to return a WebDriver instance given the browser to hit.
     *
     * @param capabilities DesiredCapabilities object coming from getGridInstance().
     */
    public static void setBrowserAndVersion(DesiredCapabilities capabilities) {
        if (CHROME.equals(BROWSER_NAME)) {
            System.setProperty("webdriver.chrome.driver", CHROME_PATH);
            capabilities.setBrowserName(BROWSER_NAME);
            capabilities = DesiredCapabilities.chrome();
            capabilities.setCapability("chrome.switches",
                    Arrays.asList("--ignore-certificate-errors"));
            capabilities.setCapability(CapabilityType.SUPPORTS_APPLICATION_CACHE, true);
        } else if (FIREFOX.equals(BROWSER_NAME)) {
//            System.setProperty("webdriver.firefox.bin", FIREFOX_PATH);
            System.setProperty("webdriver.gecko.driver", GECKO_PATH);
            capabilities.setBrowserName(BROWSER_NAME);
            capabilities = DesiredCapabilities.firefox();
            capabilities.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
            capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
            capabilities.setCapability("marionette", true);
        } else if (PHANTOMJS.equals(BROWSER_NAME)) {
            capabilities.setBrowserName(BROWSER_NAME);
            File phantomjs = Phanbedder.unpack();
            capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, PHANTOMJS_PATH);
            /*capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
                    phantomjs.getAbsolutePath());*/
        } else if (INTERNET_EXPLORER.equals(BROWSER_NAME)) {
            System.setProperty("webdriver.ie.driver", INTERNET_EXPLORER_PATH);
            capabilities.setBrowserName(BROWSER_NAME);
            capabilities = DesiredCapabilities.internetExplorer();
            capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
            capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
            capabilities.setCapability("browserstack.ie.enablePopups", false);
        } else if (HTML_UNIT.equals(BROWSER_NAME)) {
            capabilities.setBrowserName(BROWSER_NAME);
            capabilities = DesiredCapabilities.htmlUnit();
        } else if (MOBILE_EMULATOR.equals(BROWSER_NAME)) {
            System.setProperty("webdriver.chrome.driver", CHROME_PATH);
            capabilities.setBrowserName(BROWSER_NAME);
            Map<String, String> mobileEmulation = new HashMap<String, String>();
            mobileEmulation.put("deviceName", DEVICE_NAME);
            Map<String, Object> chromeOptions = new HashMap<String, Object>();
            chromeOptions.put("mobileEmulation", mobileEmulation);
            capabilities = DesiredCapabilities.chrome();
            capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
        } else {
            Assert.fail("invalid driver name");
        }
        if (BROWSER_VERSION != null) {
            capabilities.setVersion(BROWSER_VERSION);
            capabilities.setCapability("browser_version", BROWSER_VERSION);
        }
    }

    /**
     * Method makes the check and returns hub url
     *
     * @return hub url {@link URL}
     */
    public static URL getHubURL() {
        URL hubUrl = null;
        try {
            hubUrl = new URL(GRID_HUB);
            log.info("<--- HUB_URL = " + GRID_HUB + " --->");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        // In case there is no Hub
        if (hubUrl == null) {
            log.error("HUB_URL == null!\n");
            Assert.fail("hub URL == null");
            return null;
        } else {
            return hubUrl;
        }
    }

    /**
     * Helper method to set version and PLATFORM for a specific browser
     *
     * @param capabilities : DesiredCapabilities object coming from getGridInstance()
     */
    private static void setPlatform(DesiredCapabilities capabilities) {
        if (LINUX.equalsIgnoreCase(PLATFORM)) {
            capabilities.setPlatform(Platform.LINUX);
        } else if (WINDOWS.equalsIgnoreCase(PLATFORM)) {
            capabilities.setPlatform(Platform.WINDOWS);
        } else if (MAC.equalsIgnoreCase(PLATFORM)) {
            capabilities.setPlatform(Platform.MAC);
        } else {
            capabilities.setPlatform(Platform.ANY);
        }
    }

}