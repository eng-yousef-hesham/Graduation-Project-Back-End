package org.gp.civiceye.service.analysis.impl;

import org.gp.civiceye.mapper.employee.EmployeeDTO;
import org.gp.civiceye.repository.CityRepository;
import org.gp.civiceye.repository.EmployeeAnalysisRepository;
import org.gp.civiceye.repository.EmployeeRepository;
import org.gp.civiceye.service.analysis.EmployeeAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        Double avg = employeeAnalysisRepository.findAverageRatingPerGovernorate(govId);
        if (avg == null)
            return 0.0;
        else
            return avg;

    }
    @Override
    public Double getAverageRatingPerCity(Long cityId){

        Double avg = employeeAnalysisRepository.findAverageRatingPerCity(cityId);

        if (avg == null)
            return 0.0;
        else
            return avg;

    }
    @Override
    public Double getAverageRating(){

        return employeeAnalysisRepository.findAverageRating();

    }

    @Override
    public List<EmployeeDTO> getTop8RatedEmployees() {
        return employeeAnalysisRepository.findTop8ByOrderByRatingDesc().stream()
                .map(EmployeeDTO::new).toList();
    }

    @Override
    public List<EmployeeDTO> getTop8RatedEmployeesPerGovernorate(Long govId) {
        return employeeAnalysisRepository.findTop8RatedEmployeesPerGovernorate(govId).stream()
                .map(EmployeeDTO::new).toList();
    }

    @Override
    public List<EmployeeDTO> getTop8RatedEmployeesPerCity(Long cityId) {
        return employeeAnalysisRepository.findTop8RatedEmployeesPerCity(cityId).stream()
                .map(EmployeeDTO::new).toList();
    }
}
