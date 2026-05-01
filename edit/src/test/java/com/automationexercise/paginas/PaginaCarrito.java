package com.automationexercise.paginas;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PaginaCarrito {
    @FindBy(xpath="//tbody/tr/td[3]/p")
    WebElement rowPrice;

    public PaginaCarrito(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public String obtenerPrecio() {
        return rowPrice.getText();
    }
}
