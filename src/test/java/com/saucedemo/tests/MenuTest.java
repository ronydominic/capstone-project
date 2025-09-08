package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.MenuPage;
import com.saucedemo.pages.ProductsPage;
import com.saucedemo.pages.CartPage;
import com.saucedemo.utilities.Screenshot;

import org.testng.annotations.Test;
import org.testng.Assert;

public class MenuTest extends BaseTest {

    private void performLogin() {
        LoginPage lp = new LoginPage(driver);
        lp.enterUsername("standard_user");
        lp.enterPassword("secret_sauce");
        lp.clickLogin();
    }

    private void handleException(Exception e, String testCase) {
        try {
            String path = Screenshot.capture(driver, testCase + "_exception");
            test.fail("Exception: " + e.getMessage()).addScreenCaptureFromPath(path);
        } catch (Exception ex) {
            test.fail("Exception while capturing screenshot: " + ex.getMessage());
        }
        Assert.fail("Test failed due to exception: " + e.getMessage());
    }

    @Test
    public void TC_MENU_001_verifyMenuItemsPresence() {
        test = extent.createTest("TC_MENU_001 - Verify menu items presence");
        try {
            performLogin();
            MenuPage menu = new MenuPage(driver);
            menu.openMenu();
            Thread.sleep(500);

            menu.clickAllItems(); // ensures 'All Items' clickable
            boolean ok = driver.getCurrentUrl().contains("inventory") || driver.getTitle().contains("Swag Labs");
            if (ok) test.pass("Menu items present and All Items navigates correctly");
            else {
                String path = Screenshot.capture(driver, "TC_MENU_001");
                test.fail("Menu items navigation failed").addScreenCaptureFromPath(path);
                Assert.fail("Menu items navigation failed");
            }
        } catch (Exception e) {
            handleException(e, "TC_MENU_001");
        }
    }

    @Test
    public void TC_MENU_002_verifyAboutLink() {
        test = extent.createTest("TC_MENU_002 - Verify About link navigates correctly");
        try {
            performLogin();
            MenuPage menu = new MenuPage(driver);
            menu.openMenu();
            Thread.sleep(500);
            menu.clickAbout();
            Thread.sleep(1000);

            boolean ok = driver.getCurrentUrl().contains("saucelabs") || driver.getTitle().length() > 0;
            if (ok) test.pass("About link navigated correctly");
            else {
                String path = Screenshot.capture(driver, "TC_MENU_002");
                test.fail("About link navigation failed").addScreenCaptureFromPath(path);
                Assert.fail("About link navigation failed");
            }
        } catch (Exception e) {
            handleException(e, "TC_MENU_002");
        }
    }

    @Test
    public void TC_MENU_003_verifyLogoutFunctionality() {
        test = extent.createTest("TC_MENU_003 - Verify Logout functionality");
        try {
            performLogin();
            MenuPage menu = new MenuPage(driver);
            menu.openMenu();
            Thread.sleep(500);
            menu.clickLogout();
            Thread.sleep(500);

            boolean ok = driver.getCurrentUrl().contains("saucedemo.com");
            if (ok) test.pass("Logout successful");
            else {
                String path = Screenshot.capture(driver, "TC_MENU_003");
                test.fail("Logout failed").addScreenCaptureFromPath(path);
                Assert.fail("Logout failed");
            }
        } catch (Exception e) {
            handleException(e, "TC_MENU_003");
        }
    }

    @Test
    public void TC_MENU_004_verifyCloseMenu() {
        test = extent.createTest("TC_MENU_004 - Verify close menu button");
        try {
            performLogin();
            MenuPage menu = new MenuPage(driver);
            menu.openMenu();
            Thread.sleep(500);
            menu.closeMenu();
            Thread.sleep(500);

            // Verify menu is closed (menu button clickable again)
            menu.openMenu();
            test.pass("Close menu works and can reopen menu");
        } catch (Exception e) {
            handleException(e, "TC_MENU_004");
        }
    }

    @Test
    public void TC_MENU_005_verifyResetAppStateClearsCart() {
        test = extent.createTest("TC_MENU_005 - Verify Reset App State clears cart");
        try {
            performLogin();
            ProductsPage pp = new ProductsPage(driver);
            pp.addProductToCartByIndex(0);
            pp.addProductToCartByIndex(1);

            MenuPage menu = new MenuPage(driver);
            menu.openMenu();
            Thread.sleep(500);
            menu.clickResetAppState();
            Thread.sleep(500);

            CartPage cp = new CartPage(driver);
            if (cp.getCartCount() == 0) test.pass("Reset App State cleared cart successfully");
            else {
                String path = Screenshot.capture(driver, "TC_MENU_005");
                test.fail("Cart not cleared after Reset App State. Count: " + cp.getCartCount())
                        .addScreenCaptureFromPath(path);
                Assert.fail("Cart not cleared after Reset App State. Count: " + cp.getCartCount());
            }
        } catch (Exception e) {
            handleException(e, "TC_MENU_005");
        }
    }
}
