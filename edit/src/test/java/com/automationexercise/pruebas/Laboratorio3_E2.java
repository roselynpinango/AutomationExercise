package com.automationexercise.pruebas;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
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

public class Laboratorio3_E2 {
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
        // Se deja en blanco el email para provocar un error
        paginaLogin.clickCreateAccount();

        PaginaFormulario paginaFormulario = new PaginaFormulario(driver);
        String titulo = paginaFormulario.obtenerTitulo();
        Assert.assertEquals("Automation Exercise - Signup / Login", titulo);// Aquí iría el código para registrar un usuario    
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
