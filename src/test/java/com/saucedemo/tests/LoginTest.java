package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.ProductsPage;
import com.saucedemo.utilities.Screenshot;
import com.saucedemo.utilities.ExcelUtilities;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.Assert;

public class LoginTest extends BaseTest {

    private static final String EXCEL_PATH = "src/test/resources/LoginData.xlsx";

    // DataProvider using your ExcelUtilities
    @DataProvider(name = "loginData")
    public Object[][] getLoginData() throws Exception {
        return ExcelUtilities.getData(EXCEL_PATH, "Sheet1");
    }

    private void performLogin(String username, String password) {
        LoginPage lp = new LoginPage(driver);
        lp.enterUsername(username);
        test.info("Entered username: " + username);
        lp.enterPassword(password);
        test.info("Entered password");
        lp.clickLogin();
        test.info("Clicked login");
    }

    private void handleException(Exception e, String testCase) {
        try {
            String path = Screenshot.capture(driver, testCase + "_exception");
            test.fail("Exception occurred: " + e.getMessage()).addScreenCaptureFromPath(path);
        } catch (Exception ex) {
            test.fail("Exception while capturing screenshot: " + ex.getMessage());
        }
        Assert.fail("Test failed due to exception: " + e.getMessage());
    }

    @Test(dataProvider = "loginData")
    public void loginTests(String username, String password, String type) {
        test = extent.createTest("Login Test - " + type);
        try {
            performLogin(username, password);
            LoginPage lp = new LoginPage(driver);

            switch (type.toLowerCase()) {
                case "valid":
                    ProductsPage pp = new ProductsPage(driver);
                    String title = pp.getTitle();
                    if ("Products".equalsIgnoreCase(title)) test.pass("Valid login successful");
                    else {
                        String path = Screenshot.capture(driver, "Login_" + type);
                        test.fail("Login failed - title mismatch: " + title)
                            .addScreenCaptureFromPath(path);
                        Assert.fail("Login failed - title mismatch: " + title);
                    }
                    break;
                case "locked":
                    String err1 = lp.getError();
                    Assert.assertTrue(err1.toLowerCase().contains("locked out"));
                    test.pass("Locked out user shows correct error");
                    break;
                case "empty_username":
                    String err2 = lp.getError();
                    Assert.assertTrue(err2.toLowerCase().contains("username is required"));
                    test.pass("Empty username shows correct error");
                    break;
                case "empty_password":
                    String err3 = lp.getError();
                    Assert.assertTrue(err3.toLowerCase().contains("password is required"));
                    test.pass("Empty password shows correct error");
                    break;
                case "invalid":
                    String err4 = lp.getError();
                    Assert.assertTrue(err4.toLowerCase().contains("username and password"));
                    test.pass("Invalid credentials show correct error");
                    break;
                default:
                    test.warning("Unknown test type: " + type);
            }
        } catch (Exception e) {
            handleException(e, "Login_" + type);
        }
    }
}
