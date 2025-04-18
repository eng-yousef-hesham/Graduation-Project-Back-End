package org.gp.civiceye.service;

import org.gp.civiceye.mapper.employee.EmployeeCreateDTO;
import org.gp.civiceye.mapper.employee.EmployeeDTO;
import org.gp.civiceye.mapper.employee.EmployeeUpdateDTO;
import org.gp.civiceye.service.impl.employee.AddEmployeeResult;
import org.gp.civiceye.service.impl.employee.DeleteEmployeeResult;
import org.gp.civiceye.service.impl.employee.UpdateEmployeeResult;

import java.util.List;

public interface EmployeeService {

    public List<EmployeeDTO> getAllEmployees();

    public EmployeeDTO getEmployeeById(Long id);

    public AddEmployeeResult createEmployee(EmployeeCreateDTO employee);

    public UpdateEmployeeResult updateEmployee(Long employeeId, EmployeeUpdateDTO employee);

    public DeleteEmployeeResult deleteEmployee(Long employeeId);
}
