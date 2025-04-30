package com.example.ReadingXlsFile;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class ExcelReaderService {

    //Read only the first sheet
    public void readExcelFile(String filePath) throws IOException {
        FileInputStream fis = new FileInputStream(new File(filePath));
        Workbook workbook = new HSSFWorkbook(fis);  // HSSFWorkbook is for .xls files
        Sheet sheet = workbook.getSheetAt(0);  // Get the first sheet

        // Iterate through the rows and columns of the sheet
        for (Row row : sheet) {
            for (Cell cell : row) {
                // Print out the cell value depending on the cell type
                switch (cell.getCellType()) {
                    case STRING:
                        System.out.print(cell.getStringCellValue() + "\t");
                        break;
                    case NUMERIC:
                        System.out.print(cell.getNumericCellValue() + "\t");
                        break;
                    case BOOLEAN:
                        System.out.print(cell.getBooleanCellValue() + "\t");
                        break;
                    default:
                        System.out.print("UNKNOWN\t");
                }
            }
            System.out.println(); // New line for each row
        }
        fis.close();
    }




    //will read all the sheets in the xls file
    public void readAllSheetsWithNames(String filePath) throws IOException {
        // Open the Excel file
        FileInputStream fis = new FileInputStream(new File(filePath));
        Workbook workbook = new HSSFWorkbook(fis); // For .xls files
        int numberOfSheets = workbook.getNumberOfSheets(); // Get the total number of sheets

        // Iterate through all the sheets in the workbook
        for (int i = 0; i < numberOfSheets; i++) {
            Sheet sheet = workbook.getSheetAt(i);  // Get each sheet
            String sheetName = sheet.getSheetName();  // Get sheet name
            System.out.println("Sheet Name: " + sheetName);

            // Iterate through the rows and columns of the sheet
            for (Row row : sheet) {
                for (Cell cell : row) {
                    // Print out the cell value depending on the cell type
                    switch (cell.getCellType()) {
                        case STRING:
                            System.out.print(cell.getStringCellValue() + "\t");
                            break;
                        case NUMERIC:
                            System.out.print(cell.getNumericCellValue() + "\t");
                            break;
                        case BOOLEAN:
                            System.out.print(cell.getBooleanCellValue() + "\t");
                            break;
                        default:
                            System.out.print("UNKNOWN\t");
                    }
                }
                System.out.println(); // New line for each row
            }
            System.out.println("==================================="); // Separator between sheets
        }
        fis.close();
    }























    public void convertXlsToXlsx(String sourceDirectory, String targetDirectory) throws IOException {
        // Get the source .xls file (assuming there's only one file in the source directory)
        File sourceFile = new File(sourceDirectory);
        String sourceFileName = sourceFile.getName();

        if (!sourceFileName.endsWith(".xls")) {
            throw new IllegalArgumentException("The provided file is not a valid .xls file.");
        }

        // Extract the file name without the extension
        String baseFileName = sourceFileName.substring(0, sourceFileName.lastIndexOf('.'));

        // Define the target .xlsx file path
        String targetFilePath = targetDirectory + File.separator + baseFileName + ".xlsx";

        // Open the .xls file (HSSFWorkbook for .xls files)
        FileInputStream fis = new FileInputStream(sourceFile);
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(fis); // For .xls files
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(); // For .xlsx files

        // Iterate over all sheets of the .xls workbook
        int numberOfSheets = hssfWorkbook.getNumberOfSheets();
        for (int i = 0; i < numberOfSheets; i++) {
            Sheet hssfSheet = hssfWorkbook.getSheetAt(i);  // Get the sheet from .xls
            Sheet xssfSheet = xssfWorkbook.createSheet(hssfSheet.getSheetName()); // Create a sheet in .xlsx with the same name

            // Copy rows and cells from the old .xls sheet to the new .xlsx sheet
            for (int rowIndex = 0; rowIndex <= hssfSheet.getLastRowNum(); rowIndex++) {
                Row hssfRow = hssfSheet.getRow(rowIndex);
                Row xssfRow = xssfSheet.createRow(rowIndex);

                if (hssfRow != null) {
                    for (int cellIndex = 0; cellIndex < hssfRow.getLastCellNum(); cellIndex++) {
                        Cell hssfCell = hssfRow.getCell(cellIndex);
                        Cell xssfCell = xssfRow.createCell(cellIndex);

                        // Copy the cell value from .xls to .xlsx
                        if (hssfCell != null) {
                            switch (hssfCell.getCellType()) {
                                case STRING:
                                    xssfCell.setCellValue(hssfCell.getStringCellValue());
                                    break;
                                case NUMERIC:
                                    xssfCell.setCellValue(hssfCell.getNumericCellValue());
                                    break;
                                case BOOLEAN:
                                    xssfCell.setCellValue(hssfCell.getBooleanCellValue());
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                }
            }
        }

        // Save the new .xlsx file to the specified target path
        FileOutputStream fos = new FileOutputStream(new File(targetFilePath));
        xssfWorkbook.write(fos);
        fos.close();
        fis.close();

        System.out.println("Conversion from .xls to .xlsx completed successfully. File saved at: " + targetFilePath);
    }
}
