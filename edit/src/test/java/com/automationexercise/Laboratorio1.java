package com.automationexercise;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Laboratorio1 {
    @Test
    public void holaMundo() {
        System.out.println("¡Hola Mundo de Automatización!");
    }

    @Test
    public void lab1_E2() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.get("https://automationexercise.com");
        driver.quit();
    }

    @Test
    public void lab1_E3() {
        WebDriverManager.firefoxdriver().setup();
        WebDriver driver = new FirefoxDriver();
        driver.get("https://automationexercise.com");
        driver.manage().window().maximize();
        driver.quit();
    }

    @Test
    public void lab1_E4() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.get("https://automationexercise.com/products");
        driver.manage().window().maximize();

        WebElement txtBuscar = driver.findElement(By.id("search_product"));
        txtBuscar.sendKeys("Tshirt");

        WebElement btnBuscar = driver.findElement(By.id("submit_search"));
        btnBuscar.click();
        
        driver.quit();
    }
}
