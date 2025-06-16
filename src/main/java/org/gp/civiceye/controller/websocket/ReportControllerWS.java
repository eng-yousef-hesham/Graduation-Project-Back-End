package org.gp.civiceye.controller.websocket;

import org.gp.civiceye.exception.ReportNotCreatedException;
import org.gp.civiceye.mapper.ReportCountEveryDayDTO;
import org.gp.civiceye.mapper.report.CreateReportDTO;
import org.gp.civiceye.mapper.report.ReportDTO;
import org.gp.civiceye.mapper.report.UpdateReportStatusDTO;
import org.gp.civiceye.repository.entity.City;
import org.gp.civiceye.repository.entity.Department;
import org.gp.civiceye.service.CityService;
import org.gp.civiceye.service.analysis.DepartmentAnalysisService;
import org.gp.civiceye.service.analysis.ReportAnalysisService;
import org.gp.civiceye.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

@Controller
public class ReportControllerWS {

    private final ReportAnalysisService reportAnalysisService;
    private final ReportService reportService;
    private final CityService CityService;
    private final SimpMessagingTemplate messagingTemplate;
    private final DepartmentAnalysisService departmentAnalysisService;


    @Autowired
    public ReportControllerWS(ReportAnalysisService reportAnalysisService,
                                  ReportService reportService,
                                  SimpMessagingTemplate messagingTemplate,
                                  CityService cityservice,
                                  DepartmentAnalysisService departmentAnalysisService) {
        this.reportService = reportService;
        this.reportAnalysisService = reportAnalysisService;
        this.messagingTemplate = messagingTemplate;
        this.CityService = cityservice;
        this.departmentAnalysisService = departmentAnalysisService;
    }

    @MessageMapping("/updateStatus")
    public void UpdateReportStatusWS(@Payload UpdateReportStatusDTO dto) {

        reportService.updateReportStatus(dto);
        Long reportId = dto.getReportId();
        ReportDTO report = reportService.getReportsById(reportId);
        Long citizenId = report.getCitizenId();
        String topic = "/topic/citizen/" + citizenId;

        Long cityId = report.getCityId();
        Long governorateId = report.getGovernorateId();

        Long inProgresscityReportCount = reportAnalysisService.countReportsPerCityInProgress(cityId);

        Long inProgressGovernorateReportCount = reportAnalysisService.countReportsPerGovernorateInProgress(governorateId);

        Long ResolvedcityReportCount = reportAnalysisService.countReportsPerCityResolved(cityId);

        Long ResolvedGovernorateReportCount = reportAnalysisService.countReportsPerGovernorateResolved(governorateId);


        messagingTemplate.convertAndSend(topic, "Report #" + reportId + " status changed to " + dto.getNewStatus());

        messagingTemplate.convertAndSend("/topic/inProgressReportsCountPerCity/" + cityId, inProgresscityReportCount);
        messagingTemplate.convertAndSend("/topic/inProgressReportsCountPerGovernorate/" + governorateId, inProgressGovernorateReportCount);
        messagingTemplate.convertAndSend("/topic/ResolvedReportsCountPerCity/" + cityId, ResolvedcityReportCount);
        messagingTemplate.convertAndSend("/topic/ResolvedReportsCountPerGovernorate/" + governorateId, ResolvedGovernorateReportCount);
    }

    @MessageMapping("/createReport")
    public void createReportPerCityWS(@Payload CreateReportDTO dto, SimpMessageHeaderAccessor headerAccessor) {
        String sessionId = headerAccessor.getSessionId();

        try {
            Long reportId = reportService.submitReport(dto);

            // Rest of your success logic here...
            City city = CityService.getCityById(dto.getCityId());
            Long cityId = city.getCityId();
            Long governorateId = city.getGovernorate().getGovernorateId();
            Long empId = reportService.getReportsById(reportId).getAssignedEmployeeId();

            String topic = "/topic/employee/" + empId;

            List<Map<String, Object>> cityData = reportAnalysisService.getReportsPerGovernorate(governorateId);
            Long cityReportData = reportAnalysisService.getReportsCountPerCity(cityId);
            Long cityReportCount = reportAnalysisService.getReportsCountPerGovernorate(governorateId);

            messagingTemplate.convertAndSend(topic, "Report #" + reportId + " has been assigned to you. Please check your dashboard.");
            messagingTemplate.convertAndSend("/topic/reportsCountPerCitiesInGovernorate/" + governorateId, cityData);
            messagingTemplate.convertAndSend("/topic/reportsCountPerCity/" + cityId, cityReportData);
            messagingTemplate.convertAndSend("/topic/reportsCountPerGovernorate/" + governorateId, cityReportCount);

            //send latest 4 reports
            List<ReportDTO> cityReports = reportAnalysisService.GetTop4ReportsByCityId(cityId);
            List<ReportDTO> govReports = reportAnalysisService.GetTop4ReportsByGovId(governorateId);

            messagingTemplate.convertAndSend("/topic/latestReports/city/" + cityId, cityReports);
            messagingTemplate.convertAndSend("/topic/latestReports/gov/" + governorateId, govReports);


            //send reports per day
            List<ReportCountEveryDayDTO> cityReportsPerDay = reportAnalysisService.GetCountOfReportsPerDayPerCity(cityId);
            List<ReportCountEveryDayDTO> govReportsPerDay = reportAnalysisService.GetCountOfReportsPerDayPerGovernorate(governorateId);

            messagingTemplate.convertAndSend("/topic/ReportsPerDay/city/" + cityId, cityReportsPerDay);
            messagingTemplate.convertAndSend("/topic/ReportsPerDay/gov/" + governorateId, govReportsPerDay);


            //send reports per department
            Map<Department, Long> cityReportsPerDepartment = departmentAnalysisService.getCountOfReportsPerDepartmentPerCity(cityId);
            Map<Department, Long> govcityReportsPerDepartment= departmentAnalysisService.getCountOfReportsPerDepartmentPerGovernorate(governorateId);

            messagingTemplate.convertAndSend("/topic/ReportsPerDepartment/city/" + cityId, cityReportsPerDepartment);
            messagingTemplate.convertAndSend("/topic/ReportsPerDepartment/gov/" + governorateId, govcityReportsPerDepartment);


        } catch (Exception e) {
            // Send error message to the specific user who sent the request
            messagingTemplate.convertAndSendToUser(
                    headerAccessor.getUser().getName(),
                    "/queue/errors",
                    "Failed to create report: " + e.getMessage()
            );

            // general error topic
            messagingTemplate.convertAndSend("/topic/errors",
                    "Report creation failed for city: " + dto.getCityId());
        }
    }


}
