package org.gp.civiceye.controller;


import org.gp.civiceye.repository.entity.ReportStatus;
import org.gp.civiceye.service.ReportStatusService;
import org.gp.civiceye.service.impl.ReportStatusServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/V1")
public class ReportStatusController {
    ReportStatusService reportStatusService ;

    public ReportStatusController(ReportStatusServiceImpl reportStatusService) {
        this.reportStatusService = reportStatusService;
    }

    @GetMapping("/report-statuses")
    public ResponseEntity<ReportStatus[]> getAllReportStatus() {
        return new ResponseEntity<>(reportStatusService.getAllReportStatus(), HttpStatus.OK);
    }
}
