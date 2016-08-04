package utils;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;

public class CaptureScreenshot {

    private static final Logger log = Logger.getLogger(CaptureScreenshot.class);

    public static void takeScreenShot(WebDriverWrapper driverWrapper, String screenShotName)
            throws IOException {
        TakesScreenshot takesScreenshot = (TakesScreenshot) driverWrapper.getOriginalDriver();
        File source = takesScreenshot.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(source, new File("./target/screenshots/" + screenShotName + ".jpg"));
        log.info("screenshot taken successfully!");
    }

}