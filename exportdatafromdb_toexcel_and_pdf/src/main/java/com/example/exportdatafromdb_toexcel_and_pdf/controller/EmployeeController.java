package com.example.exportdatafromdb_toexcel_and_pdf.controller;

import com.example.exportdatafromdb_toexcel_and_pdf.entity.Employee;
import com.example.exportdatafromdb_toexcel_and_pdf.service.EmployeeService;
import com.example.exportdatafromdb_toexcel_and_pdf.service.ExcelGenerator;
import com.example.exportdatafromdb_toexcel_and_pdf.service.PDFGenerator;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
    @RequestMapping("/api/employees")
    public class EmployeeController {

        @Autowired
        private EmployeeService employeeService;

        @Autowired
        private ExcelGenerator excelGenerator;

        @Autowired
        private PDFGenerator pdfGenerator;

        @GetMapping("/export/excel")
        public void exportToExcel(HttpServletResponse response) throws IOException {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=employees.xlsx");

            // Fetch the list of employees
            List<Employee> employees = employeeService.getAllEmployees();

            // Using the response's output stream to pass to the Excel generator method
            ServletOutputStream outputStream = response.getOutputStream();
            excelGenerator.generateExcel(employees, outputStream);

            // Close the output stream
            outputStream.close();
        }

        @GetMapping("/export/pdf")
        public void exportToPDF(HttpServletResponse response) throws IOException, DocumentException {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=employees.pdf");

            List<Employee> employees = employeeService.getAllEmployees();
            // Use the output stream to write the PDF to the response
            pdfGenerator.generatePDF(employees, response.getOutputStream());
        }
    }


