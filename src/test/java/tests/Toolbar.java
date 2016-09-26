package tests;


import org.openqa.selenium.WebDriverException;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.PropertyLoader;

import java.util.concurrent.TimeUnit;

public class Toolbar extends Fixture{
    private static final String ADMIN_NAME = PropertyLoader.loadProperty("admin.name");
    private static final String ADMIN_PASSWORD = PropertyLoader.loadProperty("admin.password");
    private static final int newImpWait = 300;

    @Test(priority = 1)
    public void openWebSiteAndLogin() throws InterruptedException {
        cms.loginPage.openLoginPage();
        try {
            cms.loginPage.waitInvisibilityLoader();
        } catch (WebDriverException e) {
            e.printStackTrace();
            Thread.sleep(10000);
        }
        impWait = String.valueOf(newImpWait);
        driverWrapper.manage().timeouts().implicitlyWait(Long.parseLong(impWait), TimeUnit.MILLISECONDS);
        cms.loginPage.inputUserName(ADMIN_NAME);
        cms.loginPage.inputPassword(ADMIN_PASSWORD);
        cms.loginPage.clickLoginButton();
        Assert.assertEquals(driverWrapper.getTitle(), "Admin :: Araneum - Multisite manage tool");
    }

    @Test(priority = 2)
    public void searchPage(){
        cms.web.clear("searchPageField");
        cms.web.input("searchPageField", "accounts");
        Assert.assertTrue(cms.web.isElementPresent("findingElement"));
    }

    @Test(priority = 3)
    public void searchPageAtPartWord() {
        cms.web.clear("searchPageField");
        cms.web.input("searchPageField", "glos");
        Assert.assertTrue(cms.web.isElementPresent("findingElement2"));
        Assert.assertTrue(cms.web.isElementPresent("findingElement3"));
    }

    @Test(priority = 4)
    public void reloadIcon() {
        cms.web.clear("searchPageField");
        cms.web.input("searchPageField", "legal");
        cms.web.clickElement("reloadIcon");
        Assert.assertTrue(cms.web.isElementPresent("findingElement2"));
        cms.web.clear("searchPageField");
    }

    @Test(priority = 5)
    public void reloadIcon_2() throws InterruptedException {
        Thread.sleep(3000);
        cms.web.clickElement("findingElement4");
        Thread.sleep(3000);
        cms.web.clickElement("findingElement2");
        Thread.sleep(3000);
        cms.web.clickElement("findingElement3");
        cms.web.clickElement("reloadIcon");
        Thread.sleep(5000);
        Assert.assertTrue(cms.web.isElementPresent("findingElement3"));
    }

    @Test(priority = 6)
    public void expand() {
        cms.web.clickElement("expandCollapseAll");
        Assert.assertTrue(cms.web.isElementPresent("collapseAll"));

    }

    @Test(priority = 7)
    public void collapse() {
        cms.web.clickElement("expandCollapseAll");
        Assert.assertTrue(cms.web.isElementPresent("expandAll"));
    }

    @Test(priority = 8)
    public void addNewPage() throws InterruptedException {
        cms.web.clickElement("findingElement");
        Thread.sleep(5000);
        cms.web.clickButton("addNewPage");
        Thread.sleep(2000);
        cms.web.clearAndInput("pageNameField", "NewTestPage");
        cms.web.clickElement("templateDropMenu");
        cms.web.clickElement("templateAccTypes");
    }

}
