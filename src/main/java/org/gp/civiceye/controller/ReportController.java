package org.gp.civiceye.controller;

import jakarta.persistence.EntityNotFoundException;
import org.gp.civiceye.mapper.CreateReportDTO;
import org.gp.civiceye.mapper.ReportCountDTO;
import org.gp.civiceye.mapper.ReportDTO;
import org.gp.civiceye.mapper.UpdateReportStatusDTO;
import org.gp.civiceye.repository.entity.Report;
import org.gp.civiceye.service.ReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/V1")
public class ReportController {
    ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }




    @PostMapping("/reports/submit")
    public ResponseEntity<Long> submitReport(@RequestBody CreateReportDTO dto) {
        Long reportId = reportService.submitReport(dto);
        return new ResponseEntity<>(reportId, HttpStatus.OK);
    }

    @GetMapping("/reports/user/{userId}")
    public ResponseEntity<List<ReportDTO>> getUserReports(@PathVariable Long userId) {
        return ResponseEntity.ok(reportService.getReportsForUser(userId));
    }

    @GetMapping("/reports/{reportId}")
    public ResponseEntity<ReportDTO> getReportById(@PathVariable Long reportId) {

        try {
            return ResponseEntity.ok(reportService.getReportsById(reportId));
        }
        catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/reports/employee/{employeeId}")
    public ResponseEntity<List<ReportDTO>> getEmployeeReports(@PathVariable Long employeeId) {
        return ResponseEntity.ok(reportService.getReportsForEmployee(employeeId));
    }

    @PutMapping("/reports/status")
    public ResponseEntity<Void> updateStatus(@RequestBody UpdateReportStatusDTO dto) {
        reportService.updateReportStatus(dto);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/reports")
    public ResponseEntity<List<ReportDTO>> getAllReports() {

//        HashMap<String, Object> response = new HashMap<>();
//        response.put("statusText", HttpStatus.OK.getReasonPhrase());
//        response.put("statusCode", HttpStatus.OK.value());
//        response.put("message", "Retrieved All Reports");
//        response.put("data", reportService.GetAllReports());
        List<ReportDTO> reports = reportService.GetAllReports();
        if (reports.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }

    @GetMapping("/reports/countbygovernorate")
    public ResponseEntity<List<ReportCountDTO>> getReportsCountByGovernorate() {
        return ResponseEntity.ok(reportService.getReportsCountByGovernorate());
    }
}
