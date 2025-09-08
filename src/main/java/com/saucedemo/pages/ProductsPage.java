package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import org.openqa.selenium.WebElement;

public class ProductsPage {
    private WebDriver driver;

    private By title = By.className("title");
    private By productNames = By.cssSelector(".inventory_item_name");
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
}
