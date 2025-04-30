package com.example.ReadingXlsFile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ExcelReaderController {

    @Autowired
    private ExcelReaderService excelReaderService;

    @GetMapping("/read-excel")
    public String readExcel() {
        try {
            // Provide the path to your .xls file here
            excelReaderService.readExcelFile("C:\\Users\\Sreenivas Bandaru\\Downloads\\tests-example.xls");
            return "File read successfully!";
        } catch (IOException e) {
            e.printStackTrace();
            return "Error reading file.";
        }
    }


    @GetMapping("/read-all-sheets")
    public String readAllSheets() {
        try {
            // Provide the path to your .xls file here
            excelReaderService.readAllSheetsWithNames("C:\\Users\\Sreenivas Bandaru\\Desktop\\SampleXLSFile_38kb.xls");
            return "Sheets read successfully!";
        } catch (IOException e) {
            e.printStackTrace();
            return "Error reading file.";
        }
    }




    @GetMapping("/convert-xls-to-xlsx")
    public String convertXlsToXlsx() {
        try {
            // Provide the source .xls file path and the target directory path
            excelReaderService.convertXlsToXlsx(
                    "C:\\Users\\Sreenivas Bandaru\\Desktop\\SampleXLSFile_38kb.xls",
                    "C:\\Users\\Sreenivas Bandaru\\Desktop\\New folder"
            );
            return "File converted successfully!";
        } catch (IOException e) {
            e.printStackTrace();
            return "Error converting file.";
        }
    }
}
