package org.gp.civiceye.controller.rest.analysis;

import io.swagger.v3.oas.annotations.Operation;
import org.gp.civiceye.mapper.report.ReportDTO;
import org.gp.civiceye.service.analysis.ReportAnalysisService;
import org.gp.civiceye.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/init/report/top-4-reports/master")
    public ResponseEntity<List<ReportDTO>> GetTop4Reports() {

        return ResponseEntity.ok(reportAnalysisService.GetTop4Reports());
    }
    @GetMapping("/init/report/top-4-reports/gov/{govId}")
    public ResponseEntity<List<ReportDTO>> GetTop4ReportsBygovId(@PathVariable(name = "govId" ) Long govId) {

        return ResponseEntity.ok(reportAnalysisService.GetTop4ReportsByGovId(govId));
    }
    @GetMapping("/init/report/top-4-reports/city/{cityId}")
    public ResponseEntity<List<ReportDTO>> GetTop4ReportsByCityId(@PathVariable(name = "cityId" ) Long cityId) {

        return ResponseEntity.ok(reportAnalysisService.GetTop4ReportsByCityId(cityId));
    }

    @GetMapping("/init/report/pie-chart/gov/{govId}")
    public ResponseEntity<List<Map<String, Object>>> getReportsCountPerGovernorate(@PathVariable(name = "govId" ) Long govId) {

        return ResponseEntity.ok(reportAnalysisService.getReportsPerGovernorate(govId));
    }

}
