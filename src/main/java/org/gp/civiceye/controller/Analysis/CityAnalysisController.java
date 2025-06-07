package org.gp.civiceye.controller.Analysis;

import org.gp.civiceye.mapper.employee.EmployeeCreateDTO;
import org.gp.civiceye.mapper.report.CreateReportDTO;
import org.gp.civiceye.service.ReportAnalysisService;
import org.gp.civiceye.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@Controller
public class CityAnalysisController {
    ReportAnalysisService   reportAnalysisService;
    ReportService reportService;

    @Autowired
    public CityAnalysisController(ReportAnalysisService reportAnalysisService, ReportService reportService) {
        this.reportService = reportService;
        this.reportAnalysisService = reportAnalysisService;
    }

    @MessageMapping("/createReport")
    @SendTo("/topic/reportsCountPerCity")
    public List<Map<String, Object>> createReportws(@Payload CreateReportDTO dto) {

        Long reportId = reportService.submitReport(dto);

        return reportAnalysisService.getReportsPerCity();
    }


}
