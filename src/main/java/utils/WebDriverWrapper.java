package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Set;

public class WebDriverWrapper implements WebDriver, TakesScreenshot {

    private static final int TIME_TO_WAIT = Integer.parseInt(PropertyLoader.loadProperty("wait.timeout10sec"));
    private static WebDriver driver;

    /**
     * public constructor
     */
    public WebDriverWrapper(WebDriver driver) {
        this.driver = driver;
        WebDriverWrapper.driver = driver;
    }

    /**
     * this method return original driver
     */
    public WebDriver getOriginalDriver() {
        return driver;
    }

    @Override
    public void get(String pageLink) {
        driver.get(pageLink);
    }

    @Override
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    @Override
    public String getTitle() {
        return driver.getTitle();
    }

    @Override
    public List<WebElement> findElements(By by) {
        WebDriverWait driverWait = new WebDriverWait(driver, TIME_TO_WAIT);
        driverWait.until(ExpectedConditions.presenceOfElementLocated(by));
        return driver.findElements(by);
    }

    @Override
    public WebElement findElement(By by) throws TimeoutException {
        WebDriverWait driverWait = new WebDriverWait(driver, TIME_TO_WAIT);
        driverWait.until(ExpectedConditions.presenceOfElementLocated(by));
        return driver.findElement(by);
    }

    /**
     * This method used, when must specify other wait timeout
     *
     * @param by       element locator
     * @param timeWait new timeout value
     * @throws TimeoutException when the wait timeout is over
     */
    public WebElement findElement(By by, int timeWait) throws TimeoutException {
        WebDriverWait driverWait = new WebDriverWait(driver, timeWait);
        driverWait.until(ExpectedConditions.presenceOfElementLocated(by));
        return driver.findElement(by);
    }

    /**
     * Wait element to be clickable
     *
     * @param by       element locator
     * @param timeWait new timeout value
     * @throws TimeoutException when the wait timeout is over
     */
    public WebElement findElementToBeClickable(By by, int timeWait) throws TimeoutException {
        WebDriverWait driverWait = new WebDriverWait(driver, timeWait);
        driverWait.until(ExpectedConditions.elementToBeClickable(by));
        return driver.findElement(by);
    }

    /**
     * Wait element to be visibility
     *
     * @param by       element locator
     * @param timeWait new timeout value
     * @throws TimeoutException when the wait timeout is over
     */
    public WebElement findElementVisibility(By by, int timeWait) throws TimeoutException {
        WebDriverWait driverWait = new WebDriverWait(driver, timeWait);
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(by));
        return driver.findElement(by);
    }

    /**
     * Wait element to be invisibility
     *
     * @param by       element locator
     * @param timeWait new timeout value
     * @throws TimeoutException when the wait timeout is over
     */
    public WebElement findElementInvisibility(By by, int timeWait) throws TimeoutException {
        WebDriverWait driverWait = new WebDriverWait(driver, timeWait);
        driverWait.until(ExpectedConditions.invisibilityOfElementLocated(by));
        return driver.findElement(by);
    }

    @Override
    public String getPageSource() {
        return driver.getPageSource();
    }

    @Override
    public void close() {
        driver.close();
    }

    @Override
    public void quit() {
        driver.quit();
    }

    @Override
    public Set<String> getWindowHandles() {
        return driver.getWindowHandles();
    }

    @Override
    public String getWindowHandle() {
        return driver.getWindowHandle();
    }

    @Override
    public TargetLocator switchTo() {
        return driver.switchTo();
    }

    @Override
    public Navigation navigate() {
        return driver.navigate();
    }

    @Override
    public Options manage() {
        return driver.manage();
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> outputType) {

        try {
            if (driver instanceof FirefoxDriver) {
                return ((FirefoxDriver) driver).getScreenshotAs(outputType);
            } else if (driver instanceof ChromeDriver) {
                return ((ChromeDriver) driver).getScreenshotAs(outputType);
            } else if (driver instanceof InternetExplorerDriver) {
                return ((InternetExplorerDriver) driver).getScreenshotAs(outputType);
            } else if (driver instanceof PhantomJSDriver) {
                return ((PhantomJSDriver) driver).getScreenshotAs(outputType);
            } else {
                return null;
            }
        } catch (WebDriverException e) {
            e.printStackTrace();
        }

        return null;

    }

}