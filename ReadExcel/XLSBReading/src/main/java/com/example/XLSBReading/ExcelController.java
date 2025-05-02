package com.example.XLSBReading;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/excel")
public class ExcelController {

    @Autowired
    private XlsbReaderService excelReadingService;

    @GetMapping("/find-by-name")
    public List<String> getRowsByName(@RequestParam String name) {
        String filePath = "C:\\Users\\Sreenivas Bandaru\\Downloads\\SampleXLSFile_38kb.xlsb";
        List<String> result = excelReadingService.findRowsByName(filePath, name);

        log.info("===== Matching Records for: {} =====", name);
        result.forEach(System.out::println);

        return result;    }
}