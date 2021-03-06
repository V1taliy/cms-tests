package tests.UpperMenu;

import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriverException;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.Fixture;
import utils.PropertyLoader;
import utils.WebElementsActions;

import java.util.concurrent.TimeUnit;

public class RightSide extends Fixture {
    private static final String ADMIN_NAME = PropertyLoader.loadProperty("admin.name");
    private static final String ADMIN_PASSWORD = PropertyLoader.loadProperty("admin.password");
    private static final int newImpWait = 300;
    private final static Logger log = Logger.getLogger(WebElementsActions.class);

    @Test(priority = 1)
    public static void openWebSiteAndLogin() throws InterruptedException {
        cms.loginPage.openLoginPage();
        cms.loginPage.waitInvisibilityLoader();
        impWait = String.valueOf(newImpWait);
        driverWrapper.manage().timeouts().implicitlyWait(Long.parseLong(impWait), TimeUnit.MILLISECONDS);
        cms.loginPage.inputUserName(ADMIN_NAME);
        cms.loginPage.inputPassword(ADMIN_PASSWORD);
        cms.loginPage.clickLoginButton();
        Thread.sleep(2000);
        Assert.assertEquals(driverWrapper.getTitle(), "Admin :: Araneum - Multisite manage tool");
    }

    @Test(priority = 2, dependsOnMethods = {"openWebSiteAndLogin"})
    public void searchField() throws InterruptedException {
        cms.mainPage.clickOnUpperMenuItem("magnifierIcon");
        Thread.sleep(2000);
        Assert.assertTrue(cms.web.isElementPresent("searchField"));
        Thread.sleep(2000);
        cms.mainPage.searchDismiss("closeSearchField");
        Thread.sleep(2000);
        Assert.assertFalse(cms.web.isElementPresent("searchField"));
    }

    @Test(priority = 3, dependsOnMethods = {"openWebSiteAndLogin"})
    public void screenSize() throws InterruptedException {
        cms.mainPage.clickOnUpperMenuItem("toggleScreenSize");
        Thread.sleep(2000);
        cms.mainPage.clickOnUpperMenuItem("toggleScreenSize");
    }

    @Test(priority = 4, dependsOnMethods = {"openWebSiteAndLogin"})
    public void clickSettingsIcon() {
        cms.mainPage.clickOnUpperMenuItem("settingsIcon");
        Assert.assertTrue(cms.web.isElementPresent("settingsMenu"));
        try {
            if (cms.web.isElementPresent("toggleBoxedOn")) {
                cms.web.clickElement("toggleBoxed");
            }
        } catch (WebDriverException e) {
            log.info("The toggle Boxed is off");
        }
        try {
            if (cms.web.isElementPresent("toggleCollapsedOn")) {
                cms.web.clickElement("toggleCollapsed");
            }
        } catch (WebDriverException e) {
            log.info("The toggle Collapsed is off");
        }
        try {
            if (cms.web.isElementPresent("toggleFloatedOn")) {
                cms.web.clickElement("toggleFloated");
            }
        } catch (WebDriverException e) {
            log.info("The toggle Floated is off");
        }
    }

    @Test(priority = 5, dependsOnMethods = {"openWebSiteAndLogin"})
    public void changeThemeOnRed() throws InterruptedException {
        Thread.sleep(2000);
        cms.web.clickElement("themeColorRed");
        Thread.sleep(1000);
        Assert.assertEquals(cms.mainPage.getColor(), "rgba(240, 80, 80, 1)");
    }

    @Test(priority = 6, dependsOnMethods = {"openWebSiteAndLogin"})
    public void changeThemeOnGreen() throws InterruptedException {
        Thread.sleep(2000);
        cms.web.clickElement("themeColorGreen");
        Thread.sleep(1000);
        Assert.assertEquals(cms.mainPage.getColor(), "rgba(43, 149, 122, 1)");
    }

    @Test(priority = 7, dependsOnMethods = {"openWebSiteAndLogin"})
    public void layoutBoxedOn() throws InterruptedException {
        cms.web.clickElement("toggleBoxed");
        Thread.sleep(1000);
        Assert.assertTrue(cms.web.isElementPresent("toggleBoxedOn"));
        cms.web.clickElement("toggleBoxed");
    }

    @Test(priority = 8, dependsOnMethods = {"openWebSiteAndLogin"})
    public void asideCollapsedOn() throws InterruptedException {
        cms.web.clickElement("toggleCollapsed");
        Thread.sleep(1000);
        Assert.assertTrue(cms.web.isElementPresent("toggleCollapsedOn"));
        cms.web.clickElement("toggleCollapsed");
    }

    @Test(priority = 9, dependsOnMethods = {"openWebSiteAndLogin"})
    public void asideFloatOn() throws InterruptedException {
        cms.web.clickElement("toggleFloated");
        Thread.sleep(1000);
        Assert.assertTrue(cms.web.isElementPresent("toggleFloatedOn"));
        cms.web.clickElement("toggleFloated");
    }

    @Test(priority = 10, dependsOnMethods = {"openWebSiteAndLogin"})
    public void checkingSaveSettings() throws InterruptedException {
        cms.web.clickElement("themeColorRed");
        cms.web.clickElement("toggleBoxed");
        cms.web.clickElement("toggleCollapsed");
        cms.web.clickElement("toggleFloated");
        Thread.sleep(2000);
        cms.mainPage.clickLogoutButton();
        cms.mainPage.checkingSaveSettingsInAnotherBrowser();
    }

}
