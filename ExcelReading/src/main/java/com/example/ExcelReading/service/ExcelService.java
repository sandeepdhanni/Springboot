package com.example.ExcelReading.service;

import com.example.ExcelReading.commonResponse.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@Slf4j
public class ExcelService {

    @Autowired
    private CommonResponse<Object> commonResponse;

    public ResponseEntity<?> readExcel(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            log.info("Reading Excel file: {}", file.getOriginalFilename());

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                List<String> rowData = new ArrayList<>();
                row.forEach(cell -> rowData.add(getCellValueAsString(cell)));
                log.info("Row Data: {}", rowData);
            }

            return commonResponse.prepareSuccessResponseObject("File read successfully!", HttpStatus.OK);

        } catch (Exception e) {
            log.error("Error reading Excel file: {}", e.getMessage());
            return commonResponse.prepareErrorResponseObject("Error reading Excel file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String getCellValueAsString(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return null;
        }
    }
}
