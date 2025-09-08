package com.saucedemo.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.*;

public class ExcelUtilities {

    public static String[][] getData(String excelPath, String sheetName) throws IOException {
        File file = new File(excelPath);
        FileInputStream fis = new FileInputStream(file);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheet(sheetName);

        int rows = sheet.getPhysicalNumberOfRows();
        int cols = sheet.getRow(0).getPhysicalNumberOfCells();

        String[][] data = new String[rows - 1][cols];

        for (int i = 1; i < rows; i++) { // assume first row is header
            XSSFRow row = sheet.getRow(i);
            for (int j = 0; j < cols; j++) {
                XSSFCell cell = row.getCell(j);
                data[i - 1][j] = cell == null ? "" : cell.toString();
            }
        }
        workbook.close();
        fis.close();
        return data;
    }
}
