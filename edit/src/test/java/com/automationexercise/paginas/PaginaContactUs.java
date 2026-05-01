package com.automationexercise.paginas;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PaginaContactUs {
    @FindBy(name = "name")
    WebElement nameInput;

    @FindBy(name = "email")
    WebElement emailInput;

    @FindBy(name = "subject")
    WebElement subjectInput;

    @FindBy(id = "message")
    WebElement messageInput;

    @FindBy(name = "upload_file")
    WebElement uploadFileInput;

    @FindBy(name = "submit")
    WebElement submitButton;

    public PaginaContactUs(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void preencherFormulario(String name, String email, String subject, String message, String uploadFile) {
        nameInput.sendKeys(name);
        emailInput.sendKeys(email);
        subjectInput.sendKeys(subject);
        messageInput.sendKeys(message);
        uploadFileInput.sendKeys(uploadFile);
    }

    public void clickSubmit() {
        submitButton.click();
    }
}
