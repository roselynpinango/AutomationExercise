package com.automationexercise.M6_E2;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.time.Duration;

@Listeners(ReportListener.class)
public class PurchaseTest {

    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;
    ExtentTest extentTest;

    private static ExtentReports extent;
    private static final String EMAIL    = "user_" + System.currentTimeMillis() + "@test.com";
    private static final String PASSWORD = "Test@123";
    private static final String NOMBRE   = "Test User";

    @BeforeSuite
    public static void setupReporte() {
        ExtentSparkReporter spark = new ExtentSparkReporter("target/reports/M6_E2_Reporte.html");
        spark.config().setDocumentTitle("Reporte M6_E2 - Proceso de Compra");
        spark.config().setReportName("AutomationExercise – Flujo completo de compra");
        extent = new ExtentReports();
        extent.attachReporter(spark);
    }

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        js   = (JavascriptExecutor) driver;
    }

    @Test
    public void procesarCompra() {
        extentTest = extent.createTest("procesarCompra",
                "Registro → agregar al carrito → checkout → pago → confirmación");

        registrarUsuario();
        agregarAlCarrito();
        realizarCheckout();
    }

    private void registrarUsuario() {
        driver.get("https://automationexercise.com/login");
        extentTest.info("Registrando usuario: " + EMAIL);

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("input[data-qa='signup-name']"))).sendKeys(NOMBRE);
        driver.findElement(By.cssSelector("input[data-qa='signup-email']")).sendKeys(EMAIL);
        js.executeScript("arguments[0].click();",
                driver.findElement(By.cssSelector("button[data-qa='signup-button']")));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("id_gender1"))).click();
        driver.findElement(By.id("password")).sendKeys(PASSWORD);
        new Select(driver.findElement(By.id("days"))).selectByValue("15");
        new Select(driver.findElement(By.id("months"))).selectByValue("6");
        new Select(driver.findElement(By.id("years"))).selectByValue("1995");
        driver.findElement(By.id("first_name")).sendKeys("Test");
        driver.findElement(By.id("last_name")).sendKeys("User");
        driver.findElement(By.id("address1")).sendKeys("123 Test Street");
        new Select(driver.findElement(By.id("country"))).selectByVisibleText("United States");
        driver.findElement(By.id("state")).sendKeys("California");
        driver.findElement(By.id("city")).sendKeys("Los Angeles");
        driver.findElement(By.id("zipcode")).sendKeys("90001");
        driver.findElement(By.id("mobile_number")).sendKeys("1234567890");

        js.executeScript("arguments[0].click();",
                driver.findElement(By.cssSelector("button[data-qa='create-account']")));

        WebElement cuentaCreada = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//b[text()='Account Created!']")));
        Assert.assertTrue(cuentaCreada.isDisplayed(), "La cuenta no fue creada correctamente");
        extentTest.pass("Cuenta creada exitosamente");
        screenshot("01_cuenta_creada");

        js.executeScript("arguments[0].click();",
                driver.findElement(By.cssSelector("a[data-qa='continue-button']")));
    }

    private void agregarAlCarrito() {
        driver.get("https://automationexercise.com/product_details/1");
        extentTest.info("Navegando al detalle del producto 1");

        WebElement addToCart = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("button.btn.btn-default.cart")));
        js.executeScript("arguments[0].click();", addToCart);
        extentTest.info("Producto 1 agregado al carrito");

        WebElement closeModal = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("button.btn.btn-success.close-modal")));
        js.executeScript("arguments[0].click();", closeModal);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.cssSelector("div.modal-dialog")));

        driver.get("https://automationexercise.com/view_cart");

        WebElement itemCarrito = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("td.cart_description")));
        Assert.assertTrue(itemCarrito.isDisplayed(), "El carrito está vacío");
        extentTest.pass("Producto verificado en el carrito");
        screenshot("02_carrito");
    }

    private void realizarCheckout() {
        js.executeScript("arguments[0].click();",
                wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//a[contains(@class,'check_out')]"))));

        WebElement direccionEntrega = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("address_delivery")));
        Assert.assertTrue(direccionEntrega.isDisplayed(), "No se muestra la dirección de entrega");
        extentTest.pass("Dirección de entrega verificada en checkout");
        screenshot("03_checkout");

        driver.findElement(By.cssSelector("textarea[name='message']"))
                .sendKeys("Orden automatizada - M6_E2");
        js.executeScript("arguments[0].click();",
                wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//a[contains(.,'Place Order')]"))));

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("input[data-qa='name-on-card']"))).sendKeys(NOMBRE);
        driver.findElement(By.cssSelector("input[data-qa='card-number']")).sendKeys("4111111111111111");

        // CVC y expiración via JS para evitar bloqueo por anuncios
        js.executeScript(
            "var cvc = document.querySelector('input[data-qa=\"cvc\"]');" +
            "cvc.value = '123';" +
            "cvc.dispatchEvent(new Event('input', {bubbles:true}));");

        WebElement monthEl = driver.findElement(By.cssSelector("[data-qa='expiry-month']"));
        if ("select".equals(monthEl.getTagName())) {
            new Select(monthEl).selectByValue("12");
        } else {
            js.executeScript("arguments[0].value='12'; arguments[0].dispatchEvent(new Event('input',{bubbles:true}));", monthEl);
        }

        js.executeScript(
            "var yr = document.querySelector('input[data-qa=\"expiry-year\"]');" +
            "yr.value = '2027';" +
            "yr.dispatchEvent(new Event('input', {bubbles:true}));");

        extentTest.info("Datos de pago ingresados");
        screenshot("04_pago");

        js.executeScript("arguments[0].click();",
                wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector("button[data-qa='pay-button']"))));

        WebElement confirmacion = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//b[text()='Order Placed!']")));
        Assert.assertTrue(confirmacion.isDisplayed(), "No se confirmó la orden");
        extentTest.pass("Orden confirmada – proceso de compra completado");
        screenshot("05_orden_confirmada");
    }

    void screenshot(String paso) {
        try {
            File src  = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File dest = new File("target/screenshots/M6_E2/" + paso + ".png");
            dest.getParentFile().mkdirs();
            org.apache.commons.io.FileUtils.copyFile(src, dest);
            extentTest.addScreenCaptureFromPath(dest.getAbsolutePath(), paso);
        } catch (Exception e) {
            extentTest.warning("Screenshot no disponible: " + e.getMessage());
        }
    }

    WebDriver getDriver() { return driver; }

    ExtentTest getExtentTest() { return extentTest; }

    @AfterClass
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    @AfterSuite
    public static void flushReporte() {
        if (extent != null) extent.flush();
    }
}
