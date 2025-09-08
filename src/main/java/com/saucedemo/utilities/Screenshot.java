package com.saucedemo.utilities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;

public class Screenshot {
    static String projectPath = System.getProperty("user.dir");

    public static String capture(WebDriver driver, String testName) throws IOException {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String dest = projectPath + "\\src\\test\\resources\\Screenshots\\" + testName + "_" + timestamp + ".png";
        File destFile = new File(dest);
        FileUtils.copyFile(src, destFile);
        return dest;
    }
}
