package org.gp.civiceye.service.impl;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import org.gp.civiceye.service.ChartGenerationService;
import org.gp.civiceye.service.PDFReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class PDFReportServiceImpl implements PDFReportService {

    private final ChartGenerationService chartGenerationService;

    // Color constants for consistent theming
    private static final DeviceRgb PRIMARY_COLOR = new DeviceRgb(41, 128, 185);
    private static final DeviceRgb SUCCESS_COLOR = new DeviceRgb(39, 174, 96);
    private static final DeviceRgb DANGER_COLOR = new DeviceRgb(231, 76, 60);
    private static final DeviceRgb WARNING_COLOR = new DeviceRgb(241, 196, 15);
    private static final DeviceRgb INFO_COLOR = new DeviceRgb(52, 152, 219);
    private static final DeviceRgb PURPLE_COLOR = new DeviceRgb(155, 89, 182);
    private static final DeviceRgb DARK_COLOR = new DeviceRgb(52, 73, 94);
    private static final DeviceRgb LIGHT_GRAY = new DeviceRgb(248f/255f, 249f/255f, 250f/255f);

    @Autowired
    public PDFReportServiceImpl(ChartGenerationService chartGenerationService) {
        this.chartGenerationService = chartGenerationService;
    }

    @Override
    public byte[] generateReportPDF(List<Map<String, Object>> reportData) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc, PageSize.A4);

            // Set margins
            document.setMargins(40, 40, 40, 40);

            // Build PDF sections
            addHeader(document);
            addSummarySection(document, reportData);
            addChartsSection(document, reportData);
            addDetailedTable(document, reportData);
            addInsightsSection(document, reportData);
            addFooter(document);

            document.close();
            return baos.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PDF report", e);
        }
    }

    private void addHeader(Document document) throws IOException {
        PdfFont boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
        PdfFont regularFont = PdfFontFactory.createFont(StandardFonts.HELVETICA);

        // Main title
        Paragraph title = new Paragraph("CIVIC EYE REPORT ANALYSIS")
                .setFont(boldFont)
                .setFontSize(24)
                .setTextAlignment(TextAlignment.CENTER)
                .setFontColor(PRIMARY_COLOR)
                .setMarginBottom(10);
        document.add(title);

        // Subtitle
        Paragraph subtitle = new Paragraph("Department Performance Overview")
                .setFont(regularFont)
                .setFontSize(14)
                .setTextAlignment(TextAlignment.CENTER)
                .setFontColor(ColorConstants.GRAY)
                .setMarginBottom(20);
        document.add(subtitle);

        // Date and time
        String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy - HH:mm"));
        Paragraph date = new Paragraph("Generated on: " + currentDate)
                .setFont(regularFont)
                .setFontSize(10)
                .setTextAlignment(TextAlignment.RIGHT)
                .setMarginBottom(30);
        document.add(date);

        // Separator line
        addSeparatorLine(document, 20);
    }

    private void addSummarySection(Document document, List<Map<String, Object>> data) throws IOException {
        PdfFont boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

        // Section title
        addSectionTitle(document, "EXECUTIVE SUMMARY");

        // Calculate summary statistics
        int totalReports = data.stream()
                .mapToInt(item -> Integer.parseInt(item.get("reportCount").toString()))
                .sum();
        int totalDepartments = data.size();
        int avgReportsPerDept = totalDepartments > 0 ? totalReports / totalDepartments : 0;

        // Find highest and lowest performing departments
        Map<String, Object> highest = data.isEmpty() ? null : data.get(0);
        Map<String, Object> lowest = data.isEmpty() ? null : data.get(data.size() - 1);

        // Summary cards in a table layout
        Table summaryTable = new Table(new float[]{1, 1, 1})
                .setWidth(UnitValue.createPercentValue(100))
                .setMarginBottom(20);

        summaryTable.addCell(createSummaryCard("Total Reports", String.valueOf(totalReports), INFO_COLOR));
        summaryTable.addCell(createSummaryCard("Departments", String.valueOf(totalDepartments), SUCCESS_COLOR));
        summaryTable.addCell(createSummaryCard("Avg per Dept", String.valueOf(avgReportsPerDept), PURPLE_COLOR));

        document.add(summaryTable);

        // Performance comparison (only if we have data)
        if (highest != null && lowest != null) {
            Table performanceTable = new Table(new float[]{1, 1})
                    .setWidth(UnitValue.createPercentValue(100))
                    .setMarginBottom(30);

            performanceTable.addCell(createPerformanceCard("Top Performer",
                    cleanDepartmentName(highest.get("department").toString()),
                    highest.get("reportCount").toString() + " reports",
                    SUCCESS_COLOR));

            performanceTable.addCell(createPerformanceCard("Needs Attention",
                    cleanDepartmentName(lowest.get("department").toString()),
                    lowest.get("reportCount").toString() + " reports",
                    DANGER_COLOR));

            document.add(performanceTable);
        }
    }

    private void addChartsSection(Document document, List<Map<String, Object>> data) throws IOException {
        addSectionTitle(document, "VISUAL ANALYSIS");

        if (data.isEmpty()) {
            document.add(new Paragraph("No data available for chart generation.")
                    .setFontColor(ColorConstants.GRAY)
                    .setMarginBottom(30));
            return;
        }

        try {
            // Generate charts
            byte[] barChartBytes = chartGenerationService.generateBarChart(data, 1200, 720);
            byte[] pieChartBytes = chartGenerationService.generatePieChart(data, 1200, 720);

            // Add charts in a table layout
            Table chartTable = new Table(new float[]{1, 1})
                    .setWidth(UnitValue.createPercentValue(100))
                    .setMarginBottom(30);

            // Bar chart cell
            Cell barChartCell = createChartCell("Reports by Department", barChartBytes);

            // Pie chart cell
            Cell pieChartCell = createChartCell("Distribution Overview", pieChartBytes);

            chartTable.addCell(barChartCell);
            chartTable.addCell(pieChartCell);

            document.add(chartTable);

        } catch (Exception e) {
            // Fallback if charts fail
            document.add(new Paragraph("Charts could not be generated due to technical issues.")
                    .setFontColor(DANGER_COLOR)
                    .setMarginBottom(30));
        }
    }

    private void addDetailedTable(Document document, List<Map<String, Object>> data) throws IOException {
        PdfFont boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
        PdfFont regularFont = PdfFontFactory.createFont(StandardFonts.HELVETICA);

        addSectionTitle(document, "DETAILED BREAKDOWN");

        if (data.isEmpty()) {
            document.add(new Paragraph("No data available for detailed breakdown.")
                    .setFontColor(ColorConstants.GRAY)
                    .setMarginBottom(30));
            return;
        }

        // Create table
        Table table = new Table(new float[]{4, 1.5f, 1.5f, 2})
                .setWidth(UnitValue.createPercentValue(100));

        // Header row
        addTableHeader(table, "Department", boldFont);
        addTableHeader(table, "Reports", boldFont);
        addTableHeader(table, "Percentage", boldFont);
        addTableHeader(table, "Status", boldFont);

        // Calculate total for percentage and determine status thresholds
        int total = data.stream()
                .mapToInt(item -> Integer.parseInt(item.get("reportCount").toString()))
                .sum();

        double avgReports = data.stream()
                .mapToInt(item -> Integer.parseInt(item.get("reportCount").toString()))
                .average()
                .orElse(0.0);

        // Data rows
        for (int i = 0; i < data.size(); i++) {
            Map<String, Object> item = data.get(i);
            int count = Integer.parseInt(item.get("reportCount").toString());
            double percentage = total > 0 ? (double) count / total * 100 : 0;

            DeviceRgb rowColor = i % 2 == 0 ? LIGHT_GRAY : new DeviceRgb(1.0f, 1.0f, 1.0f);
            String status = getPerformanceStatus(count, avgReports);
            DeviceRgb statusColor = getStatusColor(status);

            // Department name
            table.addCell(createDataCell(cleanDepartmentName(item.get("department").toString()),
                    rowColor, TextAlignment.LEFT));

            // Report count
            table.addCell(createDataCell(String.valueOf(count), rowColor, TextAlignment.CENTER));

            // Percentage
            table.addCell(createDataCell(String.format("%.1f%%", percentage), rowColor, TextAlignment.CENTER));

            // Status with color
            Cell statusCell = createDataCell(status, rowColor, TextAlignment.CENTER);
            statusCell.setFontColor(statusColor);
            table.addCell(statusCell);
        }

        document.add(table);
        document.add(new Paragraph().setMarginBottom(20));
    }

    private void addInsightsSection(Document document, List<Map<String, Object>> data) throws IOException {
        if (data.isEmpty()) return;

        addSectionTitle(document, "KEY INSIGHTS");

        PdfFont regularFont = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        PdfFont boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

        // Calculate insights
        int totalReports = data.stream()
                .mapToInt(item -> Integer.parseInt(item.get("reportCount").toString()))
                .sum();

        double avgReports = data.stream()
                .mapToInt(item -> Integer.parseInt(item.get("reportCount").toString()))
                .average()
                .orElse(0.0);

        Map<String, Object> topDept = data.get(0);
        Map<String, Object> bottomDept = data.get(data.size() - 1);

        int topCount = Integer.parseInt(topDept.get("reportCount").toString());
        int bottomCount = Integer.parseInt(bottomDept.get("reportCount").toString());

        // Create insights list
        // Create insights list
        com.itextpdf.layout.element.List insights = new com.itextpdf.layout.element.List()
                .setSymbolIndent(12)
                .setListSymbol("•")
                .setFont(regularFont)
                .setFontSize(11);

        // Add insight items
        insights.add(new ListItem(String.format("Total of %d reports received across %d departments",
                totalReports, data.size())));

        insights.add(new ListItem(String.format("Average reports per department: %.1f", avgReports)));

        insights.add(new ListItem(String.format("%s leads with %d reports (%s of total)",
                cleanDepartmentName(topDept.get("department").toString()),
                topCount,
                String.format("%.1f%%", (double) topCount / totalReports * 100))));

        if (topCount > bottomCount * 2) {
            insights.add(new ListItem(String.format("Significant disparity: Top department has %dx more reports than lowest",
                    topCount / Math.max(bottomCount, 1))));
        }

        long highPerformers = data.stream()
                .mapToInt(item -> Integer.parseInt(item.get("reportCount").toString()))
                .filter(count -> count > avgReports)
                .count();

        insights.add(new ListItem(String.format("%d departments are above average performance", highPerformers)));

        document.add(insights);
        document.add(new Paragraph().setMarginBottom(20));
    }

    private void addFooter(Document document) throws IOException {
        PdfFont regularFont = PdfFontFactory.createFont(StandardFonts.HELVETICA);

        document.add(new Paragraph().setMarginTop(30));
        addSeparatorLine(document, 10);

        Paragraph footer = new Paragraph("This report was automatically generated by Civic Eye System. " +
                "For questions or concerns, please contact the system administrator.")
                .setFont(regularFont)
                .setFontSize(8)
                .setTextAlignment(TextAlignment.CENTER)
                .setFontColor(ColorConstants.GRAY);

        document.add(footer);
    }

    // Helper methods

    private void addSectionTitle(Document document, String title) throws IOException {
        PdfFont boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

        Paragraph sectionTitle = new Paragraph(title)
                .setFont(boldFont)
                .setFontSize(16)
                .setFontColor(PRIMARY_COLOR)
                .setMarginBottom(15);
        document.add(sectionTitle);
    }

    private void addSeparatorLine(Document document, float marginBottom) {
        document.add(new Paragraph().setBorder(Border.NO_BORDER)
                .setBorderBottom(new com.itextpdf.layout.borders.SolidBorder(ColorConstants.LIGHT_GRAY, 1))
                .setMarginBottom(marginBottom));
    }

    private Cell createSummaryCard(String title, String value, DeviceRgb color) throws IOException {
        PdfFont boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
        PdfFont regularFont = PdfFontFactory.createFont(StandardFonts.HELVETICA);

        Cell cell = new Cell()
                .setBorder(Border.NO_BORDER)
                .setBackgroundColor(LIGHT_GRAY)
                .setPadding(15)
                .setTextAlignment(TextAlignment.CENTER);

        Paragraph titleP = new Paragraph(title)
                .setFont(regularFont)
                .setFontSize(12)
                .setFontColor(color)
                .setMarginBottom(5);

        Paragraph valueP = new Paragraph(value)
                .setFont(boldFont)
                .setFontSize(24)
                .setFontColor(ColorConstants.BLACK);

        cell.add(titleP);
        cell.add(valueP);
        return cell;
    }

    private Cell createPerformanceCard(String title, String department, String count, DeviceRgb color) throws IOException {
        PdfFont boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
        PdfFont regularFont = PdfFontFactory.createFont(StandardFonts.HELVETICA);

        Cell cell = new Cell()
                .setBorder(Border.NO_BORDER)
                .setBackgroundColor(LIGHT_GRAY)
                .setPadding(15);

        Paragraph titleP = new Paragraph(title)
                .setFont(boldFont)
                .setFontSize(14)
                .setFontColor(color)
                .setMarginBottom(5);

        Paragraph deptP = new Paragraph(department)
                .setFont(regularFont)
                .setFontSize(12)
                .setFontColor(ColorConstants.BLACK)
                .setMarginBottom(3);

        Paragraph countP = new Paragraph(count)
                .setFont(regularFont)
                .setFontSize(10)
                .setFontColor(ColorConstants.GRAY);

        cell.add(titleP);
        cell.add(deptP);
        cell.add(countP);
        return cell;
    }

    private Cell createChartCell(String title, byte[] chartBytes) throws IOException {
        PdfFont boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

        Cell cell = new Cell()
                .setBorder(Border.NO_BORDER)
                .setPadding(10);

        Paragraph chartTitle = new Paragraph(title)
                .setFont(boldFont)
                .setFontSize(12)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(10);
        cell.add(chartTitle);

        Image chartImage = new Image(com.itextpdf.io.image.ImageDataFactory.create(chartBytes))
                .setWidth(UnitValue.createPercentValue(100));
        cell.add(chartImage);

        return cell;
    }

    private void addTableHeader(Table table, String text, PdfFont boldFont) {
        table.addHeaderCell(new Cell()
                .add(new Paragraph(text).setFont(boldFont).setFontColor(ColorConstants.WHITE))
                .setBackgroundColor(DARK_COLOR)
                .setPadding(10)
                .setTextAlignment(TextAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE));
    }

    private Cell createDataCell(String text, DeviceRgb backgroundColor, TextAlignment alignment) throws IOException {
        PdfFont regularFont = PdfFontFactory.createFont(StandardFonts.HELVETICA);

        return new Cell()
                .add(new Paragraph(text).setFont(regularFont))
                .setBackgroundColor(backgroundColor)
                .setPadding(8)
                .setTextAlignment(alignment)
                .setVerticalAlignment(VerticalAlignment.MIDDLE);
    }

    private String getPerformanceStatus(int count, double average) {
        if (count >= average * 1.5) {
            return "Excellent";
        } else if (count >= average) {
            return "Good";
        } else if (count >= average * 0.5) {
            return "Average";
        } else {
            return "Below Average";
        }
    }

    private DeviceRgb getStatusColor(String status) {
        switch (status) {
            case "Excellent":
                return SUCCESS_COLOR;
            case "Good":
                return new DeviceRgb(46, 125, 50); // Darker green
            case "Average":
                return WARNING_COLOR;
            case "Below Average":
                return DANGER_COLOR;
            default:
                return  new DeviceRgb(1.0f, 1.0f, 1.0f);
        }
    }

    private String cleanDepartmentName(String department) {
        if (department == null) {
            return "Unknown Department";
        }

        return department
                .replace("_", " ")
                .replace("Authority", "Auth.")
                .replace("Department", "Dept.")
                .replace("DEPARTMENT", "Dept.")
                .replace("AUTHORITY", "Auth.")
                .trim();
    }
}