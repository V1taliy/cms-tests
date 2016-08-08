package utils;

import net.anthavio.phanbedder.Phanbedder;
import org.apache.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
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

    public static final String browserName = PropertyLoader.loadProperty("browser.name");
    public static final String browserVersion = PropertyLoader.loadProperty("browser.version");
    public static final String platform = PropertyLoader.loadProperty("browser.platform");

    public static final String gridHub = PropertyLoader.loadProperty("grid.hub");

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
    private static final String CHROME_PATH = PropertyLoader.loadProperty("chromedriver.path");
    private static final String PHANTOMJS_PATH = PropertyLoader.loadProperty("phantomjsdriver.path");
    private static final String INTERNET_EXPLORER_PATH = PropertyLoader.loadProperty("internetExplorer.path");
    private static final String DEVICE_NAME = PropertyLoader.loadProperty("device.name");
    public static WebDriver driver;
    private static GridInitialization gridInit = null;

    public WebDriverFactory() {
    }

    public static WebDriver getInstance() {
        gridInit = new GridInitialization(browserName, browserVersion, platform);
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setJavascriptEnabled(true);
        log.info("<--- start work web_driver factory --->");
        setBrowserAndVersion(capabilities);
        log.info(String.format("<--- successful set up browser & version = %s --->", capabilities));
        setPlatform(capabilities);
        log.info(String.format("<---successful set up platform = %s --->", capabilities));
        driver = new RemoteWebDriver(getHubURL(), capabilities);
        driver.manage().deleteAllCookies();
        driver.manage().window().setSize(new Dimension(1920, 1080));
        driver.manage().window().maximize();
        log.info(String.format("Screen resolution - %s", driver.manage().window().getSize()));
        return driver;
    }

    /**
     * Factory method to return a WebDriver instance given the browser to hit.
     *
     * @param capabilities DesiredCapabilities object coming from getInstance().
     */
    public static void setBrowserAndVersion(DesiredCapabilities capabilities) {
        if (CHROME.equals(browserName)) {
            System.setProperty("webdriver.chrome.driver", CHROME_PATH);
            capabilities.setBrowserName(browserName);
            capabilities = DesiredCapabilities.chrome();
            capabilities.setCapability("chrome.switches",
                    Arrays.asList("--ignore-certificate-errors"));
            capabilities.setCapability(CapabilityType.SUPPORTS_APPLICATION_CACHE, true);
        } else if (FIREFOX.equals(browserName)) {
            System.setProperty("webdriver.firefox.bin", FIREFOX_PATH);
            capabilities.setBrowserName(browserName);
            capabilities = DesiredCapabilities.firefox();
            capabilities.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
            capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        } else if (PHANTOMJS.equals(browserName)) {
            capabilities.setBrowserName(browserName);
            File phantomjs = Phanbedder.unpack();
            capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, PHANTOMJS_PATH);
            /*capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
                    phantomjs.getAbsolutePath());*/
        } else if (INTERNET_EXPLORER.equals(browserName)) {
            System.setProperty("webdriver.ie.driver", INTERNET_EXPLORER_PATH);
            capabilities.setBrowserName(browserName);
            capabilities = DesiredCapabilities.internetExplorer();
            capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
            capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
            capabilities.setCapability("browserstack.ie.enablePopups", false);
        } else if (HTML_UNIT.equals(browserName)) {
            capabilities.setBrowserName(browserName);
            capabilities = DesiredCapabilities.htmlUnit();
        } else if (MOBILE_EMULATOR.equals(browserName)) {
            System.setProperty("webdriver.chrome.driver", CHROME_PATH);
            capabilities.setBrowserName(browserName);
            Map<String, String> mobileEmulation = new HashMap<String, String>();
            mobileEmulation.put("deviceName", DEVICE_NAME);
            Map<String, Object> chromeOptions = new HashMap<String, Object>();
            chromeOptions.put("mobileEmulation", mobileEmulation);
            capabilities = DesiredCapabilities.chrome();
            capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
        } else {
            Assert.fail("invalid driver name");
        }
        if (browserVersion != null) {
            capabilities.setVersion(browserVersion);
            capabilities.setCapability("browser_version", browserVersion);
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
            hubUrl = new URL(gridHub);
            log.info("<--- HUB_URL = " + gridHub + " --->");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        // In case there is no Hub
        if (hubUrl == null) {
            log.error("HUBURL == null!\n");
            Assert.fail("hub URL == null");
            return null;
        } else {
            return hubUrl;
        }
    }

    /**
     * Helper method to set version and platform for a specific browser
     *
     * @param capabilities : DesiredCapabilities object coming from getInstance()
     */
    private static void setPlatform(DesiredCapabilities capabilities) {
        if (LINUX.equalsIgnoreCase(platform)) {
            capabilities.setPlatform(Platform.LINUX);
        } else if (WINDOWS.equalsIgnoreCase(platform)) {
            capabilities.setPlatform(Platform.WINDOWS);
        } else if (MAC.equalsIgnoreCase(platform)) {
            capabilities.setPlatform(Platform.MAC);
        } else {
            capabilities.setPlatform(Platform.ANY);
        }
    }

}