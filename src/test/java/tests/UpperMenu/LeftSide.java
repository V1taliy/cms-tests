package tests.UpperMenu;

import org.testng.Assert;
import org.testng.annotations.Test;
import tests.Fixture;
import utils.PropertyLoader;

import java.util.concurrent.TimeUnit;

public class LeftSide extends Fixture {

    private static final String ADMIN_NAME = PropertyLoader.loadProperty("admin.name");
    private static final String ADMIN_PASSWORD = PropertyLoader.loadProperty("admin.password");

    @Test(priority = 1)
    public void openWebSiteAndLogin() {
        cms.loginPage.openLoginPage();
        cms.loginPage.waitInvisibilityLoader();
        cms.loginPage.inputUserName(ADMIN_NAME);
        cms.loginPage.inputPassword(ADMIN_PASSWORD);
        cms.loginPage.clickLoginButton();
        Assert.assertEquals(driverWrapper.getTitle(), "Admin :: Araneum - Multisite manage tool");
        driverWrapper.manage().timeouts().implicitlyWait(Long.parseLong(impWait), TimeUnit.MILLISECONDS);
    }

    @Test(priority = 2, dependsOnMethods = {"openWebSiteAndLogin"})
    public void clickOnHamburgerIcon() {
        cms.mainPage.clickOnUpperMenuItemTopLeftSide("naviHamburger");
        Assert.assertEquals(cms.loginPage.getTextFromElement("siteManager"), "Site manager");
    }

    @Test(priority = 3, dependsOnMethods = {"openWebSiteAndLogin"})
    public void clickOnHamburgerIconAgain() {
        cms.mainPage.clickOnUpperMenuItemTopLeftSide("naviHamburger");
        Assert.assertEquals(cms.loginPage.getTextFromElement("siteManager"), "");
    }

    @Test(priority = 4, dependsOnMethods = {"openWebSiteAndLogin"})
    public void clickOnUserIcon() throws InterruptedException {
        cms.mainPage.clickOnUpperMenuItemTopLeftSide("naviHamburger");
        cms.mainPage.clickOnUpperMenuItemTopLeftSide("naviUser");
        Thread.sleep(1000);
        Assert.assertEquals(cms.loginPage.getTextFromElement("welcomeMessage"), "Welcome AdminFirstName AdminLastName");
        Assert.assertEquals(cms.loginPage.getTextFromElement("emailUserBlock"), "araneum.dev.site@gmail.com");
    }

    @Test(priority = 5, dependsOnMethods = {"openWebSiteAndLogin"})
    public void clickOnProfilePicture() throws InterruptedException {
        cms.mainPage.clickOnUpperMenuItemTopLeftSide("profilePicture");
        Thread.sleep(2000);
        Assert.assertEquals(cms.mainPage.jsGetText("username"), "admin");
        Assert.assertEquals(cms.mainPage.jsGetText("firstName"), "AdminFirstName");
        Assert.assertEquals(cms.mainPage.jsGetText("lastName"), "AdminLastName");
        Assert.assertEquals(cms.mainPage.jsGetText("email"), "araneum.dev.site@gmail.com");
        Assert.assertTrue(cms.mainPage.isButtonPresent("modalWinSaveBtn"));
        Assert.assertTrue(cms.mainPage.isButtonPresent("modalWinCancelBtn"));
        cms.web.clickButton("modalWinCancelBtn");
    }

    @Test(priority = 6, dependsOnMethods = {"openWebSiteAndLogin"})
    public void clearDataInModalWindow() throws InterruptedException {
        Thread.sleep(2000);
        cms.mainPage.clickOnUpperMenuItemTopLeftSide("profilePicture");
        Thread.sleep(2000);
        for (int i=1;i<5;i++){
            cms.mainPage.clearDataInField("modalWinField", i);
            Assert.assertTrue(cms.mainPage.isPopupAlertPresent("modalWinPopupAlert", i));
        }
        Assert.assertFalse(cms.web.isButtonEnable("modalWinSaveBtn"));
    }
}
