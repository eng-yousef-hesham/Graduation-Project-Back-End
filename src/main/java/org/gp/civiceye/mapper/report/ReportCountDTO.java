package org.gp.civiceye.mapper.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReportCountDTO {
    private String name;
    private Integer count;
}
