package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.ProductsPage;
import com.saucedemo.utilities.Screenshot;

import org.testng.annotations.Test;
import org.testng.Assert;
import org.openqa.selenium.WebElement;
import java.util.List;

public class ProductsTest extends BaseTest {

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
            test.fail("Exception: " + e.getMessage()).addScreenCaptureFromPath(path);
        } catch (Exception ex) {
            test.fail("Exception while capturing screenshot: " + ex.getMessage());
        }
        Assert.fail("Test failed due to exception: " + e.getMessage());
    }

    @Test
    public void TC_PRODUCT_001_productsDisplayed() {
        test = extent.createTest("TC_PRODUCT_001 - Verify products are displayed");
        try {
            performLogin("standard_user", "secret_sauce");
            ProductsPage pp = new ProductsPage(driver);
            List<WebElement> products = pp.getProductNames();
            if (!products.isEmpty()) test.pass("Products displayed: count = " + products.size());
            else {
                String path = Screenshot.capture(driver, "TC_PRODUCT_001");
                test.fail("No products displayed").addScreenCaptureFromPath(path);
                Assert.fail("No products displayed");
            }
        } catch (Exception e) {
            handleException(e, "TC_PRODUCT_001");
        }
    }

    @Test
    public void TC_PRODUCT_002_sortByNameAZ() {
        test = extent.createTest("TC_PRODUCT_002 - Sort products by Name (A-Z)");
        try {
            performLogin("standard_user", "secret_sauce");
            ProductsPage pp = new ProductsPage(driver);
            pp.selectSortOption("Name (A to Z)");
            Thread.sleep(800);

            // Verify A-Z order
            List<WebElement> products = pp.getProductNames();
            boolean sorted = true;
            for (int i = 1; i < products.size(); i++) {
                if (products.get(i - 1).getText().compareToIgnoreCase(products.get(i).getText()) > 0) {
                    sorted = false;
                    break;
                }
            }
            if (sorted) test.pass("Products sorted A-Z correctly");
            else {
                String path = Screenshot.capture(driver, "TC_PRODUCT_002");
                test.fail("Products not sorted A-Z").addScreenCaptureFromPath(path);
                Assert.fail("Products not sorted A-Z");
            }
        } catch (Exception e) {
            handleException(e, "TC_PRODUCT_002");
        }
    }

    @Test
    public void TC_PRODUCT_003_sortByPriceLowToHigh() {
        test = extent.createTest("TC_PRODUCT_003 - Sort products by Price (Low → High)");
        try {
            performLogin("standard_user", "secret_sauce");
            ProductsPage pp = new ProductsPage(driver);
            pp.selectSortOption("Price (low to high)");
            Thread.sleep(800);

            List<Double> prices = pp.getProductPrices();
            boolean sorted = true;
            for (int i = 1; i < prices.size(); i++) {
                if (prices.get(i - 1) > prices.get(i)) {
                    sorted = false;
                    break;
                }
            }
            if (sorted) test.pass("Products sorted by price Low→High correctly");
            else {
                String path = Screenshot.capture(driver, "TC_PRODUCT_003");
                test.fail("Products not sorted by price Low→High").addScreenCaptureFromPath(path);
                Assert.fail("Products not sorted by price Low→High");
            }
        } catch (Exception e) {
            handleException(e, "TC_PRODUCT_003");
        }
    }

    @Test
    public void TC_PRODUCT_004_clickFirstProduct() {
        test = extent.createTest("TC_PRODUCT_004 - Click first product and verify details");
        try {
            performLogin("standard_user", "secret_sauce");
            ProductsPage pp = new ProductsPage(driver);
            String firstName = pp.getProductNames().get(0).getText();
            pp.clickFirstProduct();
            Thread.sleep(800);

            String detailTitle = pp.getProductDetailTitle();
            if (detailTitle.contains(firstName)) test.pass("Product details page opened and title matches");
            else {
                String path = Screenshot.capture(driver, "TC_PRODUCT_004");
                test.fail("Product title mismatch. Detail: " + detailTitle).addScreenCaptureFromPath(path);
                Assert.fail("Product title mismatch. Detail: " + detailTitle);
            }
        } catch (Exception e) {
            handleException(e, "TC_PRODUCT_004");
        }
    }

    @Test
    public void TC_PRODUCT_005_cartVisibility() {
        test = extent.createTest("TC_PRODUCT_005 - Verify cart icon visibility");
        try {
            performLogin("standard_user", "secret_sauce");
            ProductsPage pp = new ProductsPage(driver);
            if (pp.isCartVisible()) test.pass("Cart icon is visible");
            else {
                String path = Screenshot.capture(driver, "TC_PRODUCT_005");
                test.fail("Cart icon not visible").addScreenCaptureFromPath(path);
                Assert.fail("Cart icon not visible");
            }
        } catch (Exception e) {
            handleException(e, "TC_PRODUCT_005");
        }
    }

    @Test
    public void TC_PRODUCT_006_addProductToCartByIndex() {
        test = extent.createTest("TC_PRODUCT_006 - Add first product to cart");
        try {
            performLogin("standard_user", "secret_sauce");
            ProductsPage pp = new ProductsPage(driver);
            pp.addProductToCartByIndex(0);
            if (pp.getCartCount() == 1) test.pass("First product added to cart successfully");
            else {
                String path = Screenshot.capture(driver, "TC_PRODUCT_006");
                test.fail("Product not added to cart. Count: " + pp.getCartCount()).addScreenCaptureFromPath(path);
                Assert.fail("Product not added to cart. Count: " + pp.getCartCount());
            }
        } catch (Exception e) {
            handleException(e, "TC_PRODUCT_006");
        }
    }

    @Test
    public void TC_PRODUCT_007_addAllProductsToCart() {
        test = extent.createTest("TC_PRODUCT_007 - Add all products to cart");
        try {
            performLogin("standard_user", "secret_sauce");
            ProductsPage pp = new ProductsPage(driver);
            pp.addAllProductsToCart();
            int cartCount = pp.getCartCount();
            if (cartCount == pp.getProductNames().size()) test.pass("All products added to cart successfully");
            else {
                String path = Screenshot.capture(driver, "TC_PRODUCT_007");
                test.fail("Not all products added. Cart count: " + cartCount).addScreenCaptureFromPath(path);
                Assert.fail("Not all products added. Cart count: " + cartCount);
            }
        } catch (Exception e) {
            handleException(e, "TC_PRODUCT_007");
        }
    }

    @Test
    public void TC_PRODUCT_008_verifyImages() {
        test = extent.createTest("TC_PRODUCT_008 - Verify product images visible");
        try {
            performLogin("standard_user", "secret_sauce");
            ProductsPage pp = new ProductsPage(driver);
            if (pp.areProductImagesVisible()) test.pass("All product images visible");
            else {
                String path = Screenshot.capture(driver, "TC_PRODUCT_008");
                test.fail("Some product images missing").addScreenCaptureFromPath(path);
                Assert.fail("Some product images missing");
            }
        } catch (Exception e) {
            handleException(e, "TC_PRODUCT_008");
        }
    }

    @Test
    public void TC_PRODUCT_009_verifyDescriptions() {
        test = extent.createTest("TC_PRODUCT_009 - Verify product descriptions visible");
        try {
            performLogin("standard_user", "secret_sauce");
            ProductsPage pp = new ProductsPage(driver);
            if (pp.areProductDescriptionsVisible()) test.pass("All product descriptions visible");
            else {
                String path = Screenshot.capture(driver, "TC_PRODUCT_009");
                test.fail("Some product descriptions missing").addScreenCaptureFromPath(path);
                Assert.fail("Some product descriptions missing");
            }
        } catch (Exception e) {
            handleException(e, "TC_PRODUCT_009");
        }
    }
}
