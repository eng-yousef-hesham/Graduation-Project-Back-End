package org.gp.civiceye.controller;

import org.gp.civiceye.service.PDFReportService;
import org.gp.civiceye.service.analysis.ReportAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/V1")
public class ReportPDFController {

    private final ReportAnalysisService reportAnalysisService;
    private final PDFReportService pdfReportService;

    @Autowired
    public ReportPDFController(ReportAnalysisService reportAnalysisService,
                               PDFReportService pdfReportService) {
        this.reportAnalysisService = reportAnalysisService;
        this.pdfReportService = pdfReportService;
    }

    @GetMapping("/download-report")
    public ResponseEntity<byte[]> downloadReport() {
        try {
            List<Map<String, Object>> data = reportAnalysisService.getReportsPerDepartment();
            byte[] pdfBytes = pdfReportService.generateReportPDF(data);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=civic-eye-report.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}