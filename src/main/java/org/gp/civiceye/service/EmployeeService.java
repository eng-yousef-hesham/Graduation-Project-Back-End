package org.gp.civiceye.service;

import org.gp.civiceye.mapper.employee.EmployeeCreateDTO;
import org.gp.civiceye.mapper.employee.EmployeeDTO;
import org.gp.civiceye.mapper.employee.EmployeeUpdateDTO;

import java.util.List;

public interface EmployeeService {

    public List<EmployeeDTO> getAllEmployees();

    public EmployeeDTO getEmployeeById(Long id);

    public Long createEmployee(EmployeeCreateDTO employee);

    public Long updateEmployee(Long employeeId, EmployeeUpdateDTO employee);

    public Long deleteEmployee(Long employeeId);
}
