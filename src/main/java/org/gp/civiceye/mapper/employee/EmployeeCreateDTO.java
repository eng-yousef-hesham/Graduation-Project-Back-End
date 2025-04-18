package org.gp.civiceye.mapper.employee;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gp.civiceye.repository.entity.Department;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeCreateDTO {
    private String nationalId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Department department;
    private Long cityId;
}