package org.gp.civiceye.service;

import org.gp.civiceye.mapper.report.ReportCountDTO;
import org.gp.civiceye.mapper.report.ReportDTO;
import org.gp.civiceye.mapper.report.UpdateReportStatusDTO;
import org.gp.civiceye.mapper.report.CloseReportDTO;

import java.util.List;

public interface ReportService {
    public List<ReportDTO> GetAllReports();
    public Long submitReport(CloseReportDTO.CreateReportDTO dto);
    public List<ReportDTO> getReportsForUser(Long userId);

    public List <ReportDTO> getReportsForEmployee(Long employeeId);


    public void updateReportStatus(UpdateReportStatusDTO dto);

    List<ReportCountDTO> getReportsCountByGovernorate();
    public ReportDTO getReportsById(Long reportId);

    void closeReport(CloseReportDTO dto);
}
