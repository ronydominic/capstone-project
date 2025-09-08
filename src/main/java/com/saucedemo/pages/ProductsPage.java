package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class ProductsPage {
    private WebDriver driver;

    private By title = By.className("title");
    private By productNames = By.cssSelector(".inventory_item_name");
    private By productPrices = By.cssSelector(".inventory_item_price");
    private By productDescriptions = By.cssSelector(".inventory_item_desc");
    private By productImages = By.cssSelector(".inventory_item_img img");
    private By cartIcon = By.id("shopping_cart_container");
    private By sortDropdown = By.className("product_sort_container");
    private By firstProductName = By.cssSelector(".inventory_item_name");
    private By addToCartBtns = By.cssSelector(".btn_inventory");

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getTitle() {
        return driver.findElement(title).getText();
    }

    public boolean isCartVisible() {
        return driver.findElement(cartIcon).isDisplayed();
    }

    public List<WebElement> getProductNames() {
        return driver.findElements(productNames);
    }

    public void selectSortOption(String visibleText) {
        Select s = new Select(driver.findElement(sortDropdown));
        s.selectByVisibleText(visibleText);
    }

    public void clickFirstProduct() {
        driver.findElement(firstProductName).click();
    }

    public void addAllProductsToCart() {
        List<WebElement> btns = driver.findElements(addToCartBtns);
        for (WebElement b : btns) {
            if (b.getText().equalsIgnoreCase("add to cart")) {
                b.click();
            }
        }
    }

    public void addProductToCartByIndex(int index) {
        List<WebElement> btns = driver.findElements(addToCartBtns);
        if (index >= 0 && index < btns.size()) {
            btns.get(index).click();
        }
    }

    // ------------------- NEW METHODS -------------------

    // Get product prices as doubles
    public List<Double> getProductPrices() {
        List<WebElement> pricesElements = driver.findElements(productPrices);
        List<Double> prices = new ArrayList<>();
        for (WebElement p : pricesElements) {
            String text = p.getText().replace("$", "").trim();
            prices.add(Double.parseDouble(text));
        }
        return prices;
    }

    // Get count of items in cart
    public int getCartCount() {
        try {
            String count = driver.findElement(By.className("shopping_cart_badge")).getText();
            return Integer.parseInt(count);
        } catch (Exception e) {
            return 0;
        }
    }

    // Get product title on details page
    public String getProductDetailTitle() {
        return driver.findElement(By.className("inventory_details_name")).getText();
    }

    // Check all product images are displayed
    public boolean areProductImagesVisible() {
        List<WebElement> imgs = driver.findElements(productImages);
        for (WebElement img : imgs) {
            if (!img.isDisplayed()) return false;
        }
        return true;
    }

    // Check all product descriptions are displayed
    public boolean areProductDescriptionsVisible() {
        List<WebElement> descs = driver.findElements(productDescriptions);
        for (WebElement d : descs) {
            if (!d.isDisplayed()) return false;
        }
        return true;
    }
}
