package com.automationexercise.paginas;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PaginaDetalleProducto {
    @FindBy(css="button.btn.btn-default.cart")
    WebElement btnAddToCart;

    @FindBy(xpath="//button[normalize-space()='Continue Shopping']")
    WebElement btnContinueShopping;

    @FindBy(partialLinkText = "Cart")
    WebElement btnViewCart;

    WebDriver driver;

    public PaginaDetalleProducto(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickAddToCart() {
        btnAddToCart.click();
    }

    public void clickContinueShopping() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
            .until(ExpectedConditions.elementToBeClickable(btnContinueShopping));
        btnContinueShopping.click();
    }

    public void clickViewCart() {
        btnViewCart.click();
    }
}
