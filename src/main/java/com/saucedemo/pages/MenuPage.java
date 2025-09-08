package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MenuPage {
    private WebDriver driver;

    private By menuButton = By.id("react-burger-menu-btn");
    private By allItems = By.id("inventory_sidebar_link");
    private By about = By.id("about_sidebar_link");
    private By logout = By.id("logout_sidebar_link");
    private By reset = By.id("reset_sidebar_link");
    private By closeBtn = By.id("react-burger-cross-btn");

    public MenuPage(WebDriver driver) {
        this.driver = driver;
    }

    public void openMenu() {
        driver.findElement(menuButton).click();
    }

    public void clickAllItems() {
        driver.findElement(allItems).click();
    }

    public void clickAbout() {
        driver.findElement(about).click();
    }

    public void clickLogout() {
        driver.findElement(logout).click();
    }

    public void clickResetAppState() {
        driver.findElement(reset).click();
    }

    public void closeMenu() {
        driver.findElement(closeBtn).click();
    }
}
