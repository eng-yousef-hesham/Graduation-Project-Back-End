package org.gp.civiceye.service.impl;

import org.gp.civiceye.mapper.employee.EmployeeDTO;
import org.gp.civiceye.repository.CityRepository;
import org.gp.civiceye.repository.EmployeeRepository;
import org.gp.civiceye.service.EmployeeAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeAnalysisServiceImpl implements EmployeeAnalysisService {
    private final EmployeeRepository employeeRepository;
    private final CityRepository cityRepository;

    @Autowired
    public EmployeeAnalysisServiceImpl(EmployeeRepository employeeRepository, CityRepository cityRepository) {
        this.employeeRepository = employeeRepository;
        this.cityRepository = cityRepository;


    }

    @Override
    public Long getEmployeesCount() {

        return employeeRepository.count();
    }
}
