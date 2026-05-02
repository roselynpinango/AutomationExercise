package com.automationexercise.M6_E1;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.time.Duration;

@Listeners(TestListener.class)
public class CheckoutTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @Test
    public void comprarProducto() {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        driver.get("https://automationexercise.com/");

        WebElement products = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//a[contains(.,'Products')]")));
        js.executeScript("arguments[0].click();", products);

        WebElement addToCart = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("(//a[@data-product-id='1'])[1]")));
        js.executeScript("arguments[0].click();", addToCart);

        WebElement closeModal = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("button.btn.btn-success.close-modal")));
        js.executeScript("arguments[0].click();", closeModal);

        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.cssSelector("div.modal-dialog")));

        WebElement cartLink = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//a[@href='/view_cart']")));
        js.executeScript("arguments[0].click();", cartLink);

        WebElement cartTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//li[contains(.,'Shopping Cart')]")));
        Assert.assertTrue(cartTitle.isDisplayed(), "No se mostró el carrito");

        WebElement checkout = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//a[contains(@class,'check_out')]")));
        js.executeScript("arguments[0].click();", checkout);

        WebElement loginPrompt = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//p[contains(.,'Register / Login account')]")));
        Assert.assertTrue(loginPrompt.isDisplayed(), "No se mostró el checkout esperado");

        takeScreenshot("checkout_ok");
    }

    private void takeScreenshot(String name) {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            File dest = new File("target/screenshots/" + name + ".png");
            dest.getParentFile().mkdirs();
            org.apache.commons.io.FileUtils.copyFile(src, dest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public WebDriver getDriver() {
        return driver;
    }
}