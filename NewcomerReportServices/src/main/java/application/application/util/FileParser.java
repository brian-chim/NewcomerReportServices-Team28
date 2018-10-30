package application.application.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Class to parse excel files into HashMap<String, String>
 */
public class FileParser {
    private static int columnHiddenNameIndex = 1;
    private static int columnValueIndex = 3;


    public static HashMap<String, String> readSpreadsheet(String filePath, String sheetName) {
        FileInputStream file = null;

        // Open the file
        try {
            file = new FileInputStream(new File(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        XSSFWorkbook workbook = null;
        // Get the workbook instance for XLS file
        try {
            workbook = new XSSFWorkbook(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return readSpreadsheet(filePath, workbook.getSheetIndex(sheetName));
    }

    public static HashMap<String, String> readSpreadsheet(FileInputStream file, String sheetName) {
        XSSFWorkbook workbook = null;

        // Get the workbook instance for XLS file
        try {
            workbook = new XSSFWorkbook(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return readSpreadsheet(file, workbook.getSheetIndex(sheetName));
    }

    public static HashMap<String, String> readSpreadsheet(String filePath, int index) {
        FileInputStream file = null;

        // Open the file
        try {
            file = new FileInputStream(new File(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return readSpreadsheet(file, index);
    }

    public static HashMap<String, String> readSpreadsheet(FileInputStream file, int index) {
        XSSFWorkbook workbook = null;

        // Get the workbook instance for XLS file
        try {
            workbook = new XSSFWorkbook(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Get first sheet from the workbook
        XSSFSheet sheet = workbook.getSheetAt(index);

        // Close file
        try {
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return readSpreadsheet(sheet);
    }

    private static HashMap<String, String> readSpreadsheet(XSSFSheet sheet) {
        HashMap <String, String> map = new HashMap <String, String>();

        // Iterate through each row
        DataFormatter formatter = new DataFormatter();
        int rowEnd = sheet.getLastRowNum();
        for (int i = 0; i < rowEnd; i++) {
            // If header is empty, then end of file
            if (formatter.formatCellValue(sheet.getRow(columnHiddenNameIndex).getCell(i)).equals("")) {
                break;
            }

            // Get the values
            String column = formatter.formatCellValue(sheet.getRow(columnHiddenNameIndex).getCell(i));
            String value = formatter.formatCellValue(sheet.getRow(columnValueIndex).getCell(i));

            map.put(column, value);
        }
        return map;
    }
}