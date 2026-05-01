package com.automationexercise;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Laboratorio2 {
    String urlTest = "https://automationexercise.com/";

    @Test
    public void lab2_E2() {
        // Implementar test de registro de usuario
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.get(urlTest);
        driver.manage().window().maximize();
        
        // Clic en Sign Up / Login
        WebElement lnkSignUpLogin = driver.findElement(By.partialLinkText("Signup / Log"));
        lnkSignUpLogin.click();

        // Completar el Name e Email address
        WebElement txtName = driver.findElement(By.name("name"));
        txtName.sendKeys("John Doe");

        WebElement txtEmail = driver.findElement(By.cssSelector("input[data-qa='signup-email']"));
        txtEmail.sendKeys("john.doe@myexample.com");

        WebElement btnSignUp = driver.findElement(By.xpath("//button[@data-qa='signup-button']"));
        btnSignUp.click();

        // Completar el formulario de registro
        // Password
        WebElement txtPassword = driver.findElement(By.id("password"));
        txtPassword.sendKeys("password123");  
        
        // Date of Birth
        Select selectDay = new Select(driver.findElement(By.id("days")));
        selectDay.selectByValue("15");

        Select selectMonth = new Select(driver.findElement(By.id("months")));
        selectMonth.selectByValue("5");

        Select selectYear = new Select(driver.findElement(By.id("years")));
        selectYear.selectByValue("1990");

        // Checkboxes de Newsletter y Offers
        WebElement chkNewsletter = driver.findElement(By.id("newsletter"));
        chkNewsletter.click();

        WebElement chkOffers = driver.findElement(By.id("optin"));
        chkOffers.click();

        // Addres Information
        WebElement txtFirstName = driver.findElement(By.id("first_name"));
        txtFirstName.sendKeys("John");

        WebElement txtLastName = driver.findElement(By.id("last_name"));
        txtLastName.sendKeys("Doe");        

        WebElement txtCompany = driver.findElement(By.id("company"));
        txtCompany.sendKeys("John Doe's Company");

        WebElement txtAddress1 = driver.findElement(By.id("address1"));
        txtAddress1.sendKeys("123 Main Street");

        WebElement txtAddress2 = driver.findElement(By.id("address2"));
        txtAddress2.sendKeys("Apt 4B");

        Select lstCountries = new Select(driver.findElement(By.id("country")));
        lstCountries.selectByVisibleText("United States");

        WebElement txtState = driver.findElement(By.id("state"));
        txtState.sendKeys("New York");

        WebElement txtCity = driver.findElement(By.id("city"));
        txtCity.sendKeys("New York");

        WebElement txtZipcode = driver.findElement(By.id("zipcode"));
        txtZipcode.sendKeys("10001");

        WebElement txtMobileNumber = driver.findElement(By.id("mobile_number"));
        txtMobileNumber.sendKeys("1234567890");

        // Clic en el botón de registro
        WebElement btnRegister = driver.findElement(By.xpath("//button[@type='submit']"));
        btnRegister.click();

        //driver.quit();
    }
}
