package org.gp.civiceye.controller;

import jakarta.persistence.EntityNotFoundException;
import org.gp.civiceye.mapper.employee.EmployeeReportsCountDTO;
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
@RequestMapping("api/v1/reports")
public class ReportController {
    ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/submit")
    public ResponseEntity<Long> submitReport(@RequestBody CreateReportDTO dto) {
        Long reportId = reportService.submitReport(dto);
        return new ResponseEntity<>(reportId, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReportDTO>> getUserReports(@PathVariable Long userId) {
        return ResponseEntity.ok(reportService.getReportsForUser(userId));
    }

    @GetMapping("/user")
    public ResponseEntity<List<ReportDTO>> getUserReports() {
        return ResponseEntity.ok(reportService.getReportsForAuthenticatedUser());
    }

    @GetMapping("/{reportId}")
    public ResponseEntity<ReportDTO> getReportById(@PathVariable Long reportId) {
            return ResponseEntity.ok(reportService.getReportsById(reportId));
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<ReportDTO>> getEmployeeReports(@PathVariable Long employeeId) {
        return ResponseEntity.ok(reportService.getReportsForEmployee(employeeId));
    }

    @GetMapping("/employee/")
    public ResponseEntity<List<ReportDTO>> getEmployeeReports() {
        return ResponseEntity.ok(reportService.getReportsForAuthenticatedEmployee());
    }

    @GetMapping("/status/employee/{employeeId}")
    public ResponseEntity<EmployeeReportsCountDTO> getEmployeeReportsByStatus(@PathVariable Long employeeId) {
        return ResponseEntity.ok(reportService.getReportsForEmployeeByStatus(employeeId));
    }

    @PutMapping("/status")
    public ResponseEntity<Void> updateStatus(@RequestBody UpdateReportStatusDTO dto) {
        reportService.updateReportStatus(dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/close")
    public ResponseEntity<Void> closeReport(@RequestBody CloseReportDTO dto) {
        reportService.closeReport(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Page<ReportDTO>> getReports(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "reportId") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) ReportStatus currentStatus,
            @RequestParam(required = false) Department department,
            @RequestParam(required = false) Long cityId,
            @RequestParam(required = false) Long govId
    ) {
        ReportFilterCriteria criteria = ReportFilterCriteria.builder()
                .currentStatus(currentStatus)
                .department(department)
                .cityId(cityId)
                .govId(govId)
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .sortDir(sortDir)
                .build();

        Page<ReportDTO> reports = reportService.getReportsWithFilters(criteria);
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/allReportsWithOutClosedAndCancelled")
    public ResponseEntity<List<ReportDTO>> getReportsWithOutClosedAndCancelled() {
        return ResponseEntity.ok(reportService.getAllReportsWithOutClosedAndCancelled());
    }

    @GetMapping("/countbygovernorate")
    public ResponseEntity<List<ReportCountDTO>> getReportsCountByGovernorate() {
        return ResponseEntity.ok(reportService.getReportsCountByGovernorate());
    }
}
