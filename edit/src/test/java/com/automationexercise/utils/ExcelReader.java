package com.automationexercise.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
    
    /**
     * Lee datos del archivo Excel y los retorna como arreglo de objetos
     * @param rutaExcel Ruta del archivo Excel
     * @param nombreSheet Nombre de la hoja a leer
     * @return Matriz de objetos con los datos del Excel
     * @throws IOException Si hay error al leer el archivo
     */
    public static Object[][] leerExcel(String rutaExcel, String nombreSheet) throws IOException {
        FileInputStream fis = new FileInputStream(new File(rutaExcel));
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheet(nombreSheet);
        
        int filas = sheet.getPhysicalNumberOfRows() - 1; // Restar 1 por el header
        int columnas = sheet.getRow(0).getPhysicalNumberOfCells();
        Object[][] datos = new Object[filas][columnas];
        
        int dataIndex = 0;
        for (int i = 1; i <= filas; i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                for (int j = 0; j < columnas; j++) {
                    datos[dataIndex][j] = row.getCell(j).getStringCellValue();
                }
                dataIndex++;
            }
        }
        
        workbook.close();
        fis.close();
        return datos;
    }
}
