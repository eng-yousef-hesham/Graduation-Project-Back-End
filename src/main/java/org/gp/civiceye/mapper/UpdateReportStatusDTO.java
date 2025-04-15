package org.gp.civiceye.mapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gp.civiceye.repository.entity.ReportStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateReportStatusDTO {
    private Long reportId;
    private ReportStatus newStatus;
    private String notes;
    private Long employeeId;
}
