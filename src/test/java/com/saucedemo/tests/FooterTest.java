package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.FooterPage;
import com.saucedemo.utilities.Screenshot;

import org.testng.annotations.Test;
import org.testng.Assert;

public class FooterTest extends BaseTest {

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
    public void TC_FOOTER_001_verifyFooterVisibility() {
        test = extent.createTest("TC_FOOTER_001 - Verify footer is visible");
        try {
            performLogin();
            FooterPage fp = new FooterPage(driver);
            if (fp.isFooterVisible()) test.pass("Footer is visible");
            else {
                String path = Screenshot.capture(driver, "TC_FOOTER_001");
                test.fail("Footer not visible").addScreenCaptureFromPath(path);
                Assert.fail("Footer not visible");
            }
        } catch (Exception e) {
            handleException(e, "TC_FOOTER_001");
        }
    }

    @Test
    public void TC_FOOTER_002_verifyTwitterLink() {
        test = extent.createTest("TC_FOOTER_002 - Verify Twitter link clickable");
        try {
            performLogin();
            FooterPage fp = new FooterPage(driver);
            fp.clickTwitter();
            Thread.sleep(1000);
            boolean ok = driver.getCurrentUrl().contains("twitter") || driver.getTitle().toLowerCase().contains("twitter");
            if (ok) test.pass("Twitter link opened correctly");
            else {
                String path = Screenshot.capture(driver, "TC_FOOTER_002");
                test.fail("Twitter link did not open correctly").addScreenCaptureFromPath(path);
                Assert.fail("Twitter link did not open correctly");
            }
        } catch (Exception e) {
            handleException(e, "TC_FOOTER_002");
        }
    }

    @Test
    public void TC_FOOTER_003_verifyFacebookLink() {
        test = extent.createTest("TC_FOOTER_003 - Verify Facebook link clickable");
        try {
            performLogin();
            FooterPage fp = new FooterPage(driver);
            fp.clickFacebook();
            Thread.sleep(1000);
            boolean ok = driver.getCurrentUrl().contains("facebook") || driver.getTitle().toLowerCase().contains("facebook");
            if (ok) test.pass("Facebook link opened correctly");
            else {
                String path = Screenshot.capture(driver, "TC_FOOTER_003");
                test.fail("Facebook link did not open correctly").addScreenCaptureFromPath(path);
                Assert.fail("Facebook link did not open correctly");
            }
        } catch (Exception e) {
            handleException(e, "TC_FOOTER_003");
        }
    }

    @Test
    public void TC_FOOTER_004_verifyLinkedInLink() {
        test = extent.createTest("TC_FOOTER_004 - Verify LinkedIn link clickable");
        try {
            performLogin();
            FooterPage fp = new FooterPage(driver);
            fp.clickLinkedin();
            Thread.sleep(1000);
            boolean ok = driver.getCurrentUrl().contains("linkedin") || driver.getTitle().toLowerCase().contains("linkedin");
            if (ok) test.pass("LinkedIn link opened correctly");
            else {
                String path = Screenshot.capture(driver, "TC_FOOTER_004");
                test.fail("LinkedIn link did not open correctly").addScreenCaptureFromPath(path);
                Assert.fail("LinkedIn link did not open correctly");
            }
        } catch (Exception e) {
            handleException(e, "TC_FOOTER_004");
        }
    }

    @Test
    public void TC_FOOTER_005_verifyTermsClickable() {
        test = extent.createTest("TC_FOOTER_005 - Verify Terms of Service link is clickable");
        try {
            performLogin();
            FooterPage fp = new FooterPage(driver);
            if (fp.isTermsClickable()) test.pass("Terms of Service link is clickable");
            else {
                String path = Screenshot.capture(driver, "TC_FOOTER_005");
                test.fail("Terms of Service link not clickable").addScreenCaptureFromPath(path);
                Assert.fail("Terms of Service link not clickable");
            }
        } catch (Exception e) {
            handleException(e, "TC_FOOTER_005");
        }
    }

    @Test
    public void TC_FOOTER_006_verifyPrivacyClickable() {
        test = extent.createTest("TC_FOOTER_006 - Verify Privacy Policy link is clickable");
        try {
            performLogin();
            FooterPage fp = new FooterPage(driver);
            if (fp.isPrivacyClickable()) test.pass("Privacy Policy link is clickable");
            else {
                String path = Screenshot.capture(driver, "TC_FOOTER_006");
                test.fail("Privacy Policy link not clickable").addScreenCaptureFromPath(path);
                Assert.fail("Privacy Policy link not clickable");
            }
        } catch (Exception e) {
            handleException(e, "TC_FOOTER_006");
        }
    }
}
