package tests;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.PropertyLoader;
import java.util.concurrent.TimeUnit;

public class LoginTests extends Fixture {

    private static final Logger log = Logger.getLogger(LoginTests.class);
    private static final String INVALID_NAME = PropertyLoader.loadProperty("invalid.name");
    private static final String INVALID_PASSWORD = PropertyLoader.loadProperty("invalid.password");
    private static final String USER_NAME = PropertyLoader.loadProperty("user.name");
    private static final String USER_PASSWORD = PropertyLoader.loadProperty("user.password");
    private static final String ADMIN_NAME = PropertyLoader.loadProperty("admin.name");
    private static final String ADMIN_EMAIL = PropertyLoader.loadProperty("admin.email");
    private static final String ADMIN_PASSWORD = PropertyLoader.loadProperty("admin.password");
    private static final int newImpWait = 300;

    @Test(priority = 1)
    public void openWebSite() {
        cms.loginPage.openLoginPage();
        cms.loginPage.waitInvisibilityLoader();
        Assert.assertTrue(cms.loginPage.isLoginFormPresent());
        impWait = String.valueOf(newImpWait);
        driverWrapper.manage().timeouts().implicitlyWait(Long.parseLong(impWait), TimeUnit.MILLISECONDS);
    }

    @Test(priority = 2, dependsOnMethods = {"openWebSite"})
    public void loginWithNonExistingCredentials() {
        fillLoginForm(INVALID_NAME,INVALID_PASSWORD);
    }

    @Test(priority = 3, dependsOnMethods = {"openWebSite"})
    public void loginAfterClearingBothFields() {
        cms.loginPage.inputUserNameAndPasswordAndClearFields(INVALID_NAME, INVALID_PASSWORD);
        Assert.assertEquals(cms.loginPage.isFieldRequiredUserNameTextPresent(), true);
    }

    @Test(priority = 4, dependsOnMethods = {"openWebSite"})
    public void submitEmptyLoginForm() {
        cms.loginPage.inputUserNameAndPasswordAndClearFields(INVALID_NAME, INVALID_PASSWORD);
        cms.loginPage.clickLoginButton();
        Assert.assertEquals(cms.loginPage.getTextFromElement(), "SIGN IN TO CONTINUE.");
    }

    @Test(priority = 5, dependsOnMethods = {"openWebSite"})
    public void loginAsUser() {
        cms.web.refreshPage();
        cms.loginPage.waitInvisibilityLoader();
        cms.loginPage.inputUserName(USER_NAME);
        cms.loginPage.inputPassword(USER_PASSWORD);
        cms.loginPage.clickLoginButton();
        Assert.assertEquals(driverWrapper.getTitle(), "Admin :: Araneum - Multisite manage tool");
        cms.mainPage.clickLogoutButton();
    }

    @Test(priority = 6, dependsOnMethods = {"openWebSite"})
    public void loginAsAdminUsingEmail() {
        cms.loginPage.inputUserName(ADMIN_EMAIL);
        cms.loginPage.inputPassword(ADMIN_PASSWORD);
        cms.loginPage.clickLoginButton();
        Assert.assertEquals(driverWrapper.getTitle(), "Admin :: Araneum - Multisite manage tool");
        cms.mainPage.clickLogoutButton();
    }

    @Test(priority = 7, dependsOnMethods = {"openWebSite"})
    public void loginAsAdmin() {
        cms.loginPage.inputUserName(ADMIN_NAME);
        cms.loginPage.inputPassword(ADMIN_PASSWORD);
        cms.loginPage.clickLoginButton();
        Assert.assertEquals(driverWrapper.getTitle(), "Admin :: Araneum - Multisite manage tool");
        cms.mainPage.clickLogoutButton();
    }

    private void fillLoginForm(String username, String password) {
        cms.loginPage.inputUserName(username);
        cms.loginPage.inputPassword(password);
        cms.loginPage.clickLoginButton();
        cms.loginPage.waitInvisibilityLoginForm();
        cms.loginPage.waitAlertInvalidUserOrPass();
        Assert.assertTrue(cms.loginPage.isAlertInvalidUserOrPassPresent());
    }

}