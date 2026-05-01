package com.automationexercise.pruebas;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.automationexercise.paginas.PaginaCarrito;
import com.automationexercise.paginas.PaginaCatalogo;
import com.automationexercise.paginas.PaginaDetalleProducto;
import com.automationexercise.paginas.PaginaInicio;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Laboratorio4_E1B {
    String url = "https://www.automationexercise.com/";
    WebDriver driver;

    @BeforeSuite
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get(url);
        driver.manage().window().maximize();
    }

    @Test
    public void test() {
        PaginaInicio paginaInicio = new PaginaInicio(driver);
        paginaInicio.clickProducts();

        PaginaCatalogo paginaCatalogo = new PaginaCatalogo(driver);
        paginaCatalogo.clickProductDetail();

        PaginaDetalleProducto paginaDetalleProducto = new PaginaDetalleProducto(driver);
        paginaDetalleProducto.clickAddToCart();
        paginaDetalleProducto.clickContinueShopping();
        paginaDetalleProducto.clickViewCart();

        PaginaCarrito paginaCarrito = new PaginaCarrito(driver);
        String precio = paginaCarrito.obtenerPrecio();
        System.out.println("Precio del producto en el carrito: " + precio);
    }

    @AfterSuite
    public void tearDown() {
        //driver.quit();
    }
}
