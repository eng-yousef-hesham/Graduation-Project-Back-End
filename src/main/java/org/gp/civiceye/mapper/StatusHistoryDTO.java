package org.gp.civiceye.mapper;

import org.gp.civiceye.repository.entity.ReportStatus;
import org.gp.civiceye.repository.entity.StatusHistory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatusHistoryDTO {
    private Long statusId;
    private ReportStatus status;
    private String notes;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long reportId;
    private Long updatedByEmployeeId;
    private String updatedByEmployeeName;



    public StatusHistoryDTO(StatusHistory statusHistory) {
        this.statusId = statusHistory.getStatusId();
        this.status = statusHistory.getStatus();
        this.notes = statusHistory.getNotes();
        this.startTime = statusHistory.getStartTime();
        this.endTime = statusHistory.getEndTime();


        if (statusHistory.getReport() != null) {
            this.reportId = statusHistory.getReport().getReportId();
        }


        if (statusHistory.getChangedByEmployee() != null) {
            this.updatedByEmployeeId = statusHistory.getChangedByEmployee().getEmpId();
            this.updatedByEmployeeName = statusHistory.getChangedByEmployee().getFirstName()+" "+statusHistory.getChangedByEmployee().getLastName();
        }
    }
}