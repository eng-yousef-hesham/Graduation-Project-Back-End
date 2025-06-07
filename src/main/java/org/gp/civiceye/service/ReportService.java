package org.gp.civiceye.service;

import org.gp.civiceye.mapper.report.*;
import org.gp.civiceye.repository.entity.Department;
import org.gp.civiceye.repository.entity.ReportStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReportService {
    public Page<ReportDTO> getAllReports(int page, int size, String sortBy, String sortDir);
    public Long submitReport(CreateReportDTO dto);
    public List<ReportDTO> getReportsForUser(Long userId);

    public List <ReportDTO> getReportsForEmployee(Long employeeId);
    Page<ReportDTO> getReportsByStatusAndDepartment(ReportStatus currentStatus, Department department, int page, int size, String sortBy, String sortDir);
    Page<ReportDTO> getReportsByDepartment(Department department, int page, int size, String sortBy, String sortDir);
    public void updateReportStatus(UpdateReportStatusDTO dto);

    List<ReportCountDTO> getReportsCountByGovernorate();
    public ReportDTO getReportsById(Long reportId);

    void closeReport(CloseReportDTO dto);

    Page<ReportDTO> getReportsByStatus(ReportStatus currentStatus, int page, int size, String sortBy, String sortDir);
}
