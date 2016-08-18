package tests.UpperMenu;

import org.testng.Assert;
import org.testng.annotations.Test;
import tests.Fixture;
import utils.PropertyLoader;

import java.util.concurrent.TimeUnit;

public class LeftSide extends Fixture {

    private static final String ADMIN_NAME = PropertyLoader.loadProperty("admin.name");
    private static final String ADMIN_PASSWORD = PropertyLoader.loadProperty("admin.password");
    private static final int newImpWait = 300;

    @Test(priority = 1)
    public void openWebSiteAndLogin() {
        cms.loginPage.openLoginPage();
        cms.loginPage.waitInvisibilityLoader();
        impWait = String.valueOf(newImpWait);
        driverWrapper.manage().timeouts().implicitlyWait(Long.parseLong(impWait), TimeUnit.MILLISECONDS);
        cms.loginPage.inputUserName(ADMIN_NAME);
        cms.loginPage.inputPassword(ADMIN_PASSWORD);
        cms.loginPage.clickLoginButton();
        Assert.assertEquals(driverWrapper.getTitle(), "Admin :: Araneum - Multisite manage tool");
    }

    @Test(priority = 2, dependsOnMethods = {"openWebSiteAndLogin"})
    public void clickOnHamburgerIcon() throws InterruptedException {
        Thread.sleep(4000);
//        cms.mainPage.clickOnUpperMenuItem("naviHamburger");
//        Thread.sleep(2000);
        Assert.assertEquals(cms.web.getTextFromElement("siteManager"), "Site manager");
    }

    @Test(priority = 3, dependsOnMethods = {"openWebSiteAndLogin"})
    public void clickOnHamburgerIconAgain() throws InterruptedException {
        cms.mainPage.clickOnUpperMenuItem("naviHamburger");
        Thread.sleep(2000);
        Assert.assertEquals(cms.web.getTextFromElement("siteManager"), "");
    }

    @Test(priority = 4, dependsOnMethods = {"openWebSiteAndLogin"})
    public void clickOnUserIcon() throws InterruptedException {
        cms.mainPage.clickOnUpperMenuItem("naviUser");
        cms.mainPage.clickOnUpperMenuItem("naviHamburger");
        Thread.sleep(2000);
        Assert.assertEquals(cms.web.getTextFromElement("welcomeMessage"), "Welcome AdminFirstName AdminLastName");
        Assert.assertEquals(cms.web.getTextFromElement("emailUserBlock"), "araneum.dev.site@gmail.com");
    }

    @Test(priority = 5, dependsOnMethods = {"openWebSiteAndLogin"})
    public void clickOnProfilePicture() throws InterruptedException {
        cms.mainPage.clickOnUpperMenuItem("profilePicture");
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
        cms.mainPage.clickOnUpperMenuItem("profilePicture");
        Thread.sleep(2000);
        for (int i=1;i<5;i++){
            cms.mainPage.clearDataInField("modalWinField", i, "!");
            Assert.assertTrue(cms.mainPage.isPopupAlertPresent("modalWinPopupAlert", i));
        }
        Assert.assertFalse(cms.web.isButtonEnable("modalWinSaveBtn"));
        cms.web.clickButton("modalWinCancelBtn");
    }

    @Test(priority = 7, dependsOnMethods = {"openWebSiteAndLogin"})
    public void shortUserName() throws InterruptedException {
        Thread.sleep(2000);
        cms.mainPage.clickOnUpperMenuItem("profilePicture");
        Thread.sleep(2000);
        cms.web.clearAndInput("modalWinField1", "T");
        Assert.assertEquals(cms.web.getTextFromElement("modalWinPopupAlert1a"), "Username is too short");
    }

    @Test(priority = 8, dependsOnMethods = {"openWebSiteAndLogin"})
    public void validUserName() {
        cms.web.clearAndInput("modalWinField1", "admin");
        Assert.assertFalse(cms.mainPage.isPopupAlertPresent("modalWinPopupAlert", 1));
    }

    @Test(priority = 9, dependsOnMethods = {"openWebSiteAndLogin"})
    public void invalidFirstLastName() {
        for (int i=2;i<4;i++){
            cms.web.clearAndInput("modalWinField"+i, "!@#$%");
            Assert.assertEquals(cms.web.getTextFromElement("modalWinPopupAlert"+i), "Shouldn't be blank and only letters are allowed");
        }
    }

    @Test(priority = 10, dependsOnMethods = {"openWebSiteAndLogin"})
    public void invalidEmailSpecialSymbols() {
        cms.web.clearAndInput("modalWinField4", "1nval!d@#ma$.d*%");
        Assert.assertEquals(cms.web.getTextFromElement("modalWinPopupAlert4a"), "Not valid email");
    }

    @Test(priority = 11, dependsOnMethods = {"openWebSiteAndLogin"})
    public void invalidEmailWithOutAtCommercial() {
        cms.web.clearAndInput("modalWinField4", "invalid.email.yahoo.com");
        Assert.assertEquals(cms.web.getTextFromElement("modalWinPopupAlert4a"), "Not valid email");
    }

    @Test(priority = 12, dependsOnMethods = {"openWebSiteAndLogin"})
    public void invalidEmailWithOutDomain() {
        cms.web.clearAndInput("modalWinField4", "invalid@email");
        Assert.assertEquals(cms.web.getTextFromElement("modalWinPopupAlert4a"), "Not valid email");
    }

    @Test(priority = 13, dependsOnMethods = {"openWebSiteAndLogin"})
    public void validEmail() {
        cms.web.clearAndInput("modalWinField4", "validemail@yahoo.com");
        Assert.assertFalse(cms.mainPage.isPopupAlertPresent("modalWinPopupAlert", 4));
    }

    @Test(priority = 14, dependsOnMethods = {"openWebSiteAndLogin"})
    public void clickCancel() {
        cms.web.clickButton("modalWinCancelBtn");
        Assert.assertTrue(cms.web.isElementPresent("profilePicture"));
    }
    @Test(priority = 15, dependsOnMethods = {"openWebSiteAndLogin"})
    public void changeFirsLastName() throws InterruptedException {
        Thread.sleep(2000);
        cms.mainPage.clickOnUpperMenuItem("profilePicture");
        Thread.sleep(2000);
        cms.web.clearAndInput("modalWinField2", "changedName");
        cms.web.clearAndInput("modalWinField3", "changedSurname");
        cms.web.clickButton("modalWinSaveBtn");
        Thread.sleep(4000);
        cms.mainPage.clickOnUpperMenuItem("profilePicture");
        Thread.sleep(2000);
        Assert.assertEquals(cms.mainPage.jsGetText("firstName"), "changedName");
        Assert.assertEquals(cms.mainPage.jsGetText("lastName"), "changedSurname");
        cms.web.clearAndInput("modalWinField2", "AdminFirstName");
        cms.web.clearAndInput("modalWinField3", "AdminLastName");
        cms.web.clickButton("modalWinSaveBtn");
    }

    @Test(priority = 16, dependsOnMethods = {"openWebSiteAndLogin"})
    public void changeLanguage() throws InterruptedException {
        Thread.sleep(4000);
        cms.web.clickElement("naviLanguage");
        cms.web.clickElement("naviLanguageRu");
        Thread.sleep(1500);
        Assert.assertEquals(cms.web.getTextFromElement("welcomeMessage"), "Добро пожаловать AdminFirstName AdminLastName");
        cms.web.clickElement("naviLanguage");
        cms.web.clickElement("naviLanguageEn");
        Thread.sleep(1500);
        Assert.assertEquals(cms.web.getTextFromElement("welcomeMessage"), "Welcome AdminFirstName AdminLastName");
    }
}
