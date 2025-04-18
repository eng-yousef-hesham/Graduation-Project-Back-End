package org.gp.civiceye.service.impl;

import org.gp.civiceye.mapper.employee.EmployeeCreateDTO;
import org.gp.civiceye.mapper.employee.EmployeeDTO;
import org.gp.civiceye.repository.CityRepository;
import org.gp.civiceye.repository.EmployeeRepository;
import org.gp.civiceye.repository.entity.City;
import org.gp.civiceye.repository.entity.Employee;
import org.gp.civiceye.service.EmployeeService;
import org.gp.civiceye.service.impl.employee.AddEmployeeResult;
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


}
