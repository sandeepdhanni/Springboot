package com.example.exportdatafromdb_toexcel_and_pdf.service;

import com.example.exportdatafromdb_toexcel_and_pdf.entity.Employee;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Stream;

@Service
public class PDFGenerator {

    public Document generatePDF(List<Employee> employees) throws DocumentException {
        Document document = new Document();
        PdfPTable table = new PdfPTable(4);
        addTableHeader(table);
        addRows(table, employees);
        document.add(table);
        return document;
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("ID", "Name", "Department", "Salary").forEach(columnTitle -> {
            PdfPCell header = new PdfPCell();
            header.setBackgroundColor(BaseColor.LIGHT_GRAY);
            header.setBorderWidth(2);
            header.setPhrase(new Phrase(columnTitle));
            table.addCell(header);
        });
    }

    private void addRows(PdfPTable table, List<Employee> employees) {
        for (Employee employee : employees) {
            table.addCell(employee.getId().toString());
            table.addCell(employee.getName());
            table.addCell(employee.getDepartment());
            table.addCell(employee.getSalary().toString());
        }
    }
}
