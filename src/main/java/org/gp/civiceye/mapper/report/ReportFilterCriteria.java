package org.gp.civiceye.mapper.report;

import lombok.Builder;
import lombok.Data;
import org.gp.civiceye.repository.entity.Department;
import org.gp.civiceye.repository.entity.ReportStatus;

@Data
@Builder
public class ReportFilterCriteria {
    private ReportStatus currentStatus;
    private Department department;
    private Long cityId;
    private Long govId;
    private int page;
    private int size;
    private String sortBy;
    private String sortDir;
}