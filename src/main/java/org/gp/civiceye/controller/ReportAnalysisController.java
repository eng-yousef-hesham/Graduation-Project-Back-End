package org.gp.civiceye.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.gp.civiceye.mapper.report.CreateReportDTO;
import org.gp.civiceye.mapper.report.ReportDTO;
import org.gp.civiceye.service.ReportAnalysisService;
import org.gp.civiceye.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/V1/reports/analysis")
public class ReportAnalysisController {
    private final ReportAnalysisService reportAnalysisService;
    private final ReportService reportService;

    @Autowired
    public ReportAnalysisController(ReportAnalysisService reportAnalysisService,ReportService reportService) {
        this.reportAnalysisService = reportAnalysisService;
        this.reportService = reportService;
    }


    @GetMapping("/per-department")
    @Operation(summary = "Number of Reports per Department" )
    public ResponseEntity<List<Map<String, Object>>> getReportsPerDepartment() {
        return ResponseEntity.ok(reportAnalysisService.getReportsPerDepartment());
    }


    @GetMapping("/per-employee")
    @Operation(summary = "Number of Reports Assigned to Each Employee" )
    public ResponseEntity<List<Map<String, Object>>> getReportsPerEmployee() {
        return ResponseEntity.ok(reportAnalysisService.getReportsPerEmployee());
    }

    @GetMapping("/per-city")
    @Operation(summary = "Geographical Distribution of Reports (Count by City)" )
    public ResponseEntity<List<Map<String, Object>>> getReportsPerCity() {
        return ResponseEntity.ok(reportAnalysisService.getReportsPerCity());
    }

    @MessageMapping("/send")
    @SendTo("/topic/messages")
    public ResponseEntity<List<ReportDTO>> send(@RequestBody CreateReportDTO dto) {
        Long reportId = reportService.submitReport(dto);

        List<ReportDTO> reports = reportService.GetAllReports();
        if (reports.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }

}
