package com.automationexercise;

import com.autoheal.AutoHealLocator;
import com.autoheal.config.AIConfig;
import com.autoheal.config.AutoHealConfiguration;
import com.autoheal.config.PerformanceConfig;
import com.autoheal.impl.adapter.SeleniumWebAutomationAdapter;
import com.autoheal.model.AIProvider;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

/**
 * Laboratorio 6 – Ejercicio 3: AutoHeal Locator
 *
 * Prerrequisito: variable de entorno GEMINI_API_KEY configurada.
 * Ver instrucciones al final de este archivo.
 */
public class Laboratorio6_E3 {

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;
    private AutoHealLocator autoHeal;

    private static final String TEST_EMAIL    = "autoheal_" + System.currentTimeMillis() + "@test.com";
    private static final String TEST_PASSWORD = "Test@123";

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        js   = (JavascriptExecutor) driver;

        registrarUsuarioDePrueba();
        cerrarSesion();

        String geminiKey = System.getenv("GEMINI_API_KEY");

        AIConfig aiConfig = new AIConfig.Builder()
                .provider(AIProvider.GOOGLE_GEMINI)
                .model("gemini-2.5-flash")
                .apiKey(geminiKey)
                .apiUrl("https://generativelanguage.googleapis.com/v1beta/models")
                .timeout(Duration.ofSeconds(60))
                .maxRetries(2)
                .maxTokensDOM(4096)
                .maxTokensVisual(4096)
                .visualAnalysisEnabled(false)
                .build();

        PerformanceConfig perfConfig = new PerformanceConfig.Builder()
                .elementTimeout(Duration.ofSeconds(60))
                .build();

        AutoHealConfiguration config = new AutoHealConfiguration.Builder()
                .ai(aiConfig)
                .performance(perfConfig)
                .build();

        autoHeal = new AutoHealLocator.Builder()
                .withWebAdapter(new SeleniumWebAutomationAdapter(driver))
                .withConfiguration(config)
                .build();
    }

    private void registrarUsuarioDePrueba() {
        driver.get("https://automationexercise.com/login");

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("input[data-qa='signup-name']"))).sendKeys("AutoHeal User");
        driver.findElement(By.cssSelector("input[data-qa='signup-email']")).sendKeys(TEST_EMAIL);
        js.executeScript("arguments[0].click();",
                driver.findElement(By.cssSelector("button[data-qa='signup-button']")));

        js.executeScript("arguments[0].click();",
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("id_gender1"))));
        driver.findElement(By.id("password")).sendKeys(TEST_PASSWORD);
        new Select(driver.findElement(By.id("days"))).selectByValue("1");
        new Select(driver.findElement(By.id("months"))).selectByValue("1");
        new Select(driver.findElement(By.id("years"))).selectByValue("2000");
        driver.findElement(By.id("first_name")).sendKeys("AutoHeal");
        driver.findElement(By.id("last_name")).sendKeys("User");
        driver.findElement(By.id("address1")).sendKeys("123 Test Street");
        new Select(driver.findElement(By.id("country"))).selectByVisibleText("United States");
        driver.findElement(By.id("state")).sendKeys("California");
        driver.findElement(By.id("city")).sendKeys("Los Angeles");
        driver.findElement(By.id("zipcode")).sendKeys("90001");
        driver.findElement(By.id("mobile_number")).sendKeys("1234567890");

        js.executeScript("arguments[0].click();",
                driver.findElement(By.cssSelector("button[data-qa='create-account']")));

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//b[text()='Account Created!']")));
        js.executeScript("arguments[0].click();",
                driver.findElement(By.cssSelector("a[data-qa='continue-button']")));
    }

    private void cerrarSesion() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[contains(.,'Logout')]"))).click();
        // Navegar directo a login; no esperar elemento específico porque
        // #google_vignette puede cubrir el DOM. testLogin() navega de nuevo.
        driver.get("https://automationexercise.com/login");
    }

    @Test
    public void testLogin() {
        driver.manage().deleteAllCookies();
        driver.get("https://automationexercise.com/login");
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("input[data-qa='login-email']")));

        // Selector intencionalmente roto → AutoHeal lo sana con Gemini AI
        WebElement campoEmail = autoHeal.findElement(
                "input[data-qa='login-email-ROTO']",
                "Campo de email para iniciar sesión");
        campoEmail.sendKeys(TEST_EMAIL);

        // Selector intencionalmente roto → AutoHeal lo sana con Gemini AI
        WebElement campoPassword = autoHeal.findElement(
                "input[name='password-ROTO']",
                "Campo de contraseña para iniciar sesión");
        campoPassword.sendKeys(TEST_PASSWORD);

        WebElement btnLogin = autoHeal.findElement(
                "button[data-qa='login-button']",
                "Botón de Login");
        btnLogin.click();

        WebElement loggedIn = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[contains(.,'Logged in as')]")));
        Assert.assertTrue(loggedIn.isDisplayed(),
                "Login fallido: no se muestra 'Logged in as'");

        System.out.println("AutoHeal sanó los localizadores rotos. Login exitoso: "
                + loggedIn.getText());
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}
