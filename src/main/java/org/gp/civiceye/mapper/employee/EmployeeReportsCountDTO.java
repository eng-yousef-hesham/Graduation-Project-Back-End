package org.gp.civiceye.mapper.employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gp.civiceye.repository.entity.ReportStatus;

import java.util.HashMap;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeReportsCountDTO {
    private HashMap<ReportStatus, Integer> reportsCount = new HashMap<>();
    private Integer totalCount;
}
