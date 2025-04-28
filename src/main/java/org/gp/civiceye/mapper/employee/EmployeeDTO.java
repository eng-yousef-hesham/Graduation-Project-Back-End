package org.gp.civiceye.mapper.employee;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gp.civiceye.repository.entity.Department;
import org.gp.civiceye.repository.entity.Employee;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeDTO {
    private Long empId;
    private String nationalId;
    private String fullName;
    private String email;
    private Department department;
    private String city;
    private String governorate;
    private LocalDateTime createdAt;
    private Boolean isActive;

    public EmployeeDTO(Object o) {
        Employee employee = (Employee) o;
        this.empId = employee.getEmpId();
        this.nationalId = employee.getNationalId();
        this.email = employee.getEmail();
        this.department = employee.getDepartment();
        this.city = employee.getCity().getName();
        this.governorate = employee.getCity().getGovernorate().getName();
        this.createdAt = employee.getCreatedAt();
        this.isActive = employee.getIsActive();
    }
}