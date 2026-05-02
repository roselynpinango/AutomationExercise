package com.automationexercise.M6_E2;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.time.Duration;
import java.util.List;

public class CartTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;
    private ExtentTest extentTest;
    private static ExtentReports extent;

    @BeforeClass
    public void setupReporteYDriver() {
        ExtentSparkReporter spark = new ExtentSparkReporter("target/reports/M6_E2_CartReport.html");
        spark.config().setDocumentTitle("Reporte M6_E2 – Gestión de Carrito");
        spark.config().setReportName("AutomationExercise – Cobertura adicional de carrito");
        extent = new ExtentReports();
        extent.attachReporter(spark);

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        js   = (JavascriptExecutor) driver;
    }

    // Limpia el carrito antes de cada test para garantizar independencia
    @BeforeMethod
    public void limpiarCarrito() {
        driver.get("https://automationexercise.com/view_cart");
        List<WebElement> botones = driver.findElements(By.cssSelector("a.cart_quantity_delete"));
        for (WebElement btn : botones) {
            js.executeScript("arguments[0].click();", btn);
        }
        if (!botones.isEmpty()) {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.cssSelector("a.cart_quantity_delete")));
        }
    }

    /**
     * Verifica que el nombre del producto en el carrito coincide
     * con el nombre mostrado en su página de detalle.
     */
    @Test
    public void verificarDetallesProductoEnCarrito() {
        extentTest = extent.createTest("verificarDetallesProductoEnCarrito",
                "El nombre del producto en el carrito debe coincidir con el de su página de detalle");

        driver.get("https://automationexercise.com/product_details/1");
        String nombreDetalle = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='product-information']//h2"))).getText();
        extentTest.info("Nombre en página de detalle: " + nombreDetalle);

        agregarProductoYCerrarModal();
        driver.get("https://automationexercise.com/view_cart");

        String nombreCarrito = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("td.cart_description h4 a"))).getText();
        extentTest.info("Nombre en carrito: " + nombreCarrito);

        Assert.assertEquals(nombreCarrito, nombreDetalle,
                "El nombre del producto en el carrito no coincide con la página de detalle");
        extentTest.pass("Nombre '" + nombreDetalle + "' coincide en detalle y en carrito");
        screenshot("detalles_producto_en_carrito");
    }

    /**
     * Verifica que al eliminar el único producto del carrito,
     * el carrito queda vacío (sin items).
     */
    @Test
    public void eliminarProductoDelCarrito() {
        extentTest = extent.createTest("eliminarProductoDelCarrito",
                "Al eliminar el único producto del carrito, el carrito debe quedar vacío");

        driver.get("https://automationexercise.com/product_details/2");
        agregarProductoYCerrarModal();

        driver.get("https://automationexercise.com/view_cart");
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("td.cart_description")));
        extentTest.info("Producto agregado y verificado en el carrito");
        screenshot("antes_de_eliminar");

        js.executeScript("arguments[0].click();",
                driver.findElement(By.cssSelector("a.cart_quantity_delete")));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.cssSelector("td.cart_description")));

        List<WebElement> itemsRestantes = driver.findElements(By.cssSelector("td.cart_description"));
        Assert.assertTrue(itemsRestantes.isEmpty(),
                "El carrito debería estar vacío después de eliminar el producto");
        extentTest.pass("Producto eliminado – carrito vacío verificado");
        screenshot("carrito_vacio");
    }

    /**
     * Verifica que al agregar dos productos distintos ambos
     * aparecen correctamente en el carrito.
     */
    @Test
    public void agregarDosProductos() {
        extentTest = extent.createTest("agregarDosProductos",
                "Agregar 2 productos distintos y verificar que ambos aparecen en el carrito");

        driver.get("https://automationexercise.com/product_details/1");
        agregarProductoYCerrarModal();
        extentTest.info("Producto 1 agregado al carrito");

        driver.get("https://automationexercise.com/product_details/2");
        agregarProductoYCerrarModal();
        extentTest.info("Producto 2 agregado al carrito");

        driver.get("https://automationexercise.com/view_cart");

        List<WebElement> items = wait.until(d -> {
            List<WebElement> lista = d.findElements(By.cssSelector("td.cart_description"));
            return lista.size() >= 2 ? lista : null;
        });

        Assert.assertEquals(items.size(), 2,
                "El carrito debería contener exactamente 2 productos");
        extentTest.pass("Carrito con " + items.size() + " productos verificado");
        screenshot("dos_productos_en_carrito");
    }

    private void agregarProductoYCerrarModal() {
        js.executeScript("arguments[0].click();",
                wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("button.btn.btn-default.cart"))));
        js.executeScript("arguments[0].click();",
                wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("button.btn.btn-success.close-modal"))));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.cssSelector("div.modal-dialog")));
    }

    private void screenshot(String paso) {
        try {
            File src  = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File dest = new File("target/screenshots/M6_E2/" + paso + ".png");
            dest.getParentFile().mkdirs();
            org.apache.commons.io.FileUtils.copyFile(src, dest);
            if (extentTest != null) {
                extentTest.addScreenCaptureFromPath(dest.getAbsolutePath(), paso);
            }
        } catch (Exception e) {
            if (extentTest != null) extentTest.warning("Screenshot no disponible: " + e.getMessage());
        }
    }

    @AfterMethod
    public void capturarFalloSiHay(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            screenshot(result.getName() + "_fallo");
            if (extentTest != null) {
                extentTest.fail("Test fallido: " + result.getThrowable().getMessage());
            }
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) driver.quit();
        if (extent != null) extent.flush();
    }
}
