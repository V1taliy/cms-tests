package utils;

import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.*;

public class WebElementsActions {

    private final static Logger log = Logger.getLogger(WebElementsActions.class);
    private static WebDriverWait driverWait;
    private ConfigurationData config;
    private WebDriverWrapper driverWrapper;

    public WebElementsActions(WebDriverWrapper driverWrapper) {
        this.driverWrapper = driverWrapper;
        driverWait = new WebDriverWait(driverWrapper,
                Integer.parseInt(PropertyLoader.loadProperty("wait.timeout10sec")));
        config = ConfigurationData.getConfigData();
    }

    /**
     * Open page
     *
     * @param url url{@see uniform resource locator} of the page
     */
    public void openPage(String url) {
        driverWrapper.get(url);
        log.info(String.format("browser open page < %s >" + url));
    }

    /**
     * This method return the desired element with locator
     *
     * @param elementLocator search element locator
     * @return element {@link WebElement} driverWrapper from
     * configuration {@link WebElementsActions#config}
     * @throws NoSuchElementException If the locator cannot found
     */
    public WebElement getElement(String elementLocator) throws TimeoutException {
        log.info(String.format("get element < %s >", elementLocator));
        return driverWrapper.findElement(config.getLocator(elementLocator));
    }

    /**
     * This method return a list of elements
     *
     * @param elementLocator search element locator
     * @return elements {@link List<WebElement>} driverWrapper find elements from
     * configuration {@link WebElementsActions#config}
     * @throws NoSuchElementException If the locator cannot found
     */
    public List<WebElement> getElements(String elementLocator) throws NoSuchElementException {
        log.info(String.format("get elements < %s >", elementLocator));
        return driverWrapper.findElements(config.getLocator(elementLocator));
    }

    /**
     * This method return the desired element with locator
     *
     * @param elementLocator search element locator
     * @param timeWait       time to wait for element to be displayed
     * @return element {@link WebElement} driverWrapper from
     * configuration {@link WebElementsActions#config}
     * @throws NoSuchElementException If the locator cannot found
     */
    public WebElement getElementWithTimeout(String elementLocator, int timeWait) {
        try {
            log.info(String.format("get element < %s >", elementLocator));
            return driverWrapper.findElement(config.getLocator(elementLocator),
                    Integer.parseInt(PropertyLoader.loadProperty(String.valueOf(timeWait))));
        } catch (TimeoutException e) {
            log.info("element not present in DOM");
            return null;
        }
    }

    /**
     * Clear text field or some field
     *
     * @param clearLocator search a locator
     * @throws NoSuchElementException If the locator cannot found
     */
    public void clear(String clearLocator) throws NoSuchElementException {
        driverWrapper.findElement(config.getLocator(clearLocator)).clear();
        log.info(String.format("clear element < %s >", clearLocator));
    }

    /**
     * Insert value into text input HTML field
     *
     * @param inputLocator search a locator for input
     * @param data         data input
     * @throws NoSuchElementException If the locator cannot found
     */
    public void input(String inputLocator, String data) throws NoSuchElementException {
        driverWrapper.findElement(config.getLocator(inputLocator)).sendKeys(data);
        log.info(String.format("input < %s > and send < %s >", inputLocator, data));
    }

    /**
     * Clear text field and input data
     *
     * @param inputLocator search a locator for input
     * @param inputData    search a locator for data input
     * @throws NoSuchElementException If the locator cannot found
     */
    public void clearAndInput(String inputLocator, String inputData) throws NoSuchElementException {
        driverWrapper.findElement(config.getLocator(inputLocator)).clear();
        driverWrapper.findElement(config.getLocator(inputLocator)).sendKeys(inputData);
        log.info(String.format("clear < %s > and input < %s >", inputLocator, inputData));
    }

    /**
     * Insert value into text input HTML field and Click ENTER
     * for Field which used in the xpath expression
     *
     * @param inputLocator search a locator for input
     * @param inputData    search a locator for data input
     * @throws NoSuchElementException If the locator cannot found
     */
    public void clearAndInputAndClickEnter(String inputLocator, String inputData) throws NoSuchElementException {
        driverWrapper.findElement(config.getLocator(inputLocator)).clear();
        driverWrapper.findElement(config.getLocator(inputLocator)).sendKeys(inputData);
        driverWrapper.findElement(config.getLocator(inputLocator)).sendKeys(Keys.ENTER);
        log.info(String.format("clear < %s > and send < %s > and click ENTER.", inputLocator, inputData));
    }

    /**
     * Click a link
     *
     * @param linkLocator search link locator for click
     * @throws NoSuchElementException If the locator cannot found
     */
    public void clickLink(String linkLocator) throws NoSuchElementException {
        driverWrapper.findElement(config.getLocator(linkLocator)).click();
        log.info(String.format("click on link < %s >", linkLocator));
    }

    /**
     * Click on element
     *
     * @param elementLocator search element locator for click
     * @throws NoSuchElementException If the locator cannot found
     */
    public void clickElement(String elementLocator) throws NoSuchElementException {
        driverWrapper.findElement(config.getLocator(elementLocator)).click();
        log.info(String.format("click element < %s >", elementLocator));
    }

    /**
     * Click a button
     *
     * @param buttonLocator search button locator for click
     * @throws NoSuchElementException If the locator cannot found
     */
    public void clickButton(String buttonLocator) throws NoSuchElementException {
        driverWrapper.findElement(config.getLocator(buttonLocator)).click();
        log.info(String.format("click on button < %s >", buttonLocator));
    }

    /**
     * Click button if this operation possible
     *
     * @param buttonLocator search button locator for click
     * @throws NoSuchElementException If the locator cannot found
     */
    public void clickButtonIsClickable(String buttonLocator, int timeWait) throws NoSuchElementException {
        WebElement webElement = driverWrapper.findElementToBeClickable(config.getLocator(buttonLocator), timeWait);
        if (webElement.isDisplayed()) {
            webElement.click();
            log.info(String.format("click on element < %s > with time wait < %s >", buttonLocator, timeWait));
        } else {
            log.info(String.format("not click on element < %s >", buttonLocator));
        }
    }

    /**
     * Move to element and click on this element
     *
     * @param moveToLocator search move locator
     * @throws NoSuchElementException If the locator cannot found
     */
    public void moveToElement(String moveToLocator) throws NoSuchElementException {
        WebElement webElement = driverWrapper.findElement(config.getLocator(moveToLocator));
        Actions actions = new Actions(driverWrapper.getOriginalDriver());
        actions.moveToElement(webElement);
        log.info(String.format("move to element < %s >", moveToLocator));
        actions.perform();
    }

    /**
     * Move to element and click on this element
     *
     * @param moveToLocator search move locator
     * @param clickElement  search element for click
     * @throws NoSuchElementException If the locator cannot found
     */
    public void moveToElementAndClick(String moveToLocator, WebElement clickElement) throws NoSuchElementException {
        WebElement moveToElement = driverWrapper.findElement(config.getLocator(moveToLocator));
        Actions actions = new Actions(driverWrapper.getOriginalDriver());
        actions.moveToElement(moveToElement).perform();
        WebDriverWait wait = new WebDriverWait(driverWrapper.getOriginalDriver(),
                Long.parseLong(PropertyLoader.loadProperty("wait.timeout5sec")));
        wait.until(ExpectedConditions.elementToBeClickable(clickElement));
        clickElement.click();
        log.info(String.format("move to element < %s > and click on element < %s >",
                moveToLocator, clickElement.getTagName()));
    }

    /**
     * Move to element in position and click on this element
     *
     * @param moveToLocator search move locator
     * @param xValue        coordinates relative to the x
     * @param yValue        coordinates relative to the y
     * @throws NoSuchElementException If the locator cannot found
     */
    public void moveToElementInPointAndClick(String moveToLocator, int xValue, int yValue) throws NoSuchElementException {
        WebElement webElement = driverWrapper.findElement(config.getLocator(moveToLocator));
        Dimension size = webElement.getSize();
        Actions actions = new Actions(driverWrapper.getOriginalDriver());
        int x = driverWrapper.findElement(config.getLocator(moveToLocator)).getLocation().getX();
        int y = driverWrapper.findElement(config.getLocator(moveToLocator)).getLocation().getY();
        double width = size.getWidth();
        double height = size.getHeight();
        log.info(String.format("click on < %s > in position: x = < %d >, " +
                        "y = < %d > AND element: width = < %s >, height = < %s >",
                moveToLocator, x, y, width, height));
        actions.moveToElement(webElement, size.getWidth() - xValue,
                size.getHeight() - yValue).click().build().perform();
    }

    /**
     * Method is used to check that element is present on page
     *
     * @param elementLocator search element locator
     * @return true if element is present on page, otherwise false
     * @throws NoSuchElementException If the locator cannot found
     */
    public boolean isElementPresent(String elementLocator) throws NoSuchElementException {
        if (!driverWrapper.findElement(config.getLocator(elementLocator)).isDisplayed()) {
            log.info(String.format("< %s > not present on page.", elementLocator));
            return false;
        }
        log.info(String.format("< %s > is present on page.", elementLocator));
        return true;
    }

    /**
     * Method is used to check that element is present on page with time wait
     *
     * @param elementLocator search element locator
     * @param timeWait       time to wait for element to be displayed
     * @return true if element is present on page, otherwise false
     * @throws NoSuchElementException If the locator cannot found
     */
    public boolean isElementPresent(String elementLocator, int timeWait) throws TimeoutException {
        WebElement webElement = driverWrapper.findElementVisibility(config.getLocator(elementLocator), timeWait);
        if (webElement.isDisplayed()) {
            log.info(String.format("element < %s > is displayed", elementLocator));
            return true;
        }
        log.info(String.format("element < %s > is not displayed", elementLocator));
        return false;
    }

    /**
     * This method is used to agree tabStatus on pop-up windows
     *
     * @return true if alert is present on page, otherwise false
     * @throws NoAlertPresentException If alert is not found on page
     */
    public boolean isAlertPresentAndAccept() {
        boolean isAlertPresent = false;
        try {
            Alert alert = driverWrapper.switchTo().alert();
            alert.accept();
            isAlertPresent = true;
            log.info(String.format("alert is present on page"));
        } catch (NoAlertPresentException e) {
            e.printStackTrace();
            log.error(String.format("alert is not found. NoAlertPresentException < %s >", e.getMessage()));
            return isAlertPresent;
        }
        log.info(String.format("success, alert no present on page"));
        return isAlertPresent;
    }

    /**
     * This method is used to get text from pop-up windows
     *
     * @return alert text
     * @throws NoAlertPresentException If alert is not found on page
     */
    public String getAlertText() {
        String alertText;
        try {
            Alert alert = driverWrapper.switchTo().alert();
            alertText = alert.getText();
            alert.accept();
            log.info(String.format("alert text < %s >", alertText));
        } catch (NoAlertPresentException e) {
            alertText = "alert is not found";
            log.error(String.format("< %s > . NoAlertPresentException < %s >", alertText, e.getMessage()));
            e.printStackTrace();
        }
        return alertText;
    }

    /**
     * First method for refresh page
     */
    public void refreshPage() {
        driverWrapper.navigate().refresh();
        log.info(String.format("page < %s > refresh", driverWrapper.getCurrentUrl()));
    }

    /**
     * An expectation for checking that an element is present on the DOM of a page and visible.
     * Visibility means that the element is not only displayed but also has a height and width
     * that is greater than 0.
     * Advantages of this method over isUserLogIn(By elementLocator);
     * is that it expects the appearance of an element.
     *
     * @param elementLocator search element locator
     * @return true If element is present, otherwise false
     * @throws NoSuchElementException If the locator cannot found
     */
    public boolean waitForElementPresent(String elementLocator) throws NoSuchElementException {
        if (elementLocator != null) {
            driverWait.until(ExpectedConditions.
                    visibilityOfElementLocated(config.getLocator(elementLocator)));
            log.info(String.format("wait for element < %s > present", elementLocator));
            return true;
        } else {
            log.info(String.format("waiting for the appearance of the element < %s > was not successful, " +
                    "wait for element < %s > present", elementLocator, elementLocator));
            return false;
        }
    }

    /**
     * An expectation for checking an element is visible and enabled such that you
     * can click it.
     *
     * @param elementLocator used to find the element
     * @return true If the WebElement once it is located and clickable (visible and enabled), otherwise false
     * @throws IOException If the locator cannot found
     */
    public boolean waitElementToBeClickable(String elementLocator) throws IOException {
        if (driverWait.until(ExpectedConditions.
                elementToBeSelected(config.getLocator(elementLocator)))) {
            driverWait.until(ExpectedConditions.
                    elementToBeClickable(config.getLocator(elementLocator)));
            log.info(String.format("wait element < %s > to be clickable", elementLocator));
            return true;
        }
        log.info(String.format("wait element < %s > not to be clickable", elementLocator));
        return false;

    }

    /**
     * An expectation for checking an element is visible and enabled such that you
     * can click it.
     *
     * @param webElement can this element to be clickable
     * @return true If the WebElement once it is located and clickable (visible and enabled), otherwise false
     * @throws NoSuchElementException If the element cannot found
     */
    public boolean waitElementToBeClickable(WebElement webElement) throws NoSuchElementException {
        if (driverWait.until(ExpectedConditions.elementToBeSelected(webElement))) {
            driverWait.until(ExpectedConditions.elementToBeClickable(webElement));
            log.info(String.format("element < %s > to be clickable", webElement));
            return true;
        }
        log.info(String.format("element < %s > not clickable", webElement));
        return false;
    }

    /**
     * This method checks whether there is an element on the page
     * that overlaps another element
     *
     * @param disappearLocator element that overlaps
     * @throws NoSuchElementException
     * @throws TimeoutException
     */
    public boolean waitDisappearElement(String disappearLocator) throws NoSuchElementException, TimeoutException {
        WebDriverWait wait = new WebDriverWait(driverWrapper.getOriginalDriver(),
                Long.parseLong(PropertyLoader.loadProperty("wait.timeout10sec")));
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(config.getLocator(disappearLocator)));
    }

    /**
     * This method checks whether there is an element on the page
     * that overlaps another element
     *
     * @param disappearLocator element that overlaps
     * @param timeWait         time to wait for element to be displayed
     * @throws NoSuchElementException
     * @throws TimeoutException
     */
    public boolean waitDisappearElement(String disappearLocator, int timeWait) throws NoSuchElementException, TimeoutException {
        WebDriverWait wait = new WebDriverWait(driverWrapper.getOriginalDriver(), timeWait);
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(config.getLocator(disappearLocator)));
    }

    /**
     * This method checks whether there is an element on the page that overlaps another element
     *
     * @param disappearLocator element that overlaps
     * @throws NoSuchElementException, {@link InterruptedException}
     */
    public void waitOpacityElement(final String disappearLocator) throws NoSuchElementException {
        WebDriverWait wait = new WebDriverWait(driverWrapper.getOriginalDriver(),
                Long.parseLong(PropertyLoader.loadProperty("wait.timeout10sec")));
        wait.until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                WebElement webElement = driverWrapper.findElement(config.getLocator(disappearLocator));
                String opacity = webElement.getCssValue("opacity");
                String zIndex = webElement.getCssValue("z-index");
                if (opacity.equals("0") && zIndex.equals("-1")) {
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * Wait to present of element on the page
     *
     * @param visibilityLocator visibility element locator
     * @throws NoSuchElementException
     */
    public boolean waitElementToBeVisibility(String visibilityLocator) throws NoSuchElementException {
        WebElement element = driverWrapper.findElement(config.getLocator(visibilityLocator));
        WebDriverWait wait = new WebDriverWait(driverWrapper.getOriginalDriver(),
                Long.parseLong(PropertyLoader.loadProperty("wait.timeout10sec")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(config.getLocator(visibilityLocator)));
        return element.isDisplayed();
    }

    /**
     * This method is used to wait for getting response from all Ajax requests
     *
     * @param timeoutSeconds the timeout in seconds when an expectation is called
     * @return true If {@param driverWrapper} instanceof {@link JavascriptExecutor},otherwise false
     */
    public boolean waitForAjaxResponse(int timeoutSeconds) {
        if (driverWrapper instanceof JavascriptExecutor) {
            JavascriptExecutor jsDriver = (JavascriptExecutor) driverWrapper;
            for (int i = 0; i < timeoutSeconds; i++) {
                Long numberOfConnections = (Long) jsDriver.executeScript("return jQuery.active");
                if (numberOfConnections instanceof Long) {
                    log.debug(String.format("number of active jQuery Ajax calls is < %d >", numberOfConnections));
                    if (numberOfConnections == 0) {
                        break;
                    }
                }
            }
            return true;
        } else {
            log.info(String.format("web elements actions driverWrapper: < %s > can't execute JavaScript", driverWrapper));
            return false;
        }
    }

    /**
     * Select/deselect the checkbox
     *
     * @param checkBoxLocator search check box locator
     * @throws NoSuchElementException If the locator cannot found
     */
    public void selectCheckBox(String checkBoxLocator) throws NoSuchElementException {
        if (driverWrapper.findElement(config.getLocator(checkBoxLocator)).isSelected()) {
            driverWrapper.findElement(config.getLocator(checkBoxLocator)).click();
            log.info(String.format("select < %s >", checkBoxLocator));
        }
    }

    /**
     * Press a button TAB {@info is recommended to enter a single value in
     * {@link WebElementsActions#pressTAB(String)}
     *
     * @param locator search locator for press button {@link Keys#TAB}
     * @throws ElementNoSuch If the locator cannot found
     */
    public void pressTAB(String locator) throws NoSuchElementException {
        WebElement webElement = driverWrapper.findElement(config.getLocator(String.valueOf(locator)));
        webElement.sendKeys(Keys.TAB);
        log.info(String.format("press TAB"));
    }

    /**
     * Press a button Space
     *
     * @param locator press the space on the locator
     * @see {@link Keys#SPACE}
     */
    public void pressSpace(String locator) throws NoSuchElementException {
        WebElement webElement = driverWrapper.findElement(config.getLocator(locator));
        webElement.sendKeys(Keys.SPACE);
        log.info(String.format("press space"));
    }

    /**
     * Press a button
     *
     * @param locator press the space on the locator
     * @see {@link Keys#ENTER}
     */
    public void pressENTER(String locator) throws NoSuchElementException {
        WebElement webElement = driverWrapper.findElement(config.getLocator(locator));
        webElement.sendKeys(Keys.ENTER);
        log.info(String.format("press ENTER"));
    }

    /**
     * Scroll a window in up
     *
     * @see {@link JavascriptExecutor} and {@link JavascriptExecutor#executeScript(String, Object...)}
     */
    public void windowScrollUp(int valuePixels) {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driverWrapper.getOriginalDriver();
        // Vertical scroll - up by -valuePixels
        javascriptExecutor.executeScript("window.scrollBy(0,-" + valuePixels + ")", "");
    }

    /**
     * Scroll a window in down
     *
     * @see {@link JavascriptExecutor} and {@link JavascriptExecutor#executeScript(String, Object...)}
     */
    public void windowScrollDown(int valuePixels) {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driverWrapper.getOriginalDriver();
        // Vertical scroll - up by 100  pixels
        javascriptExecutor.executeScript("window.scrollBy(0," + valuePixels + ")", "");
    }

    /**
     * Scroll to element taking into account the height of the header
     *
     * @param elementLocator element to which you want to scroll
     */
    public void scrollToElement(String elementLocator, String someElement) {
        WebElement element = driverWrapper.findElement(config.getLocator(elementLocator));
        WebElement element2 = driverWrapper.findElement(config.getLocator(someElement));
        int elementCoordinateY = element.getLocation().getY();
        int headerHeight = element2.getSize().getHeight();
        int scrollValue = elementCoordinateY - headerHeight;
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driverWrapper.getOriginalDriver();
        javascriptExecutor.executeScript("window.scrollBy(0," + scrollValue + ")", "");
    }

}