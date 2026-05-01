package com.automationexercise.paginas;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PaginaAlerta {
    @FindBy(id = "promtButton")
    WebElement btnAlerta;

    WebDriver driver;

    public PaginaAlerta(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void clickAlerta() {
        btnAlerta.click();
    }

    public void ingresarTextoAlerta(String texto) {
        driver.switchTo().alert().sendKeys(texto);
    }

    public void aceptarAlerta() {
        driver.switchTo().alert().accept();
    }
}
