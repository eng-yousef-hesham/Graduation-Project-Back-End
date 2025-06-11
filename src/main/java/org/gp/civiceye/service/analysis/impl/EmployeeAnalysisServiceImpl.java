package org.gp.civiceye.service.analysis.impl;

import org.gp.civiceye.repository.CityRepository;
import org.gp.civiceye.repository.EmployeeAnalysisRepository;
import org.gp.civiceye.repository.EmployeeRepository;
import org.gp.civiceye.service.analysis.EmployeeAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeAnalysisServiceImpl implements EmployeeAnalysisService {
    private final EmployeeRepository employeeRepository;
    private final CityRepository cityRepository;
    private final EmployeeAnalysisRepository employeeAnalysisRepository;

    @Autowired
    public EmployeeAnalysisServiceImpl(EmployeeRepository employeeRepository, CityRepository cityRepository, EmployeeAnalysisRepository employeeAnalysisRepository) {
        this.employeeRepository = employeeRepository;
        this.cityRepository = cityRepository;
        this.employeeAnalysisRepository = employeeAnalysisRepository;


    }

    @Override
    public Long getEmployeesCount() {

        return employeeRepository.count();
    }

    @Override
    public Double getAverageRatingPerGovernorate(Long govId){

        return employeeAnalysisRepository.findAverageRatingPerGovernorate(govId);

    }
    @Override
    public Double getAverageRatingPerCity(Long cityId){

        return employeeAnalysisRepository.findAverageRatingPerCity(cityId);

    }
    @Override
    public Double getAverageRating(){

        return employeeAnalysisRepository.findAverageRating();

    }
}
