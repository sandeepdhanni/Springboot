package com.example.XLSBReading;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.binary.XSSFBSharedStringsTable;
import org.apache.poi.xssf.binary.XSSFBSheetHandler;
import org.apache.poi.xssf.binary.XSSFBStylesTable;
import org.apache.poi.xssf.eventusermodel.XSSFBReader;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class XlsbReaderService {
    public List<String> findRowsByName(String filePath, String targetName) {
        List<String> matchingRows = new ArrayList<>();

        try (OPCPackage pkg = OPCPackage.open(filePath)) {
            XSSFBReader reader = new XSSFBReader(pkg);
            XSSFBSharedStringsTable sst = new XSSFBSharedStringsTable(pkg);
            XSSFBStylesTable styles = reader.getXSSFBStylesTable();
            XSSFBReader.SheetIterator sheets = (XSSFBReader.SheetIterator) reader.getSheetsData();

            while (sheets.hasNext()) {
                try (InputStream is = sheets.next()) {
                    XLSB2Lists sheetHandlerImpl = new XLSB2Lists();
                    sheetHandlerImpl.startSheet(sheets.getSheetName());

                    XSSFBSheetHandler sheetHandler = new XSSFBSheetHandler(
                            is, styles, sheets.getXSSFBSheetComments(),
                            sst, sheetHandlerImpl, new DataFormatter(), false
                    );
                    sheetHandler.parse();
                    sheetHandlerImpl.endSheet();

                    List<List<String>> data = sheetHandlerImpl.getSheetContentAsList();
                    if (data.isEmpty()) continue;

                    List<String> headers = data.get(0);
                    int nameColIndex = headers.indexOf("Name");

                    for (int i = 1; i < data.size(); i++) {
                        List<String> row = data.get(i);

                        boolean isEmptyRow = row.stream().allMatch(cell -> cell == null || cell.trim().isEmpty());
                        if (isEmptyRow) continue;

                        if (nameColIndex != -1 && row.size() > nameColIndex && row.get(nameColIndex).equalsIgnoreCase(targetName)) {
                            StringBuilder rowData = new StringBuilder();
                            for (int j = 0; j < headers.size(); j++) {
                                String header = headers.get(j).toUpperCase().replace(" ", "_");
                                String value = j < row.size() ? row.get(j) : "";
                                rowData.append(header).append(": ").append(value);
                                if (j < headers.size() - 1) rowData.append(", ");
                            }
                            matchingRows.add(rowData.toString());
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error reading file", e);
        }

        return matchingRows;
    }


}
