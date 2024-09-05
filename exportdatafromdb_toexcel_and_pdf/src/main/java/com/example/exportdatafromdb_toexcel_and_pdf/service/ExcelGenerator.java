package com.example.exportdatafromdb_toexcel_and_pdf.service;

import com.example.exportdatafromdb_toexcel_and_pdf.entity.Employee;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Service
public class ExcelGenerator {

    public void generateExcel(List<Employee> employees, OutputStream outputStream) throws IOException {
        // Create a workbook
        Workbook workbook = new XSSFWorkbook();
        // Create a sheet
        Sheet sheet = workbook.createSheet("Employee Details");

        // Create a header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Name");
        headerRow.createCell(2).setCellValue("Department");
        headerRow.createCell(3).setCellValue("Salary");

        // Check if the employees list is not empty
        if (employees != null && !employees.isEmpty()) {
            // Loop through the employees and add them to the rows
            int rowNum = 1;  // Start from the second row
            for (Employee employee : employees) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(employee.getId());
                row.createCell(1).setCellValue(employee.getName());
                row.createCell(2).setCellValue(employee.getDepartment());
                row.createCell(3).setCellValue(employee.getSalary());
            }
        } else {
            // If there are no employees, add a message in the first row
            Row row = sheet.createRow(1);
            row.createCell(0).setCellValue("No employee data available.");
        }

        // Adjust column width for readability
        for (int i = 0; i < 4; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write the output to the OutputStream
        workbook.write(outputStream);

        // Close the workbook
        workbook.close();
    }
}
