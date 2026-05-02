package com.automationexercise.M6_E2;

import org.openqa.selenium.*;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;

public class ReportListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        Object instance = result.getInstance();
        if (!(instance instanceof PurchaseTest)) return;

        PurchaseTest test = (PurchaseTest) instance;
        WebDriver driver  = test.getDriver();
        if (driver == null) return;

        try {
            File src  = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File dest = new File("target/screenshots/M6_E2/" + result.getName() + "_fallo.png");
            dest.getParentFile().mkdirs();
            FileHandler.copy(src, dest);

            if (test.getExtentTest() != null) {
                test.getExtentTest().fail("Fallo: " + result.getThrowable().getMessage());
                test.getExtentTest().addScreenCaptureFromPath(dest.getAbsolutePath(), "screenshot_fallo");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
