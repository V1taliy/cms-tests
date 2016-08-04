package pages;

import org.apache.log4j.Logger;
import utils.PropertyLoader;
import utils.WebDriverWrapper;

public class LoginPage extends AbstractPage {

    private static final Logger log = Logger.getLogger(LoginPage.class);
    private static final String DEV_URL = PropertyLoader.loadProperty("site.url");

    public LoginPage(WebDriverWrapper driverWrapper) {
        super(driverWrapper, DEV_URL);
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
        web.clickButton("loginButton");
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
        web.waitDisappearElement("apisSystemLoader");
    }

    public void waitInvisibilityPanelBody() {
        web.waitDisappearElement("loginForm",
                Integer.parseInt(PropertyLoader.loadProperty("wait.timeout1sec")));
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

    public boolean isLoginFormPresent() {
        return web.isElementPresent("loginForm");
    }

}