package org.gp.civiceye.mapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gp.civiceye.repository.entity.Department;
import org.gp.civiceye.repository.entity.Report;
import org.gp.civiceye.repository.entity.ReportStatus;
import org.gp.civiceye.repository.entity.StatusHistory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportDTO {
    private Long reportId;
    private String title;
    private String description;
    private Float latitude;
    private Float longitude;
    private String contactInfo;
    private Department department;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDate expectedResolutionDate;
    private ReportStatus currentStatus;
    private Long citizenId;
    private String citizenName;
    private Long cityId;
    private String cityName;
    private Long assignedEmployeeId;
    private String assignedEmployeeName;
    private List<StatusHistoryDTO> statusHistory;



    public ReportDTO(Report report) {
        this.reportId = report.getReportId();
        this.title = report.getTitle();
        this.description = report.getDescription();
        this.latitude = report.getLatitude();
        this.longitude = report.getLongitude();
        this.contactInfo = report.getContactInfo();
        this.department = report.getDepartment();
        this.createdAt = report.getCreatedAt();
        this.updatedAt = report.getUpdatedAt();
        this.expectedResolutionDate = report.getExpectedResolutionDate();
        this.currentStatus = report.getCurrentStatus();
        this.statusHistory = report.getStatusHistoryList().stream().map(StatusHistoryDTO::new).toList();


        if (report.getCitizen() != null) {
            this.citizenId = report.getCitizen().getCitizenId();
            this.citizenName = (report.getCitizen().getFirstName()+" "+report.getCitizen().getLastName());
        }


        if (report.getCity() != null) {
            this.cityId = report.getCity().getCityId();
            this.cityName = report.getCity().getName();
        }


        if (report.getAssignedEmployee() != null) {
            this.assignedEmployeeId = report.getAssignedEmployee().getEmpId();
            this.assignedEmployeeName = report.getAssignedEmployee().getFirstName()+" "+report.getAssignedEmployee().getLastName();
        }


    }
}