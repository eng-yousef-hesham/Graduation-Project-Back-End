package org.gp.civiceye.controller;


import org.gp.civiceye.repository.entity.ReportStatus;
import org.gp.civiceye.service.ReportStatusService;
import org.gp.civiceye.service.impl.ReportStatusServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/V1")
public class ReportStatusController {
    ReportStatusService reportStatusService ;

    public ReportStatusController(ReportStatusServiceImpl reportStatusService) {
        this.reportStatusService = reportStatusService;
    }

    @GetMapping("/ReportStatus")
    public ReportStatus[] getAllReportStatus() {
        return reportStatusService.getAllReportStatus();
    }
}
