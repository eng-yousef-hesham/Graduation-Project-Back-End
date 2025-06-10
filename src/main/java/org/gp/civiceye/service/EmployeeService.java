package org.gp.civiceye.service;

import org.gp.civiceye.mapper.employee.EmployeeCreateDTO;
import org.gp.civiceye.mapper.employee.EmployeeDTO;
import org.gp.civiceye.mapper.employee.EmployeeUpdateDTO;
import org.gp.civiceye.repository.entity.Department;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeService {

    public Page<EmployeeDTO> getAllEmployees(int page, int size);

    public Page<EmployeeDTO> getAllEmployeesByCityId(Long cityId, int page, int size);

    public Page<EmployeeDTO> getAllEmployeesByGovernorateId(Long govId, int page, int size);

    public EmployeeDTO getEmployeeById(Long id);

    public Long createEmployee(EmployeeCreateDTO employee);

    public Long updateEmployee(Long employeeId, EmployeeUpdateDTO employee);

    public Long deleteEmployee(Long employeeId);

    Page<EmployeeDTO> getAllEmployeesByDepartment(Department department, int page, int size);

    Page<EmployeeDTO> getAllEmployeesByDepartmentAndCityId(Department department,Long cityId, int page, int size);

    Page<EmployeeDTO> getAllEmployeesByDepartmentAndGovernorateId(Department department,Long govId, int page, int size);
}
