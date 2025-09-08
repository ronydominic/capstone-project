package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage {
    private WebDriver driver;
    private By cartItems = By.className("cart_item");
    private By checkoutBtn = By.id("checkout");

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public int getCartCount() {
        try {
            return driver.findElements(cartItems).size();
        } catch (Exception e) {
            return 0;
        }
    }

    public boolean isCheckoutVisible() {
        try {
            return driver.findElement(checkoutBtn).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
