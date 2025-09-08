package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CartPage {
    private WebDriver driver;
    private By cartItems = By.className("cart_item");
    private By checkoutBtn = By.id("checkout");
    private By removeBtns = By.cssSelector(".cart_button"); // button to remove item

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

    public void removeItemByIndex(int index) {
        try {
            List<WebElement> buttons = driver.findElements(removeBtns);
            if (index >= 0 && index < buttons.size()) {
                buttons.get(index).click();
            }
        } catch (Exception e) {
            // ignore if not found
        }
    }

    public void removeAllItems() {
        try {
            List<WebElement> buttons = driver.findElements(removeBtns);
            for (WebElement b : buttons) {
                b.click();
            }
        } catch (Exception e) {
            // ignore
        }
    }
}
