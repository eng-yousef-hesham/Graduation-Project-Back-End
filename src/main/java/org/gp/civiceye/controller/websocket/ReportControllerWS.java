package org.gp.civiceye.controller.websocket;

import org.gp.civiceye.mapper.report.CreateReportDTO;
import org.gp.civiceye.mapper.report.ReportDTO;
import org.gp.civiceye.mapper.report.UpdateReportStatusDTO;
import org.gp.civiceye.repository.entity.City;
import org.gp.civiceye.service.CityService;
import org.gp.civiceye.service.ReportAnalysisService;
import org.gp.civiceye.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Controller
public class ReportControllerWS {

    private final ReportAnalysisService reportAnalysisService;
    private final ReportService reportService;
    private final CityService CityService;
    private final SimpMessagingTemplate messagingTemplate;


    @Autowired
    public ReportControllerWS(ReportAnalysisService reportAnalysisService,
                                  ReportService reportService,
                                  SimpMessagingTemplate messagingTemplate,
                                  CityService cityservice) {
        this.reportService = reportService;
        this.reportAnalysisService = reportAnalysisService;
        this.messagingTemplate = messagingTemplate;
        this.CityService = cityservice;
    }

    @MessageMapping("/updateStatus")
    public void UpdateReportStatusWS(@Payload UpdateReportStatusDTO dto) {
        reportService.updateReportStatus(dto);
        Long reportId = dto.getReportId();
        ReportDTO report = reportService.getReportsById(reportId);
        Long citizenId = report.getCitizenId();
        String topic = "/topic/citizen/" + citizenId;

        messagingTemplate.convertAndSend(topic, "Report #" + reportId + " status changed to " + dto.getNewStatus());

    }

    @MessageMapping("/createReport")
    public void createReportPerCityWS(@Payload CreateReportDTO dto) {

        Long reportId = reportService.submitReport(dto);

        City city = CityService.getCityById(dto.getCityId());

        Long cityId = city.getCityId();

        Long governorateId = city.getGovernorate().getGovernorateId();



        List<Map<String, Object>> cityData = reportAnalysisService.getReportsPerGovernorate(governorateId);

        Long cityReportData = reportAnalysisService.getReportsCountPerCity(cityId);

        Long cityReportCount = reportAnalysisService.getReportsCountPerGovernorate(governorateId);


        messagingTemplate.convertAndSend("/topic/reportsCountPerCitiesInGovernorate/" + governorateId, cityData);

        messagingTemplate.convertAndSend("/topic/reportsCountPerCity/" + cityId, cityReportData);

        messagingTemplate.convertAndSend("/topic/reportsCountPerGovernorate/" + governorateId, cityReportCount);
    }









}
