package com.automationexercise.pruebas;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.automationexercise.paginas.PaginaCatalogo;
import com.automationexercise.paginas.PaginaInicio;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Laboratorio4_E2 {
    String url = "https://www.automationexercise.com/";
    WebDriver driver;

    @BeforeSuite
    public void abrirNavegador() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("incognito");
        driver = new ChromeDriver(options   );
        driver.get(url);
        driver.manage().window().maximize();
    }

    @Test
    public void buscarPalabra() {
        PaginaInicio paginaInicio = new PaginaInicio(driver);
        paginaInicio.clickProducts();

        PaginaCatalogo paginaCatalogo = new PaginaCatalogo(driver);
        paginaCatalogo.buscarProducto("Dress");
        paginaCatalogo.clickBuscar();
    }

    @AfterSuite
    public void cerrarNavegador() {
        //driver.quit();
    }
}
