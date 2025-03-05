package com.example.ReadingExcelData;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.binary.XSSFBReader;
import org.apache.poi.xssf.binary.XSSFBSheetHandler;
import org.apache.poi.xssf.binary.XSSFBSharedStringsTable;
import org.apache.poi.xssf.binary.XSSFBStylesTable;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.poifs.filesystem.FileMagic;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ExcelService {

    public String readExcelFile(MultipartFile file) throws IOException, InvalidFormatException {
        InputStream inputStream = file.getInputStream();
        FileMagic fileMagic = FileMagic.valueOf(inputStream);

        if (fileMagic == FileMagic.OLE2) {
            // It's an XLSB file, handle using XSSFBReader
            return readXLSBFile(file);
        } else if (fileMagic == FileMagic.OOXML) {
            // It's an XLSX file, handle using XSSFWorkbook
            return readXLSXFile(inputStream);
        } else {
            throw new IllegalArgumentException("Unsupported file format!");
        }
    }

    private String readXLSBFile(MultipartFile file) throws IOException, InvalidFormatException {
        OPCPackage pkg = OPCPackage.open(file.getInputStream());
        XSSFBReader reader = new XSSFBReader(pkg);
        XSSFBSharedStringsTable sharedStringsTable = new XSSFBSharedStringsTable(pkg);
        XSSFBStylesTable stylesTable = reader.getXSSFBStylesTable();

        XSSFBReader.SheetIterator sheetIterator = (XSSFBReader.SheetIterator) reader.getSheetsData();
        StringBuilder data = new StringBuilder();

        while (sheetIterator.hasNext()) {
            InputStream sheetStream = sheetIterator.next();
            String sheetName = sheetIterator.getSheetName();

            // Custom Sheet Handler to capture sheet content
            XLSBSheetHandler handler = new XLSBSheetHandler(sheetName);
            XSSFBSheetHandler sheetHandler = new XSSFBSheetHandler(
                    sheetStream,
                    stylesTable,
                    sheetIterator.getXSSFBSheetComments(),
                    sharedStringsTable,
                    handler,
                    new DataFormatter(),
                    false
            );
            try {
                sheetHandler.parse();
                data.append(handler.getSheetData());
            } catch (SAXException e) {
                throw new IOException("Error parsing XLSB sheet: " + e.getMessage(), e);
            }
        }
        return data.toString();
    }

    private String readXLSXFile(InputStream inputStream) throws IOException {
        Workbook workbook = new XSSFWorkbook(inputStream);
        return readDataFromWorkbook(workbook);
    }

    private String readDataFromWorkbook(Workbook workbook) {
        StringBuilder data = new StringBuilder();
        Sheet sheet = workbook.getSheetAt(0); // Read the first sheet

        for (Row row : sheet) {
            for (Cell cell : row) {
                data.append(getCellValue(cell)).append("\t");
            }
            data.append("\n");
        }
        return data.toString();
    }

    private String getCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }
}

class XLSBSheetHandler implements XSSFBSheetHandler.SheetContentsHandler {
    private final StringBuilder sheetData;
    private final String sheetName;

    public XLSBSheetHandler(String sheetName) {
        this.sheetName = sheetName;
        this.sheetData = new StringBuilder("Sheet: ").append(sheetName).append("\n");
    }

    @Override
    public void startRow(int rowNum) {
        sheetData.append("Row ").append(rowNum).append(": ");
    }

    @Override
    public void endRow(int rowNum) {
        sheetData.append("\n");
    }

    @Override
    public void cell(String cellReference, String formattedValue, XSSFBSheetHandler.XSSFComment comment) {
        sheetData.append(formattedValue).append("\t");
    }

    @Override
    public void endSheet() {
        sheetData.append("\n");
    }

    public String getSheetData() {
        return sheetData.toString();
    }
}
