package pages;


import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
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

    public void clickOnUpperMenuItemTopLeftSide(String element){
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

    public void clearDataInField(String field, int num){
        web.clear(field+num);
    }

    public boolean isPopupAlertPresent(String alert, int num) {
        return web.isElementPresent(alert+num);
    }


}
