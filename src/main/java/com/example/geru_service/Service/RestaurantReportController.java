package com.example.geru_service.Service;

import com.example.geru_service.Service.OwnerReport;
import com.example.geru_service.Service.ReportService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/restaurants")
public class RestaurantReportController {
    @Autowired
    private ReportService reportService;

    @GetMapping("/{id}/owner-report.xlsx")
    public void exportOwnerReportExcel(@PathVariable("id") Long restaurantId,
                                       HttpServletResponse response) throws IOException {
        OwnerReport report = reportService.generateReport(restaurantId);
        Workbook workbook = new XSSFWorkbook();

        // 1. Барааны бүрэн жагсаалт
        Sheet itemsSheet = workbook.createSheet("Барааны Борлуулалт");
        int rowIdx = 0;
        Row header = itemsSheet.createRow(rowIdx++);
        header.createCell(0).setCellValue("Барааны нэр");
        header.createCell(1).setCellValue("Борлуулсан тоо");
        header.createCell(2).setCellValue("Орлого");

        List<ItemSales> items = report.getItemsSales();
        for (ItemSales item : items) {
            Row row = itemsSheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(item.getItemName());
            row.createCell(1).setCellValue(item.getQuantity());
            row.createCell(2).setCellValue(item.getRevenue());
        }
        for (int i = 0; i < 3; i++) itemsSheet.autoSizeColumn(i);

        // 2. Товч тайлан
        Sheet summarySheet = workbook.createSheet("Товч Тайлан");
        int sumIdx = 0;
        Row r1 = summarySheet.createRow(sumIdx++);
        r1.createCell(0).setCellValue("Ресторан");
        r1.createCell(1).setCellValue(report.getRestaurantName());

        Row r2 = summarySheet.createRow(sumIdx++);
        r2.createCell(0).setCellValue("Нийт захиалга");
        r2.createCell(1).setCellValue(report.getTotalOrders());

        Row r3 = summarySheet.createRow(sumIdx++);
        r3.createCell(0).setCellValue("Нийт орлого");
        r3.createCell(1).setCellValue(report.getTotalRevenue());

        sumIdx++;
        Row catHeader = summarySheet.createRow(sumIdx++);
        catHeader.createCell(0).setCellValue("Ангилал");
        catHeader.createCell(1).setCellValue("Орлого");

        for (Map.Entry<String, Long> entry : report.getRevenueByCategory().entrySet()) {
            Row row = summarySheet.createRow(sumIdx++);
            row.createCell(0).setCellValue(entry.getKey());
            row.createCell(1).setCellValue(entry.getValue());
        }
        for (int i = 0; i < 2; i++) summarySheet.autoSizeColumn(i);

        // Response setup
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        String filename = "owner-report-" + restaurantId + ".xlsx";
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");

        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
