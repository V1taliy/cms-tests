package tests;

import org.apache.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import pages.Cms;
import utils.CaptureScreenshot;
import utils.PropertyLoader;
import utils.WebDriverFactory;
import utils.WebDriverWrapper;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Fixture {

    // not final, because in some situations must redefine wait timeout
    public static String impWait = PropertyLoader.loadProperty("wait.timeout20sec");
    private static final Logger log = Logger.getLogger(Fixture.class);
    public static WebDriverWrapper driverWrapper;
    public static Cms cms;

    @BeforeSuite
    public void startBrowser() {
        driverWrapper = new WebDriverWrapper(WebDriverFactory.getInstance());
        driverWrapper.manage().timeouts().implicitlyWait(Long.parseLong(impWait), TimeUnit.SECONDS);
        try {
            cms = new Cms(driverWrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info(String.format("start test suit execution"));
    }

    @AfterSuite
    public void quitBrowser() {
        if (driverWrapper != null) {
            driverWrapper.quit();
        }
        log.info(String.format("tests suite execution completed"));
    }

    @AfterMethod
    public void tearDown(ITestResult result) throws IOException {
        log.info(String.format("test status is < %s >", result.getStatus()));
        log.info(String.format("iresult status is < %s >", result.FAILURE));
        if (result.FAILURE == result.getStatus()) {
            CaptureScreenshot.takeScreenShot(driverWrapper, result.getName());
        }
    }

}