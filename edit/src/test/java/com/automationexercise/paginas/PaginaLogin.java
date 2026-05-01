package com.automationexercise.paginas;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PaginaLogin {
    // Elementos de la página (Name, Email address, Create Account)
    @FindBy(name = "name")
    WebElement txtName;

    @FindBy(css = "input[data-qa='signup-email']")
    WebElement txtEmail;

    @FindBy(xpath = "//button[@data-qa='signup-button']")
    WebElement btnSignUp;

    @FindBy(css = "input[data-qa='login-email']")
    WebElement txtUsername;

    @FindBy(name = "password")
    WebElement txtPassword;

    @FindBy(xpath = "//button[@data-qa='login-button']")
    WebElement btnLogin;

    // Constructor
    public PaginaLogin(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    // Métodos para interactuar con la página (Completar Name, Email, Click en Create Account)
    public void completarName(String name) {
        txtName.sendKeys(name);
    }

    public void completarEmail(String email) {
        txtEmail.sendKeys(email);
    }

    public void clickCreateAccount() {
        btnSignUp.click();
    }

    public void completarUsername(String username) {
        txtUsername.sendKeys(username);
    }

    public void completarPassword(String password) {
        txtPassword.sendKeys(password);
    }

    public void clickLogin() {
        btnLogin.click();
    }
}
