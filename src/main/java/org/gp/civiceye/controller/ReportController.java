package org.gp.civiceye.controller;

import org.gp.civiceye.mapper.ReportDTO;
import org.gp.civiceye.service.ReportService;
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
public class ReportController {
    ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
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
}
