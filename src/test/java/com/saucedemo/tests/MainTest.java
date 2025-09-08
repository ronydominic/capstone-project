package com.saucedemo.tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.saucedemo.base.BaseTest;
import com.saucedemo.listeners.TestListener;
import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.FooterPage;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.MenuPage;
import com.saucedemo.pages.ProductsPage;
import com.saucedemo.utilities.ExcelUtilities;
import com.saucedemo.utilities.Screenshot;

@Listeners(TestListener.class)
public class MainTest extends BaseTest {

    LoginPage loginPage;
    ProductsPage productsPage;
    CartPage cartPage;
    MenuPage menuPage;
    FooterPage footerPage;

    static String projectPath = System.getProperty("user.dir");

    @BeforeMethod
    public void initPages() {
        loginPage = new LoginPage(driver);
        productsPage = new ProductsPage(driver);
        cartPage = new CartPage(driver);
        menuPage = new MenuPage(driver);
        footerPage = new FooterPage(driver);
    }

    @DataProvider
    public Object[][] loginData() throws IOException {
        String excelPath = projectPath + "\\src\\test\\resources\\Testdata\\login.xlsx";
        return ExcelUtilities.getData(excelPath, "Sheet1");
    }

    @Test(dataProvider = "loginData")
    public void fullEndToEndFlow(String username, String password) throws Exception {
        test = extent.createTest("SauceDemo End-to-End Flow");

        // ---------------- Login ----------------
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();

        if (productsPage.isCartVisible()) {
            String path = Screenshot.capture(driver, "LoginSuccess");
            test.pass("Login successful").addScreenCaptureFromPath(path);
        } else {
            String path = Screenshot.capture(driver, "LoginFail");
            test.fail("Login failed").addScreenCaptureFromPath(path);
            Assert.fail("Login failed");
        }

        // ---------------- Add Product ----------------
        productsPage.addProductToCartByIndex(0);
        if (productsPage.getCartCount() > 0) {
            String path = Screenshot.capture(driver, "AddProduct");
            test.pass("First product added to cart").addScreenCaptureFromPath(path);
        } else {
            String path = Screenshot.capture(driver, "AddProductFail");
            test.fail("Failed to add product").addScreenCaptureFromPath(path);
            Assert.fail("Failed to add product");
        }

        // ---------------- Cart Validation ----------------
        if (cartPage.getCartCount() > 0 && cartPage.isCheckoutVisible()) {
            String path = Screenshot.capture(driver, "CartCheck");
            test.pass("Cart contains items & checkout button visible").addScreenCaptureFromPath(path);
        } else {
            String path = Screenshot.capture(driver, "CartEmpty");
            test.fail("Cart empty or checkout button not visible").addScreenCaptureFromPath(path);
            Assert.fail("Cart validation failed");
        }

        // ---------------- Footer Validation ----------------
        if (footerPage.isFooterVisible() && footerPage.isTermsClickable() && footerPage.isPrivacyClickable()) {
            String path = Screenshot.capture(driver, "FooterVisible");
            test.pass("Footer & links visible and clickable").addScreenCaptureFromPath(path);
        } else {
            String path = Screenshot.capture(driver, "FooterFail");
            test.fail("Footer or links missing/not clickable").addScreenCaptureFromPath(path);
            Assert.fail("Footer validation failed");
        }

        // ---------------- Reset App State ----------------
        menuPage.openMenu();
        menuPage.clickResetAppState();
        Thread.sleep(1000);

        if (productsPage.getCartCount() == 0) {
            String path = Screenshot.capture(driver, "ResetAppSuccess");
            test.pass("Cart cleared successfully after reset").addScreenCaptureFromPath(path);
        } else {
            String path = Screenshot.capture(driver, "ResetAppFail");
            test.fail("Cart not cleared after reset").addScreenCaptureFromPath(path);
            Assert.fail("Cart not cleared after reset");
        }
    }
}
