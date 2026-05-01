package com.automationexercise.paginas;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class PaginaFormulario {
    WebDriver driver;

    // Constructor
    public PaginaFormulario(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Métodos para interactuar con la página
    public String obtenerTitulo() {
        return driver.getTitle();
    }
}
