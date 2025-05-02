package com.example.XLSBReading;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.binary.XSSFBSharedStringsTable;
import org.apache.poi.xssf.binary.XSSFBSheetHandler;
import org.apache.poi.xssf.binary.XSSFBStylesTable;
import org.apache.poi.xssf.eventusermodel.XSSFBReader;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class XlsbFileReading {
    public static void main(String[] args) {
        String xlsbFileName = "C:\\Users\\Sreenivas Bandaru\\Downloads\\SampleXLSFile_38kb.xlsb";
        callXLToList(xlsbFileName);
    }

    static void callXLToList(String xlsbFileName) {
        try (OPCPackage pkg = OPCPackage.open(xlsbFileName)) {
            XSSFBReader reader = new XSSFBReader(pkg);
            XSSFBSharedStringsTable sst = new XSSFBSharedStringsTable(pkg);
            XSSFBStylesTable stylesTable = reader.getXSSFBStylesTable();
            XSSFBReader.SheetIterator sheetIterator = (XSSFBReader.SheetIterator) reader.getSheetsData();

            List<XLSB2Lists> workbookAsList = new ArrayList<>();
            int sheetNr = 1;

            while (sheetIterator.hasNext()) {
                try (InputStream is = sheetIterator.next()) {
                    String sheetName = sheetIterator.getSheetName();
                    log.info("Begin parsing sheet {}: {}", sheetNr, sheetName);

                    XLSB2Lists sheetHandlerImpl = new XLSB2Lists();
                    sheetHandlerImpl.startSheet(sheetName);

                    XSSFBSheetHandler sheetHandler = new XSSFBSheetHandler(
                            is,
                            stylesTable,
                            sheetIterator.getXSSFBSheetComments(),
                            sst,
                            sheetHandlerImpl,
                            new DataFormatter(),
                            false
                    );
                    sheetHandler.parse();
                    sheetHandlerImpl.endSheet();

                    workbookAsList.add(sheetHandlerImpl);

                    log.info("End parsing sheet {}: {}", sheetNr, sheetName);
                    sheetNr++;
                }
            }

            // Print data from all sheets in "Header: Value" format
            for (XLSB2Lists sheet : workbookAsList) {
                log.info("Sheet Name: {}", sheet.getMapOfInfo().get("sheetName"));
                List<List<String>> content = sheet.getSheetContentAsList();
                if (content.isEmpty()) {
                    log.info("No data found in sheet.");
                    continue;
                }

                List<String> headers = content.get(0); // Row 0 is header
                for (int i = 1; i < content.size(); i++) {
                    List<String> row = content.get(i);

                    // Skip empty rows (all cells are empty or blank)
                    boolean isEmptyRow = row.stream().allMatch(cell -> cell == null || cell.trim().isEmpty());
                    if (isEmptyRow) continue;

                    StringBuilder rowOutput = new StringBuilder();
                    for (int j = 0; j < headers.size(); j++) {
                        String header = headers.get(j).trim().toUpperCase().replace(" ", "_");
                        String value = j < row.size() ? row.get(j) : "";
                        rowOutput.append(header).append(": ").append(value);
                        if (j < headers.size() - 1) {
                            rowOutput.append(", ");
                        }
                    }
                    log.info(rowOutput.toString());
                }

            }

        } catch (IOException | OpenXML4JException | SAXException e) {
            log.error("Error while processing the XLSB file: {}", e.getMessage(), e);
        }
    }
}
