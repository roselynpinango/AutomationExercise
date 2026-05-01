package com.automationexercise.pruebas;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.automationexercise.paginas.PaginaCatalogo;
import com.automationexercise.paginas.PaginaInicio;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CrossBrowserTest {
    String url = "https://www.automationexercise.com/";
    WebDriver driver;

    @BeforeTest
    @Parameters("browser")
    public void setUp(@Optional("chrome") String navegador) {
        if (navegador.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--no-sandbox", "--disable-dev-shm-usage", "--window-size=1920,1080");
            driver = new ChromeDriver(options);
        } else if (navegador.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (navegador.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        } else {
            throw new IllegalArgumentException("Navegador no soportado: " + navegador);
        }
        driver.get(url);
    }

    @Test
    public void testCrossBrowser() {
        PaginaInicio paginaInicio = new PaginaInicio(driver);
        paginaInicio.clickProducts();

        PaginaCatalogo paginaCatalogo = new PaginaCatalogo(driver);
        paginaCatalogo.buscarProducto("Dress");
        paginaCatalogo.clickBuscar();
    }

    @AfterSuite
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
