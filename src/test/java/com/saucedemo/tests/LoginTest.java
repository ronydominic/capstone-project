package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.ProductsPage;
import com.saucedemo.utilities.Screenshot;

import org.testng.annotations.Test;
import org.testng.Assert;

public class LoginTest extends BaseTest {

    @Test
    public void TC_LOGIN_001_validLogin() {
        test = extent.createTest("TC_LOGIN_001 - Valid Login");
        try {
            LoginPage lp = new LoginPage(driver);
            lp.enterUsername("standard_user");
            test.info("Entered username: standard_user");
            lp.enterPassword("secret_sauce");
            test.info("Entered password");
            lp.clickLogin();
            test.info("Clicked login");

            ProductsPage pp = new ProductsPage(driver);
            String title = pp.getTitle();
            if ("Products".equalsIgnoreCase(title)) {
                test.pass("Login success - landed on Products page");
            } else {
                String path = Screenshot.capture(driver, "TC_LOGIN_001");
                test.fail("Login failed - title mismatch: " + title).addScreenCaptureFromPath(path);
                Assert.fail("Login did not navigate to Products page. Title: " + title);
            }
        } catch (Exception e) {
            try {
                String path = Screenshot.capture(driver, "TC_LOGIN_001_exception");
                test.fail("Exception during login: " + e.getMessage()).addScreenCaptureFromPath(path);
            } catch (Exception ex) {
                test.fail("Exception and screenshot failed: " + ex.getMessage());
            }
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }

    @Test
    public void TC_LOGIN_002_lockedOutUser() {
        test = extent.createTest("TC_LOGIN_002 - Locked Out User");
        try {
            LoginPage lp = new LoginPage(driver);
            lp.enterUsername("locked_out_user");
            test.info("Entered username: locked_out_user");
            lp.enterPassword("secret_sauce");
            test.info("Entered password");
            lp.clickLogin();
            test.info("Clicked login");

            String err = lp.getError();
            if (err.contains("locked out")) {
                test.pass("Correct error message displayed for locked out user");
            } else {
                String path = Screenshot.capture(driver, "TC_LOGIN_002");
                test.fail("Unexpected error message: " + err).addScreenCaptureFromPath(path);
                Assert.fail("Unexpected error message: " + err);
            }
        } catch (Exception e) {
            try {
                String path = Screenshot.capture(driver, "TC_LOGIN_002_exception");
                test.fail("Exception during test: " + e.getMessage()).addScreenCaptureFromPath(path);
            } catch (Exception ex) {
                test.fail("Exception and screenshot failed: " + ex.getMessage());
            }
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }
}
