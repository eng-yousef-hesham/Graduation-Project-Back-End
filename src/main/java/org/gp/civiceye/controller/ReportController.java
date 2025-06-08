package org.gp.civiceye.controller;

import jakarta.persistence.EntityNotFoundException;
import org.gp.civiceye.mapper.report.*;
import org.gp.civiceye.repository.entity.Department;
import org.gp.civiceye.repository.entity.ReportStatus;
import org.gp.civiceye.service.ReportService;
import org.springframework.data.domain.Page;
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
        } catch (EntityNotFoundException ex) {
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

    @PutMapping("/reports/close")
    public ResponseEntity<Void> closeReport(@RequestBody CloseReportDTO dto) {
        reportService.closeReport(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/reports")
    public ResponseEntity<Page<ReportDTO>> getReports(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "reportId") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) ReportStatus currentStatus,
            @RequestParam(required = false) Department department
    ) {
        Page<ReportDTO> reports;

        if (currentStatus != null && department != null) {
            reports = reportService.getReportsByStatusAndDepartment(currentStatus, department, page, size, sortBy, sortDir);
        } else if (currentStatus != null) {
            reports = reportService.getReportsByStatus(currentStatus, page, size, sortBy, sortDir);
        } else if (department != null) {
            reports = reportService.getReportsByDepartment(department, page, size, sortBy, sortDir);
        } else {
            reports = reportService.getAllReports(page, size, sortBy, sortDir);
        }

        return ResponseEntity.ok(reports);
    }

    @GetMapping("reports/allReports")
    public ResponseEntity<List<ReportDTO>> getReports()
    {
        return ResponseEntity.ok(reportService.getAllReportsWithOutClosedAndCancelled());
    }

    @GetMapping("/reports/countbygovernorate")
    public ResponseEntity<List<ReportCountDTO>> getReportsCountByGovernorate() {
        return ResponseEntity.ok(reportService.getReportsCountByGovernorate());
    }
}
