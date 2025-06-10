package org.gp.civiceye.service;

import org.gp.civiceye.mapper.report.*;
import org.gp.civiceye.repository.entity.Department;
import org.gp.civiceye.repository.entity.ReportStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReportService {
    public Page<ReportDTO> getAllReports(int page, int size, String sortBy, String sortDir);

    public Page<ReportDTO> getAllReportsByCityId(Long cityId, int page, int size, String sortBy, String sortDir);

    public Page<ReportDTO> getAllReportsByGovernmentId(Long govId, int page, int size, String sortBy, String sortDir);

    public Long submitReport(CreateReportDTO dto);

    public List<ReportDTO> getReportsForUser(Long userId);

    public List<ReportDTO> getReportsForEmployee(Long employeeId);

    Page<ReportDTO> getReportsByStatusAndDepartment(ReportStatus currentStatus, Department department, int page, int size, String sortBy, String sortDir);

    Page<ReportDTO> getReportsByStatusAndDepartmentAndGovernmentId(ReportStatus currentStatus, Department department, Long govId, int page, int size, String sortBy, String sortDir);

    Page<ReportDTO> getReportsByStatusAndDepartmentAndCityId(ReportStatus currentStatus, Department department, Long cityId, int page, int size, String sortBy, String sortDir);

    Page<ReportDTO> getReportsByDepartment(Department department, int page, int size, String sortBy, String sortDir);
    Page<ReportDTO> getReportsByDepartmentAndGovernmentId(Department department,Long govId, int page, int size, String sortBy, String sortDir);
    Page<ReportDTO> getReportsByDepartmentAndCityId(Department department,Long cityId, int page, int size, String sortBy, String sortDir);

    public void updateReportStatus(UpdateReportStatusDTO dto);

    List<ReportCountDTO> getReportsCountByGovernorate();

    public ReportDTO getReportsById(Long reportId);

    void closeReport(CloseReportDTO dto);

    Page<ReportDTO> getReportsByStatus(ReportStatus currentStatus, int page, int size, String sortBy, String sortDir);
    Page<ReportDTO> getReportsByStatusAndGovernmentId(ReportStatus currentStatus,Long govId, int page, int size, String sortBy, String sortDir);
    Page<ReportDTO> getReportsByStatusAndCityId(ReportStatus currentStatus,Long cityId, int page, int size, String sortBy, String sortDir);

    public List<ReportDTO> getAllReportsWithOutClosedAndCancelled();
}
