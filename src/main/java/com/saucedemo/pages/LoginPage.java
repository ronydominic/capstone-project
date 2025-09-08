package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private WebDriver driver;

    private By username = By.id("user-name");
    private By password = By.id("password");
    private By loginButton = By.id("login-button");
    private By errorContainer = By.cssSelector("h3[data-test='error']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterUsername(String u) {
        driver.findElement(username).clear();
        driver.findElement(username).sendKeys(u);
    }

    public void enterPassword(String p) {
        driver.findElement(password).clear();
        driver.findElement(password).sendKeys(p);
    }

    public void clickLogin() {
        driver.findElement(loginButton).click();
    }

    public String getError() {
        try {
            return driver.findElement(errorContainer).getText();
        } catch (Exception e) {
            return "";
        }
    }
}
