package com.login.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.login.model.Sell;
import com.login.repository.SellRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
public class ReportController {

    @Autowired
    private SellRepository sellRepository; // Assuming you have a SellRepository to fetch Sell data

    @PostMapping("/generateReport")
    public void generateReport(@RequestParam String transactionStartDate,@RequestParam String transactionEndDate, HttpServletResponse response) throws IOException {
        
    	LocalDate date = LocalDate.parse(transactionStartDate);
        String formattedStartDate1 = date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        
        LocalDate date1 = LocalDate.parse(transactionEndDate);
        String formattedStartDate2 = date1.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        
    	System.out.println("Formatted report date: "+formattedStartDate1);
    	List<Sell> sells = sellRepository.findSellinfoByDate(formattedStartDate1,formattedStartDate2); // Fetch all sell records from the repository

        // Create a workbook and a sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sales Report");

        // Create header row
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Sell ID");
        header.createCell(1).setCellValue("Transaction Value");
        header.createCell(2).setCellValue("CGST");
        header.createCell(3).setCellValue("SGST");
        header.createCell(4).setCellValue("GST");
        header.createCell(5).setCellValue("Total with GST");
        header.createCell(6).setCellValue("Invoice No");
        header.createCell(7).setCellValue("Customer Name");
        header.createCell(8).setCellValue("Customer Address");
        header.createCell(9).setCellValue("Transaction Date");

        // Create data rows
        int rowNum = 1;
        for (Sell sell : sells) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(sell.getSellId());
            row.createCell(1).setCellValue(sell.getTransanctionValue());
            row.createCell(2).setCellValue(sell.getCgst());
            row.createCell(3).setCellValue(sell.getSgst());
            row.createCell(4).setCellValue(sell.getGst());
            row.createCell(5).setCellValue(sell.getTotalwithGst());
            row.createCell(6).setCellValue(sell.getInvoiceNo());
            row.createCell(7).setCellValue(sell.getCustName());
            row.createCell(8).setCellValue(sell.getCustAddress());
            row.createCell(9).setCellValue(sell.getTransDate());
        }

        // Auto-size columns
        for (int i = 0; i < 10; i++) {
            sheet.autoSizeColumn(i);
        }

        // Set response headers
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=sales_report.xlsx");

        // Write to response output stream
        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
