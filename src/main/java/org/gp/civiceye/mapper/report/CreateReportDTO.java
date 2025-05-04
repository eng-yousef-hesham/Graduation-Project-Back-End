package org.gp.civiceye.mapper.report;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gp.civiceye.repository.entity.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateReportDTO {
    private String title;
    private String contactInfo;
    private Long cityId;
    private String description;
    private Float latitude;
    private Float longitude;
    private Department department;
    private Long citizenId;


    public Report toReportEntity(City city, Citizen citizen, Employee assignedEmployee) {
        return Report.builder()
                .description(description)
                .contactInfo(contactInfo)
                .city(city)
                .latitude(latitude)
                .longitude(longitude)
                .department(department)
                .currentStatus(ReportStatus.Submitted) // or your initial status
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .title(title)
                .expectedResolutionDate(null)
                .assignedEmployee(assignedEmployee)
                .citizen(citizen)
                .build();
    }
}