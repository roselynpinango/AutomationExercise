package com.automationexercise.pruebas;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.automationexercise.paginas.PaginaContactUs;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Laboratorio5_E1 {
    String url = "https://www.automationexercise.com/contact_us";
    WebDriver driver;

    @BeforeSuite
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get(url);
        driver.manage().window().maximize();
    }

    @Test
    public void testContactUs() {
        PaginaContactUs paginaContactUs = new PaginaContactUs(driver);
        paginaContactUs.preencherFormulario("John Doe", "john@myemail.com", "Title", "Message Text", "C:\\addIntegerData.txt");
        paginaContactUs.clickSubmit();
    }

    @AfterSuite
    public void tearDown() {
        //driver.quit();
    }
}
