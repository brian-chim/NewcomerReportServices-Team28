package application.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Class to parse excel files into HashMap<String, String>
 */
public class FileParser {
    private static int columnHiddenNameIndex = 1;
    private static int columnValueIndex = 3;


    public static ArrayList<HashMap<String, String>> readSpreadsheet(String filePath, String sheetName) {
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

    public static ArrayList<HashMap<String, String>> readSpreadsheet(FileInputStream file, String sheetName) {
        XSSFWorkbook workbook = null;

        // Get the workbook instance for XLS file
        try {
            workbook = new XSSFWorkbook(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return readSpreadsheet(file, workbook.getSheetIndex(sheetName));
    }

    public static ArrayList<HashMap<String, String>> readSpreadsheet(String filePath, int index) {
        FileInputStream file = null;

        // Open the file
        try {
            file = new FileInputStream(new File(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return readSpreadsheet(file, index);
    }

    public static ArrayList<HashMap<String, String>> readSpreadsheet(FileInputStream file, int index) {
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

    private static ArrayList<HashMap<String, String>> readSpreadsheet(XSSFSheet sheet) {
        ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
        DataFormatter formatter = new DataFormatter();
        // Use an iterator to go through the rows
        Iterator<Row> rowIterator = sheet.iterator();
        // skip the first 3 rows
        rowIterator.next();
        rowIterator.next();
        rowIterator.next();
        // Iterate through the rows
        while (rowIterator.hasNext()) {
            HashMap <String, String> map = new HashMap<String, String>();
            Row row = rowIterator.next();
            if (formatter.formatCellValue(row.getCell(0)).equals("")) {
                break;
            }
            // For each row in the sheet, iterate through each columns
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {

                Cell cell = cellIterator.next();
                //cell = row.getCell(cell.getColumnIndex());
                String column = formatter.formatCellValue(sheet.getRow(columnHiddenNameIndex).getCell(cell.getColumnIndex()));
                column = column.replace("[", "_").replace("]", "_");
                // add each cell as columnName: value to the hashmap
                map.put(column, formatter.formatCellValue(cell));

            }
            result.add(map);
        }
        return result;
    }
}