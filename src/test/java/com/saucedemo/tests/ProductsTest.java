package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.ProductsPage;
import com.saucedemo.utilities.Screenshot;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ProductsTest extends BaseTest {

    @Test
    public void TC_PRODUCTS_001_verifyProductPage() {
        test = extent.createTest("TC_PRODUCTS_001 - Verify Products Page");
        try {
            LoginPage lp = new LoginPage(driver);
            lp.enterUsername("standard_user");
            lp.enterPassword("secret_sauce");
            lp.clickLogin();

            ProductsPage pp = new ProductsPage(driver);
            if (pp.isProductListDisplayed()) {
                test.pass("Products list displayed successfully");
            } else {
                String path = Screenshot.capture(driver, "TC_PRODUCTS_001");
                test.fail("Products list not displayed").addScreenCaptureFromPath(path);
                Assert.fail("Products list not visible on Products Page");
            }
        } catch (Exception e) {
            try {
                String path = Screenshot.capture(driver, "TC_PRODUCTS_001_exception");
                test.fail("Exception: " + e.getMessage()).addScreenCaptureFromPath(path);
            } catch (Exception ex) {
                test.fail("Exception and screenshot failed: " + ex.getMessage());
            }
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }
}
