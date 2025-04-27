package org.gp.civiceye.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.gp.civiceye.service.ReportAnalysisService;
import org.gp.civiceye.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/V1/reports/analysis")
public class ReportAnalysisController {
    private final ReportAnalysisService reportAnalysisService;

    @Autowired
    public ReportAnalysisController(ReportAnalysisService reportAnalysisService) {
        this.reportAnalysisService = reportAnalysisService;
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

}
