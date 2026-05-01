package com.automationexercise.pruebas;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.automationexercise.paginas.PaginaAlerta;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Laboratorio4_E1A {
    String url = "https://demoqa.com/alerts";
    WebDriver driver;

    @BeforeSuite
    public void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--force-device-scale-factor=0.75");
        driver = new ChromeDriver(options);
        driver.get(url);
        driver.manage().window().maximize();
    }

    @Test
    public void pruebaAlerta() {
        PaginaAlerta paginaAlerta = new PaginaAlerta(driver);
        paginaAlerta.clickAlerta();
        paginaAlerta.ingresarTextoAlerta("Texto de prueba");
        paginaAlerta.aceptarAlerta();
    }

    @AfterSuite
    public void tearDown() {
        // Cerrar el navegador después de las pruebas
        // driver.quit();
    }
}
