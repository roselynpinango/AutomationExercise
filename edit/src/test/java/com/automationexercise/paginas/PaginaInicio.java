package com.automationexercise.paginas;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PaginaInicio {
    @FindBy(partialLinkText = "Signup / Log")
    WebElement lnkSignUpLogin;

    @FindBy(partialLinkText = "Products")
    WebElement lnkProducts;

    WebDriver driver;

    public PaginaInicio(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickSignUpLogin() {
        lnkSignUpLogin.click();
    }

    public void clickProducts() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", lnkProducts);
    }
}
