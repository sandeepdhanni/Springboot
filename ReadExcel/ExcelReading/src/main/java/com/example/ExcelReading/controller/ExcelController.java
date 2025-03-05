package com.example.ExcelReading.controller;

import com.example.ExcelReading.commonResponse.CommonResponse;
import com.example.ExcelReading.service.ExcelService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/excel")
public class ExcelController {

    @Autowired
    private ExcelService excelService;

    @PostMapping("/read")
    public ResponseEntity<?> readExcel(@RequestParam("file") MultipartFile file) {
        try {
            return excelService.readExcel(file);
        } catch (Exception e) {
            log.error("Unexpected error while reading the Excel file", e);
            return new CommonResponse<>().prepareErrorResponseObject("Failed to read Excel file",
                    org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
