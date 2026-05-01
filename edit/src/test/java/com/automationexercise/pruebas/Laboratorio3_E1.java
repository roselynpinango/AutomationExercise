package com.automationexercise.pruebas;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.automationexercise.paginas.PaginaFormulario;
import com.automationexercise.paginas.PaginaInicio;
import com.automationexercise.paginas.PaginaLogin;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Laboratorio3_E1 {
    String url = "https://www.automationexercise.com/";
    WebDriver driver;
    
    @BeforeSuite
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @BeforeTest
    public void irURL() {
        driver.get("https://www.automationexercise.com/");
    }

    @BeforeClass
    public void MaxVentana() {
        driver.manage().window().maximize();
    }

    @Test
    public void registrarUsuario() {
        PaginaInicio paginaInicio = new PaginaInicio(driver);
        paginaInicio.clickSignUpLogin();

        PaginaLogin paginaLogin = new PaginaLogin(driver);
        paginaLogin.completarName("John Doe");
        paginaLogin.completarEmail("john.doe@otroejemplo.com");
        paginaLogin.clickCreateAccount();

        PaginaFormulario paginaFormulario = new PaginaFormulario(driver);
        String titulo = paginaFormulario.obtenerTitulo();
        Assert.assertEquals("Automation Exercise - Signup", titulo);// Aquí iría el código para registrar un usuario    
    }

    @AfterMethod
    public void capturarEvidencia() throws IOException {
        File screen = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String ruta = "..\\Evidencias\\laboratorio3_e1.png";
        FileUtils.copyFile(screen, new File(ruta));
    }

    @AfterClass
    public void finPrueba() {
        System.out.println("Fin Prueba");
    }

    @AfterTest
    public void cerrarNavegador() {
        driver.quit();
    }

    @AfterSuite
    public void finSuite() {
        System.out.println("Fin Suite");
    }
}
