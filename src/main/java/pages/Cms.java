package pages;

import utils.WebDriverWrapper;
import utils.WebElementsActions;

public class Cms {

    public WebElementsActions web;
    public LoginPage loginPage;

    public Cms(WebDriverWrapper driverWrapper) {
        web = new WebElementsActions(driverWrapper);
        loginPage = new LoginPage(driverWrapper);
    }

}