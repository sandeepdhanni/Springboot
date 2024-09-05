package com.example.exportdatafromdb_toexcel_and_pdf.service;

import com.example.exportdatafromdb_toexcel_and_pdf.entity.Employee;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;


import java.io.OutputStream;
import java.util.List;
import java.util.stream.Stream;

@Service
public class PDFGenerator {

    public void generatePDF(List<Employee> employees, OutputStream outputStream) throws DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);

        // Open the document
        document.open();

        // Add Title
        document.add(new Paragraph("Employee Details"));

        // Adds a new line after the title
        document.add(new Paragraph(" "));  // This creates a blank line

        //this will create a table with 3 columns (for ID, Name,Department and salary)
        PdfPTable table = new PdfPTable(4);
        table.addCell("ID");
        table.addCell("Name");
        table.addCell("Department");
        table.addCell("Salary");

        // Check if the employees list is not empty
        if (employees != null && !employees.isEmpty()) {
            // Loop through the employees and add them to the table
            for (Employee employee : employees) {
                table.addCell(String.valueOf(employee.getId()));
                table.addCell(employee.getName());
                table.addCell(employee.getDepartment());
                table.addCell(String.valueOf(employee.getSalary()));
            }
        } else {
            // if there are no employees, this will add a message to the PDF
            document.add(new Paragraph("No employee data available."));
        }

        //this will add the table to the document
        document.add(table);

        //this will close the document after adding all content
        document.close();
    }
}