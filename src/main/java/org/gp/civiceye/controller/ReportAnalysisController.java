package org.gp.civiceye.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.gp.civiceye.mapper.report.CreateReportDTO;
import org.gp.civiceye.mapper.report.ReportDTO;
import org.gp.civiceye.service.ReportAnalysisService;
import org.gp.civiceye.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping("/init/report/numbers/gov/{govId}")
    public ResponseEntity<Map<String,Long>> initReportNumbersForGovernorate
            (@PathVariable(name = "govId" ) Long govId) {

        return ResponseEntity.ok(reportAnalysisService.initReportNumbersForGovernorate(govId));
    }

    @GetMapping("/init/report/numbers/city/{cityId}")
    public ResponseEntity<Map<String,Long>> initReportNumbersForcity
            (@PathVariable(name = "cityId" ) Long cityId) {

        return ResponseEntity.ok(reportAnalysisService.initReportNumbersForcity(cityId));
    }



}
