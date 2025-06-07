package org.gp.civiceye.controller.websocket.Analysis;

import org.gp.civiceye.mapper.report.CreateReportDTO;
import org.gp.civiceye.repository.entity.City;
import org.gp.civiceye.service.CityService;
import org.gp.civiceye.service.ReportAnalysisService;
import org.gp.civiceye.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

@Controller
public class CityAnalysisController {

    private final ReportAnalysisService reportAnalysisService;
    private final ReportService reportService;
    private final CityService CityService;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public CityAnalysisController(ReportAnalysisService reportAnalysisService,
                                  ReportService reportService,
                                  SimpMessagingTemplate messagingTemplate,
                                  CityService cityservice) {
        this.reportService = reportService;
        this.reportAnalysisService = reportAnalysisService;
        this.messagingTemplate = messagingTemplate;
        this.CityService = cityservice;
    }

    @MessageMapping("/createReport")
    public void createReportWS(@Payload CreateReportDTO dto) {

        Long reportId = reportService.submitReport(dto);

        City city = CityService.getCityById(dto.getCityId());

        Long governorateId = city.getGovernorate().getGovernorateId();

        // Fetch city report data only for this governorate
        List<Map<String, Object>> cityData = reportAnalysisService.getReportsPerGovernorate(governorateId);

        // Send data to the topic specific to governorateId
        messagingTemplate.convertAndSend("/topic/reportsCountPerCity/" + governorateId, cityData);
    }
}
