package org.gp.civiceye.service.analysis;

import org.gp.civiceye.mapper.employee.EmployeeDTO;

import java.util.List;
import java.util.Map;

public interface EmployeeAnalysisService {

    public Long getEmployeesCount();

    public Double getAverageRatingPerGovernorate(Long govId);

    public Double getAverageRatingPerCity(Long cityId);

    public Double getAverageRating();

    List<EmployeeDTO> getTop8RatedEmployees();

    List<EmployeeDTO> getTop8RatedEmployeesPerGovernorate(Long govId);

    List<EmployeeDTO> getTop8RatedEmployeesPerCity(Long cityId);

    Map<String, Double> get8FastestEmployeeToSolveReports();

    Map<String, Double> get8FastestEmployeeToSolveReportsPerGovernorate(Long govId);

    Map<String, Double> get8FastestEmployeeToSolveReportsPerCity(Long cityId);

}

