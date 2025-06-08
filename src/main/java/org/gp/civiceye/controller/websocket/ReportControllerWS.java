package org.gp.civiceye.controller.websocket;

import org.gp.civiceye.mapper.report.ReportDTO;
import org.gp.civiceye.mapper.report.UpdateReportStatusDTO;
import org.gp.civiceye.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

@Controller
public class ReportControllerWS {

    private final ReportService reportService;
    private final SimpMessagingTemplate messagingTemplate;


    @Autowired
    public ReportControllerWS(ReportService reportService, SimpMessagingTemplate messagingTemplate) {
        this.reportService = reportService;
        this.messagingTemplate = messagingTemplate;
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
}
