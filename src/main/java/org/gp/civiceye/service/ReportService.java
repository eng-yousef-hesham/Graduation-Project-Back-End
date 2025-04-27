package org.gp.civiceye.service;

import org.gp.civiceye.mapper.CreateReportDTO;
import org.gp.civiceye.mapper.ReportCountDTO;
import org.gp.civiceye.mapper.ReportDTO;
import org.gp.civiceye.mapper.UpdateReportStatusDTO;
import org.gp.civiceye.repository.entity.Employee;

import java.util.List;

public interface ReportService {
    public List<ReportDTO> GetAllReports();
    public Long submitReport(CreateReportDTO dto);
    public List<ReportDTO> getReportsForUser(Long userId);

    public List <ReportDTO> getReportsForEmployee(Long employeeId);


    public void updateReportStatus(UpdateReportStatusDTO dto);

    List<ReportCountDTO> getReportsCountByGovernorate();
    public ReportDTO getReportsById(Long reportId);
}
