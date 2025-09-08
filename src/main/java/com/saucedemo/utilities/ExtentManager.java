package com.saucedemo.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
    private static ExtentReports extent;
    private static final String REPORT_PATH = System.getProperty("user.dir")
            + "/src/test/resources/Reports/extent-report.html";

    public static ExtentReports getInstance() {
        if (extent == null) {
            ExtentSparkReporter spark = new ExtentSparkReporter(REPORT_PATH);
            spark.config().setDocumentTitle("SauceDemo Automation Report");
            spark.config().setReportName("SauceDemo Test Suite");
            extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.setSystemInfo("Environment", "QA");
            extent.setSystemInfo("Tester", "Rony Dominic");
        }
        return extent;
    }
}
