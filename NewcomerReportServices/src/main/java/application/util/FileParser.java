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

    /**
     * Reads and parses an excel spreadsheet of the extension .xslx
     * @param filePath the path of the file
     * @param sheetName the name of the sheet to parse
     * @return an ArrayList with a hashmap for each row of the sheet, mapping columnName to value
     */
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

        return readFile(filePath, workbook.getSheetIndex(sheetName));
    }

    private static ArrayList<HashMap<String, String>> readFile(String filePath, int index) {
        FileInputStream file = null;

        // Open the file
        try {
            file = new FileInputStream(new File(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return readWorkbook(file, index);
    }

    private static ArrayList<HashMap<String, String>> readWorkbook(FileInputStream file, int index) {
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

        return readData(sheet);
    }

    private static ArrayList<HashMap<String, String>> readData(XSSFSheet sheet) {
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