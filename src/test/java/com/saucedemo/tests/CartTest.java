package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.ProductsPage;
import com.saucedemo.pages.CartPage;
import com.saucedemo.utilities.Screenshot;

import org.testng.annotations.Test;
import org.testng.Assert;

public class CartTest extends BaseTest {

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
    public void TC_CART_001_verifyCartEmptyInitially() {
        test = extent.createTest("TC_CART_001 - Verify cart is empty initially");
        try {
            performLogin();
            CartPage cp = new CartPage(driver);
            if (cp.getCartCount() == 0) test.pass("Cart is empty initially");
            else {
                String path = Screenshot.capture(driver, "TC_CART_001");
                test.fail("Cart is not empty. Count: " + cp.getCartCount()).addScreenCaptureFromPath(path);
                Assert.fail("Cart is not empty. Count: " + cp.getCartCount());
            }
        } catch (Exception e) {
            handleException(e, "TC_CART_001");
        }
    }

    @Test
    public void TC_CART_002_addSingleProductAndVerify() {
        test = extent.createTest("TC_CART_002 - Add single product to cart and verify count");
        try {
            performLogin();
            ProductsPage pp = new ProductsPage(driver);
            pp.addProductToCartByIndex(0);
            CartPage cp = new CartPage(driver);
            if (cp.getCartCount() == 1) test.pass("Single product added successfully");
            else {
                String path = Screenshot.capture(driver, "TC_CART_002");
                test.fail("Product not added correctly. Count: " + cp.getCartCount()).addScreenCaptureFromPath(path);
                Assert.fail("Product not added correctly. Count: " + cp.getCartCount());
            }
        } catch (Exception e) {
            handleException(e, "TC_CART_002");
        }
    }

    @Test
    public void TC_CART_003_addMultipleProductsAndVerify() {
        test = extent.createTest("TC_CART_003 - Add multiple products to cart and verify count");
        try {
            performLogin();
            ProductsPage pp = new ProductsPage(driver);
            pp.addAllProductsToCart();
            CartPage cp = new CartPage(driver);
            if (cp.getCartCount() == pp.getProductNames().size()) test.pass("All products added successfully");
            else {
                String path = Screenshot.capture(driver, "TC_CART_003");
                test.fail("Not all products added. Count: " + cp.getCartCount()).addScreenCaptureFromPath(path);
                Assert.fail("Not all products added. Count: " + cp.getCartCount());
            }
        } catch (Exception e) {
            handleException(e, "TC_CART_003");
        }
    }

    @Test
    public void TC_CART_004_removeSingleProduct() {
        test = extent.createTest("TC_CART_004 - Remove a product from cart");
        try {
            performLogin();
            ProductsPage pp = new ProductsPage(driver);
            pp.addAllProductsToCart();
            CartPage cp = new CartPage(driver);
            int initial = cp.getCartCount();
            cp.removeItemByIndex(0);
            if (cp.getCartCount() == initial - 1) test.pass("Product removed successfully");
            else {
                String path = Screenshot.capture(driver, "TC_CART_004");
                test.fail("Product not removed. Count: " + cp.getCartCount()).addScreenCaptureFromPath(path);
                Assert.fail("Product not removed. Count: " + cp.getCartCount());
            }
        } catch (Exception e) {
            handleException(e, "TC_CART_004");
        }
    }

    @Test
    public void TC_CART_005_removeAllProducts() {
        test = extent.createTest("TC_CART_005 - Remove all products from cart");
        try {
            performLogin();
            ProductsPage pp = new ProductsPage(driver);
            pp.addAllProductsToCart();
            CartPage cp = new CartPage(driver);
            cp.removeAllItems();
            if (cp.getCartCount() == 0) test.pass("All products removed successfully");
            else {
                String path = Screenshot.capture(driver, "TC_CART_005");
                test.fail("Cart not empty after removing all. Count: " + cp.getCartCount()).addScreenCaptureFromPath(path);
                Assert.fail("Cart not empty after removing all. Count: " + cp.getCartCount());
            }
        } catch (Exception e) {
            handleException(e, "TC_CART_005");
        }
    }

    @Test
    public void TC_CART_006_checkoutButtonVisibility() {
        test = extent.createTest("TC_CART_006 - Verify checkout button visibility");
        try {
            performLogin();
            ProductsPage pp = new ProductsPage(driver);
            pp.addProductToCartByIndex(0);
            CartPage cp = new CartPage(driver);
            if (cp.isCheckoutVisible()) test.pass("Checkout button is visible");
            else {
                String path = Screenshot.capture(driver, "TC_CART_006");
                test.fail("Checkout button not visible").addScreenCaptureFromPath(path);
                Assert.fail("Checkout button not visible");
            }
        } catch (Exception e) {
            handleException(e, "TC_CART_006");
        }
    }
}
