package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTests extends Fixture {

    @Test(priority = 1)
    public void openWebSite() {
        cms.loginPage.openLoginPage();
        cms.loginPage.waitInvisibilityLoader();
        Assert.assertTrue(cms.loginPage.isLoginFormPresent());
    }

}