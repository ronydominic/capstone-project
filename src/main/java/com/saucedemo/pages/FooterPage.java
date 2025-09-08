package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FooterPage {
    private WebDriver driver;

    private By footer = By.className("footer");
    private By twitter = By.cssSelector("a[href*='twitter']");
    private By facebook = By.cssSelector("a[href*='facebook']");
    private By linkedin = By.cssSelector("a[href*='linkedin']");
    private By terms = By.xpath("//a[text()='Terms of Service' or contains(.,'Terms')]");
    private By privacy = By.xpath("//a[text()='Privacy Policy' or contains(.,'Privacy')]");

    public FooterPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isFooterVisible() {
        try {
            return driver.findElement(footer).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickTwitter() {
        driver.findElement(twitter).click();
    }

    public void clickFacebook() {
        driver.findElement(facebook).click();
    }

    public void clickLinkedin() {
        driver.findElement(linkedin).click();
    }

    public boolean isTermsClickable() {
        try {
            return driver.findElement(terms).isDisplayed() && driver.findElement(terms).isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPrivacyClickable() {
        try {
            return driver.findElement(privacy).isDisplayed() && driver.findElement(privacy).isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickTerms() {
        driver.findElement(terms).click();
    }

    public void clickPrivacy() {
        driver.findElement(privacy).click();
    }
}
