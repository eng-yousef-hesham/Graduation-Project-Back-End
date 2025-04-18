package org.gp.civiceye.mapper.employee;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gp.civiceye.repository.entity.Department;
import org.gp.civiceye.dto.CityDTO;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeDTO {
    private Long empId;
    private String nationalId;
    private String firstName;
    private String lastName;
    private String email;
    private Department department;
    private CityDTO city;
    private LocalDateTime createdAt;
    private Boolean isActive;
}