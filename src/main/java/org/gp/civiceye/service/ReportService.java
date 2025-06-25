package org.gp.civiceye.service;

import org.gp.civiceye.mapper.employee.EmployeeReportsCountDTO;
import org.gp.civiceye.mapper.report.*;
import org.gp.civiceye.repository.entity.Department;
import org.gp.civiceye.repository.entity.ReportStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReportService {
    public Long submitReport(CreateReportDTO dto);
    Page<ReportDTO> getReportsWithFilters(ReportFilterCriteria criteria);
    public List<ReportDTO> getReportsForUser(Long userId);
    public List<ReportDTO> getReportsForAuthenticatedUser();
    public EmployeeReportsCountDTO getReportsForEmployeeByStatus(Long employeeId);
    public List<ReportDTO> getReportsForEmployee(Long employeeId);
    public List<ReportDTO> getReportsForAuthenticatedEmployee();
    public void updateReportStatus(UpdateReportStatusDTO dto);
    List<ReportCountDTO> getReportsCountByGovernorate();
    public ReportDTO getReportsById(Long reportId);
    void closeReport(CloseReportDTO dto);
    public List<ReportDTO> getAllReportsWithOutClosedAndCancelled();
}
