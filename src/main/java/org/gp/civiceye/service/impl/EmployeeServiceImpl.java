package org.gp.civiceye.service.impl;

import org.gp.civiceye.exception.CityNotFoundException;
import org.gp.civiceye.exception.EmployeeAlreadyExistsException;
import org.gp.civiceye.exception.EmployeeNotFoundException;
import org.gp.civiceye.mapper.employee.EmployeeCreateDTO;
import org.gp.civiceye.mapper.employee.EmployeeDTO;
import org.gp.civiceye.mapper.employee.EmployeeUpdateDTO;
import org.gp.civiceye.repository.CityRepository;
import org.gp.civiceye.repository.EmployeeRepository;
import org.gp.civiceye.repository.entity.City;
import org.gp.civiceye.repository.entity.Department;
import org.gp.civiceye.repository.entity.Employee;
import org.gp.civiceye.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Page<EmployeeDTO> getAllEmployees(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return employeeRepository.findAll(pageable).map(employee -> EmployeeDTO.builder()
                        .empId(employee.getEmpId())
                        .nationalId(employee.getNationalId())
                        .firstName(employee.getFirstName())
                        .lastName(employee.getLastName())
                        .fullName(employee.getFirstName() + " " + employee.getLastName())
                        .email(employee.getEmail())
                        .department(employee.getDepartment())
                        .city(employee.getCity().getName())
                        .cityId(employee.getCity().getCityId())
                        .governorate(employee.getCity().getGovernorate().getName())
                        .governorateId(employee.getCity().getGovernorate().getGovernorateId())
                        .createdAt(employee.getCreatedAt())
                        .isActive(employee.getIsActive())
                        .rating(employee.getRating())
                        .build()
                );
    }

    @Override
    public Page<EmployeeDTO> getAllEmployeesByDepartment(int page, int size, Department department) {
        Pageable pageable = PageRequest.of(page, size);
        return employeeRepository.findByDepartment(department, pageable).map(employee -> EmployeeDTO.builder()
                        .empId(employee.getEmpId())
                        .nationalId(employee.getNationalId())
                        .firstName(employee.getFirstName())
                        .lastName(employee.getLastName())
                        .fullName(employee.getFirstName() + " " + employee.getLastName())
                        .email(employee.getEmail())
                        .department(employee.getDepartment())
                        .city(employee.getCity().getName())
                        .cityId(employee.getCity().getCityId())
                        .governorate(employee.getCity().getGovernorate().getName())
                        .governorateId(employee.getCity().getGovernorate().getGovernorateId())
                        .createdAt(employee.getCreatedAt())
                        .isActive(employee.getIsActive())
                        .rating(employee.getRating())
                        .build()
                );
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        return employeeRepository.findById(id).map(employee -> EmployeeDTO.builder()
                        .empId(employee.getEmpId())
                        .nationalId(employee.getNationalId())
                        .firstName(employee.getFirstName())
                        .lastName(employee.getLastName())
                        .fullName(employee.getFirstName() + " " + employee.getLastName())
                        .email(employee.getEmail())
                        .department(employee.getDepartment())
                        .city(employee.getCity().getName())
                        .cityId(employee.getCity().getCityId())
                        .governorate(employee.getCity().getGovernorate().getName())
                        .governorateId(employee.getCity().getGovernorate().getGovernorateId())
                        .createdAt(employee.getCreatedAt())
                        .isActive(employee.getIsActive())
                        .rating(employee.getRating())
                        .build())
                .orElseThrow(() -> new EmployeeNotFoundException(id));

    }

    public Long createEmployee(EmployeeCreateDTO employee) {
        String password = employee.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        employee.setPassword(encodedPassword);

        employeeRepository.findByEmailOrNationalId(employee.getEmail(), employee.getNationalId()).ifPresent(emp -> {
            throw new EmployeeAlreadyExistsException(employee.getEmail(), employee.getNationalId());
        });

        Long cityId = employee.getCityId();
        City city = cityRepository.findById(cityId).orElseThrow(() -> new CityNotFoundException(cityId));

        Employee emp = Employee.builder()
                .nationalId(employee.getNationalId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .passwordHash(employee.getPassword())
                .department(employee.getDepartment())
                .city(city)
                .isActive(true)
                .rating(0.0)
                .build();

        Employee savedEmployee = employeeRepository.save(emp);
        return savedEmployee.getEmpId();
    }

    @Override
    public Long updateEmployee(Long employeeId, EmployeeUpdateDTO employee) {
        Employee employeeInfo = employeeRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException(employeeId));

        City city = cityRepository.findById(employee.getCityId()).orElseThrow(() -> new CityNotFoundException(employee.getCityId()));
        employeeInfo.setCity(city);

        updateEmployeeBasicInfo(employeeInfo, employee);
        employeeRepository.save(employeeInfo);
        return employeeId;
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
    public Long deleteEmployee(Long employeeId) {

        Employee existingEmployee = employeeRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException(employeeId));
        employeeRepository.deleteById(employeeId);
        return employeeId;
    }
}
