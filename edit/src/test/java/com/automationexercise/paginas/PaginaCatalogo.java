package com.automationexercise.paginas;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PaginaCatalogo {
    @FindBy(css="a[href='/product_details/1']")
    WebElement btnProductDetail;

    @FindBy(id="search_product")
    WebElement txtBuscador;

    @FindBy(id="submit_search")
    WebElement btnBuscar;

    WebDriver driver;

    public PaginaCatalogo(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickProductDetail() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnProductDetail);
    }

    public void buscarProducto(String producto) {
        new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.visibilityOf(txtBuscador));
        txtBuscador.sendKeys(producto);
    }

    public void clickBuscar() {
        btnBuscar.click();
    }
}
