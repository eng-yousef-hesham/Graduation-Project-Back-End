package org.gp.civiceye.controller;

import org.gp.civiceye.mapper.ReportDTO;
import org.gp.civiceye.service.ReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/V1")
public class ReportController {
    ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/reports")
    public List<ReportDTO> getAllReports() {
        return reportService.GetAllReports();
    }
}
