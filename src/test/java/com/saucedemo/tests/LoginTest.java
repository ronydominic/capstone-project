package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.ProductsPage;
import com.saucedemo.utilities.Screenshot;

import org.testng.annotations.Test;
import org.testng.Assert;

public class LoginTest extends BaseTest {

    // Utility method to perform login
    private void performLogin(String username, String password) {
        LoginPage lp = new LoginPage(driver);
        lp.enterUsername(username);
        test.info("Entered username: " + username);
        lp.enterPassword(password);
        test.info("Entered password");
        lp.clickLogin();
        test.info("Clicked login");
    }

    // Utility method to handle exceptions and capture screenshots
    private void handleException(Exception e, String testCase) {
        try {
            String path = Screenshot.capture(driver, testCase + "_exception");
            test.fail("Exception occurred: " + e.getMessage())
                .addScreenCaptureFromPath(path);
        } catch (Exception ex) {
            test.fail("Exception while capturing screenshot: " + ex.getMessage());
        }
        Assert.fail("Test failed due to exception: " + e.getMessage());
    }

    @Test
    public void TC_LOGIN_001_validLogin() {
        test = extent.createTest("TC_LOGIN_001 - Valid Login");
        try {
            performLogin("standard_user", "secret_sauce");
            ProductsPage pp = new ProductsPage(driver);
            String title = pp.getTitle();
            if ("Products".equalsIgnoreCase(title)) {
                test.pass("Login successful - landed on Products page");
            } else {
                String path = Screenshot.capture(driver, "TC_LOGIN_001");
                test.fail("Login failed - title mismatch: " + title)
                    .addScreenCaptureFromPath(path);
                Assert.fail("Login did not navigate to Products page. Title: " + title);
            }
        } catch (Exception e) {
            handleException(e, "TC_LOGIN_001");
        }
    }

    @Test
    public void TC_LOGIN_002_lockedOutUser() {
        test = extent.createTest("TC_LOGIN_002 - Locked Out User");
        try {
            performLogin("locked_out_user", "secret_sauce");
            LoginPage lp = new LoginPage(driver);
            String err = lp.getError();
            if (err.toLowerCase().contains("locked out")) {
                test.pass("Correct error message displayed for locked out user");
            } else {
                String path = Screenshot.capture(driver, "TC_LOGIN_002");
                test.fail("Unexpected error message: " + err)
                    .addScreenCaptureFromPath(path);
                Assert.fail("Unexpected error message: " + err);
            }
        } catch (Exception e) {
            handleException(e, "TC_LOGIN_002");
        }
    }

    @Test
    public void TC_LOGIN_003_emptyUsername() {
        test = extent.createTest("TC_LOGIN_003 - Empty Username");
        try {
            performLogin("", "secret_sauce");
            LoginPage lp = new LoginPage(driver);
            String err = lp.getError();
            if (err.toLowerCase().contains("username is required")) {
                test.pass("Correct error message displayed for empty username");
            } else {
                String path = Screenshot.capture(driver, "TC_LOGIN_003");
                test.fail("Unexpected error message: " + err)
                    .addScreenCaptureFromPath(path);
                Assert.fail("Unexpected error message for empty username: " + err);
            }
        } catch (Exception e) {
            handleException(e, "TC_LOGIN_003");
        }
    }

    @Test
    public void TC_LOGIN_004_emptyPassword() {
        test = extent.createTest("TC_LOGIN_004 - Empty Password");
        try {
            performLogin("standard_user", "");
            LoginPage lp = new LoginPage(driver);
            String err = lp.getError();
            if (err.toLowerCase().contains("password is required")) {
                test.pass("Correct error message displayed for empty password");
            } else {
                String path = Screenshot.capture(driver, "TC_LOGIN_004");
                test.fail("Unexpected error message: " + err)
                    .addScreenCaptureFromPath(path);
                Assert.fail("Unexpected error message for empty password: " + err);
            }
        } catch (Exception e) {
            handleException(e, "TC_LOGIN_004");
        }
    }

    @Test
    public void TC_LOGIN_005_invalidCredentials() {
        test = extent.createTest("TC_LOGIN_005 - Invalid Credentials");
        try {
            performLogin("invalid_user", "wrong_pass");
            LoginPage lp = new LoginPage(driver);
            String err = lp.getError();
            if (err.toLowerCase().contains("username and password do not match")) {
                test.pass("Correct error message displayed for invalid credentials");
            } else {
                String path = Screenshot.capture(driver, "TC_LOGIN_005");
                test.fail("Unexpected error message: " + err)
                    .addScreenCaptureFromPath(path);
                Assert.fail("Unexpected error message for invalid credentials: " + err);
            }
        } catch (Exception e) {
            handleException(e, "TC_LOGIN_005");
        }
    }
}
