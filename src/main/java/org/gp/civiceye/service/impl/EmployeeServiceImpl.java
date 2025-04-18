package org.gp.civiceye.service.impl;

import org.gp.civiceye.mapper.UpdateAdminDTO;
import org.gp.civiceye.mapper.employee.EmployeeCreateDTO;
import org.gp.civiceye.mapper.employee.EmployeeDTO;
import org.gp.civiceye.mapper.employee.EmployeeUpdateDTO;
import org.gp.civiceye.repository.CityRepository;
import org.gp.civiceye.repository.EmployeeRepository;
import org.gp.civiceye.repository.entity.Admin;
import org.gp.civiceye.repository.entity.City;
import org.gp.civiceye.repository.entity.Employee;
import org.gp.civiceye.service.EmployeeService;
import org.gp.civiceye.service.impl.admin.AdminType;
import org.gp.civiceye.service.impl.admin.DeleteAdminResult;
import org.gp.civiceye.service.impl.admin.UpdateAdminResult;
import org.gp.civiceye.service.impl.employee.AddEmployeeResult;
import org.gp.civiceye.service.impl.employee.DeleteEmployeeResult;
import org.gp.civiceye.service.impl.employee.UpdateEmployeeResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.gp.civiceye.dto.CityDTO;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final CityRepository cityRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder, CityRepository cityRepository) {
        this.employeeRepository = employeeRepository;
        this.cityRepository = cityRepository;
        this.passwordEncoder = passwordEncoder;

    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(employee -> new EmployeeDTO(
                        employee.getEmpId(),
                        employee.getNationalId(),
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getEmail(),
                        employee.getDepartment(),
                        new CityDTO(employee.getCity()),
                        employee.getCreatedAt(),
                        employee.getIsActive()
                )).toList();
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        return employeeRepository.findById(id).map(employee -> new EmployeeDTO(
                employee.getEmpId(),
                employee.getNationalId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getDepartment(),
                new CityDTO(employee.getCity()),
                employee.getCreatedAt(),
                employee.getIsActive()
        )).orElse(null);
    }

    public AddEmployeeResult createEmployee(EmployeeCreateDTO employee) {
        String password = employee.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        employee.setPassword(encodedPassword);

        Optional<Employee> existingEmployee = employeeRepository.findByEmailOrNationalId(employee.getEmail(),employee.getNationalId());
        if (existingEmployee.isPresent()) {
            return new AddEmployeeResult(false, "Error, Email or National ID Found, Employee already exists");
        }

        Long cityId = employee.getCityId();
        Optional<City> city = cityRepository.findById(cityId);

        if (!city.isPresent()) {
            return new AddEmployeeResult(false, "Error, City not found");
        }

        Employee emp = Employee.builder()
                .nationalId(employee.getNationalId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .passwordHash(employee.getPassword())
                .department(employee.getDepartment())
                .city(city.get())
                .isActive(true)
                .build();
        Employee savedEmployee = employeeRepository.save(emp);


        return new AddEmployeeResult(true, "Employee created successfully");
    }

    @Override
    public UpdateEmployeeResult updateEmployee(Long employeeId, EmployeeUpdateDTO employee) {
        Optional<Employee> existingEmployee = employeeRepository.findById(employeeId);
        if (!existingEmployee.isPresent()) {
            return new UpdateEmployeeResult(false, "Error, Employee not found");
        }

        Employee employeeinfo =existingEmployee.get();
        if (employee.getCityId() != null) {
            Optional<City> city = cityRepository.findById(employee.getCityId());
            if (!city.isPresent()) {
                return new UpdateEmployeeResult(false, "Error, City not found");
            }
            employeeinfo.setCity(city.get());
        }

        updateEmployeeBasicInfo(employeeinfo, employee);

        employeeRepository.save(employeeinfo);


        return new UpdateEmployeeResult(true, "Employee updated successfully");
    }


    private <T extends Employee> void updateEmployeeBasicInfo(T employee, EmployeeUpdateDTO updateData) {
        if (updateData.getFirstName() != null) {
            employee.setFirstName(updateData.getFirstName());
        }

        if (updateData.getLastName() != null) {
            employee.setLastName(updateData.getLastName());
        }

        if (updateData.getDepartment() != null) {
            employee.setDepartment(updateData.getDepartment());
        }
        if (updateData.getPassword() != null && !updateData.getPassword().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(updateData.getPassword());
            employee.setPasswordHash(encodedPassword);
        }
    }

    @Override
    public DeleteEmployeeResult deleteEmployee(Long employeeId) {
        try {
            Optional<Employee> existingEmployee = employeeRepository.findById(employeeId);
            if (!existingEmployee.isPresent()) {
                return new DeleteEmployeeResult(false, "Error, Employee not found");
            }
            employeeRepository.deleteById(employeeId);
            return new DeleteEmployeeResult(true, "Employee deleted successfully");
        } catch (Exception e) {
            return new DeleteEmployeeResult(false, "Error deleting employee: " + e.getMessage());
        }
    }

}
