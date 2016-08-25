package pages;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import utils.WebDriverWrapper;

import java.util.List;

public class MainPage extends AbstractPage{

    public MainPage (WebDriverWrapper driverWrapper){
        super(driverWrapper);
    }
    public void clickLogoutButton() {
        web.clickElement("naviUser");
        web.waitElementToBeVisibility("logoutButton");
        web.clickButton("logoutButton");
    }

    /**
     * Click on item from navigation panel
     *
     * @param navItemNumber navigation item number, where
     *                      1 - Pages
     *                      2 - Banners
     *                      3 - Translations
     *                      4 - Users
     *                      5 - Locales
     *
     */
    public void clickOnNavigationItem(int navItemNumber) {
        List<WebElement> navItemList = web.getElements("navigationItemList");
        if (navItemNumber == 8 || navItemNumber == 9) {
            navItemList.get(6).click();
            web.waitElementToBeClickable(navItemList.get(navItemNumber - 1));
        } else {
            navItemList.get(navItemNumber - 1).click();
        }
    }

    public void clickOnUpperMenuItem(String element){
        web.clickElement(element);
    }

    public void searchDismiss(String element){
        web.clickElement(element);
    }
    /**
     * Get text from element with helping javascript executor
     *
     * @see {@link JavascriptExecutor} and {@link JavascriptExecutor#executeScript(String, Object...)}
     */
    public String jsGetText(String elementID){
        return web.getTextJS(elementID);
    }

    public boolean isButtonPresent(String button){
        return web.isElementPresent(button);
    }

    public void clearDataInField(String field, int num, String text){
        web.clearAndInput(field+num, text);
        web.clear(field+num);
    }

    public boolean isPopupAlertPresent(String alert, int num) {
        return web.isElementPresent(alert+num);
    }

    public String getColor() {
        return driverWrapper.findElement(By.cssSelector(".nav-wrapper")).getCssValue("background-color");
    }

    public void checkingSaveSettingsInAnotherBrowser() throws InterruptedException {
        WebDriver driverWrapper1 = new FirefoxDriver();
        driverWrapper1.get("http://development.toroption.araneum.office.dev/admin");
        driverWrapper1.manage().window().maximize();
        Thread.sleep(7000);
        driverWrapper1.findElement(By.xpath(".//div[@ng-controller='LoginController']//input[@name='username']")).sendKeys("admin");
        driverWrapper1.findElement(By.xpath(".//div[@ng-controller='LoginController']//input[@name='password']")).sendKeys("QDurWe67");
        driverWrapper1.findElement(By.xpath(".//button[contains(@class, 'btn')]")).click();
        Thread.sleep(5000);
        Assert.assertEquals(driverWrapper1.findElement(By.cssSelector("nav.navbar.topnavbar.ng-scope")).getCssValue("background-color"), "rgba(240, 80, 80, 1)");
        driverWrapper1.findElement(By.xpath(".//nav[@class='navbar topnavbar ng-scope']//a[@ng-click='app.offsidebarOpen = !app.offsidebarOpen']")).click();
        Assert.assertEquals(driverWrapper1.findElement(By.xpath(".//body")).getAttribute("class"), "layout-fixed layout-fs hidden-footer aside-collapsed layout-boxed aside-float offsidebar-open");
        driverWrapper1.findElement(By.xpath(".//nav[@class='ng-scope']//div[@class='p'][1]/div[2]/div[2]")).click();
        driverWrapper1.findElement(By.xpath(".//div[@class='ng-scope']/div[2]//div[@class='pull-right']")).click();
        driverWrapper1.findElement(By.xpath(".//div[@class='ng-scope']/div[3]/div[1]/div[@class='pull-right']")).click();
        driverWrapper1.findElement(By.xpath(".//div[@class='ng-scope']/div[3]/div[2]/div[@class='pull-right']")).click();
        driverWrapper1.quit();
    }
}
