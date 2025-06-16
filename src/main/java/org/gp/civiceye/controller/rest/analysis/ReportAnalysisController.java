package org.gp.civiceye.controller.rest.analysis;

import io.swagger.v3.oas.annotations.Operation;
import org.gp.civiceye.mapper.CityReportCountDTO;
import org.gp.civiceye.mapper.ReportCountEveryDayDTO;
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

    @GetMapping("/init/report/cities/avg-time/master")
    public ResponseEntity<List<CityReportCountDTO>> getAverageTimeToSolveReportInCities() {

        return ResponseEntity.ok(reportAnalysisService.getAverageTimeToSolveReportInCities());
    }
    @GetMapping("/init/report/cities/avg-time/city/{cityId}")
    public ResponseEntity<List<CityReportCountDTO>> getAverageTimeToSolveReportInCity(@PathVariable(name = "cityId" ) Long cityId) {

        return ResponseEntity.ok(reportAnalysisService.getAverageTimeToSolveReportInCity(cityId));
    }
    @GetMapping("/init/report/cities/avg-time/gov/{govId}")
    public ResponseEntity<List<CityReportCountDTO>> getAverageTimeToSolveReportInCitiesPerGovernorate(@PathVariable(name = "govId" ) Long govId) {

        return ResponseEntity.ok(reportAnalysisService.getAverageTimeToSolveReportInCitiesPerGovernorate(govId));
    }

    @GetMapping("/init/report/count/per-day/master")
    public ResponseEntity<List<ReportCountEveryDayDTO>> GetCountOfReportsPerDay() {

        return ResponseEntity.ok(reportAnalysisService.GetCountOfReportsPerDay());
    }
    @GetMapping("/init/report/count/per-day/city/{cityId}")
    public ResponseEntity<List<ReportCountEveryDayDTO>> GetCountOfReportsPerDayPerCity(@PathVariable(name = "cityId" ) Long cityId) {

        return ResponseEntity.ok(reportAnalysisService.GetCountOfReportsPerDayPerCity(cityId));
    }
    @GetMapping("/init/report/count/per-day/gov/{govId}")
    public ResponseEntity<List<ReportCountEveryDayDTO>> GetCountOfReportsPerDayPerGovernorate(@PathVariable(name = "govId" ) Long govId) {

        return ResponseEntity.ok(reportAnalysisService.GetCountOfReportsPerDayPerGovernorate(govId));
    }


}
