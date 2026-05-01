package com.automationexercise.pruebas;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.automationexercise.paginas.PaginaInicio;
import com.automationexercise.paginas.PaginaLogin;
import com.automationexercise.utils.ExcelReader;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Laboratorio3_E3 {
    String url = "https://www.automationexercise.com/";
    WebDriver driver;

    @BeforeSuite
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--force-device-scale-factor=0.75");
        driver = new ChromeDriver(options);
        driver.get(url);
        driver.manage().window().maximize();
    }

    @Test(dataProvider = "credenciales")
    public void loginUsuario(String username, String password) {
        PaginaInicio paginaInicio = new PaginaInicio(driver);
        paginaInicio.clickSignUpLogin();

        PaginaLogin paginaLogin = new PaginaLogin(driver);
        paginaLogin.completarUsername(username);
        paginaLogin.completarPassword(password);
        paginaLogin.clickLogin();
        System.out.println("Fin del test");
    }

    @DataProvider(name = "credenciales")
    public Object[][] leerCredencialesExcel() throws IOException {
        String rutaExcel = "..\\Datos\\credenciales.xlsx";
        return ExcelReader.leerExcel(rutaExcel, "Login");
    }


    @AfterSuite
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
