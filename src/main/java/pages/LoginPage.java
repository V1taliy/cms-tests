package pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import utils.PropertyLoader;
import utils.WebDriverWrapper;

public class LoginPage extends AbstractPage {

    private static final Logger log = Logger.getLogger(LoginPage.class);
    private static final String LOGIN_URL = PropertyLoader.loadProperty("login.url");

    public LoginPage(WebDriverWrapper driverWrapper) {
        super(driverWrapper, LOGIN_URL);
    }

    public boolean openLoginPage() {
        return openPage();
    }

    /**
     * Input user name in user name field on a page
     *
     * @param userName user name data
     */
    public void inputUserName(String userName) {
        web.clickLink("userNameField");
        web.clearAndInput("userNameField", userName);
    }

    /**
     * Input password in password field on a page
     *
     * @param password password data
     */
    public void inputPassword(String password) {
        web.clickLink("passwordField");
        web.clearAndInput("passwordField", password);
    }

    /**
     * Input user name and password and after that clear this fields
     */
    public void inputUserNameAndPasswordAndClearFields(String userName, String password) {
        inputUserName(userName);
        web.clear("userNameField");
        inputPassword(password);
        web.clear("passwordField");
    }

    /**
     * Click login button
     */
    public void clickLoginButton() {
        driverWrapper.findElement(By.xpath(".//button[contains(@class, 'btn')]")).click();
    }

    /**
     * Is alert preset on a page
     *
     * @return true if alert 'Invalid username or password' present on a page, otherwise false
     */
    public boolean isAlertInvalidUserOrPassPresent() {
        return web.isElementPresent("alertInvalidUserOrPass",
                Integer.parseInt(PropertyLoader.loadProperty("wait.timeout5sec")));
    }

    public void waitAlertInvalidUserOrPass() {
        web.waitElementToBeVisibility("alertInvalidUserOrPass");
    }

    public void waitInvisibilityLoader() {
        web.waitDisappearElement("AraneumLoader");
    }

    public void waitInvisibilityLoginForm() {
        web.waitDisappearElement("loginForm",
                Integer.parseInt(PropertyLoader.loadProperty("wait.timeout3sec")));
    }

    /**
     * Check is text 'Field required' present on a page
     *
     * @return true if text 'Field required' for userName field and
     * password field present, otherwise false
     */
    public boolean isFieldRequiredUserNameTextPresent() {
        return web.isElementPresent("fieldRequiredUserNameText") &&
               web.isElementPresent("fieldRequiredPasswordText");
    }

    public String getPageURL() {
        return getCurrentPageURL();
    }

    /**
     * Check is Login Form present on a page
     *
     * @return true if Login Form present, otherwise - false
     */
    public boolean isLoginFormPresent() {
        return web.isElementPresent("loginForm");
    }

}