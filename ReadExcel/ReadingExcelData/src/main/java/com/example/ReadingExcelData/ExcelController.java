package com.example.ReadingExcelData;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/excel")
public class ExcelController {

    @Autowired
    private ExcelService excelService;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            return excelService.readExcelFile(file);
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
            return "Error processing the file: " + e.getMessage();
        }
    }
}
