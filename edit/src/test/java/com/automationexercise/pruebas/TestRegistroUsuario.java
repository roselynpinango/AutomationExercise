package com.automationexercise.pruebas;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.automationexercise.paginas.PaginaFormulario;
import com.automationexercise.paginas.PaginaInicio;
import com.automationexercise.paginas.PaginaLogin;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestRegistroUsuario {
    String urlTest = "https://automationexercise.com/";

    @Test
    public void registrarUsuarioPaso1() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.get(urlTest);
        driver.manage().window().maximize();

        PaginaInicio paginaInicio = new PaginaInicio(driver);
        paginaInicio.clickSignUpLogin();

        PaginaLogin paginaLogin = new PaginaLogin(driver);
        paginaLogin.completarName("John Doe");
        paginaLogin.completarEmail("john.doe@otroejemplo.com");
        paginaLogin.clickCreateAccount();

        PaginaFormulario paginaFormulario = new PaginaFormulario(driver);
        String titulo = paginaFormulario.obtenerTitulo();
        Assert.assertEquals("Automation Exercise - Signup", titulo);
    }
}
