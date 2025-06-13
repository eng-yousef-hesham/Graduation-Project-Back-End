package org.gp.civiceye.service.analysis.impl;

import org.gp.civiceye.mapper.employee.EmployeeDTO;
import org.gp.civiceye.repository.CityRepository;
import org.gp.civiceye.repository.EmployeeAnalysisRepository;
import org.gp.civiceye.repository.EmployeeRepository;
import org.gp.civiceye.repository.ReportAnalysisRepository;
import org.gp.civiceye.repository.entity.Employee;
import org.gp.civiceye.service.analysis.EmployeeAnalysisService;
import org.jfree.data.time.Millisecond;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmployeeAnalysisServiceImpl implements EmployeeAnalysisService {
    private final EmployeeRepository employeeRepository;
    private final CityRepository cityRepository;
    private final EmployeeAnalysisRepository employeeAnalysisRepository;
    private final ReportAnalysisRepository reportAnalysisRepository;

    @Autowired
    public EmployeeAnalysisServiceImpl(EmployeeRepository employeeRepository, CityRepository cityRepository,
                                       EmployeeAnalysisRepository employeeAnalysisRepository, ReportAnalysisRepository reportAnalysisRepository) {
        this.employeeRepository = employeeRepository;
        this.cityRepository = cityRepository;
        this.employeeAnalysisRepository = employeeAnalysisRepository;
        this.reportAnalysisRepository = reportAnalysisRepository;


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




    @Override
    public Map<String, Double> get8FastestEmployeeToSolveReportsPerGovernorate(Long govId) {
        List<Object[]> results = reportAnalysisRepository.find8FastestEmployeeToSolveReportsPerGovernorate(govId);
        return Fastest8EmployeeTransformResultsToMap(results);
    }

    @Override
    public Map<String, Double> get8FastestEmployeeToSolveReportsPerCity(Long cityId) {
        List<Object[]> results = reportAnalysisRepository.find8FastestEmployeeToSolveReportsPerCity(cityId);
        return Fastest8EmployeeTransformResultsToMap(results);
    }

    // You'll also need this method for the general case (without location filter)
    @Override
    public Map<String, Double> get8FastestEmployeeToSolveReports() {
        List<Object[]> results = reportAnalysisRepository.find8FastestEmployeeToSolveReports();
        return Fastest8EmployeeTransformResultsToMap(results);
    }

    // this method transforms the results from the repository into a map of EmployeeDTO and total resolution time
    private Map<String, Double> Fastest8EmployeeTransformResultsToMap(List<Object[]> results) {
        Map<String, Double> employeePerformanceMap = new LinkedHashMap<>();

        for (Object[] result : results) {
            String FirstName = (String) result[0];
            String LastName = (String) result[1];
            Long totalResolutionTimeNanos = (Long) result[2]; // This will be in nanoseconds

            // Convert nanoseconds to hours for better readability
            Double totalResolutionTimeHours = totalResolutionTimeNanos / (1000000000.0 * 3600.0);


            // Convert Employee entity to EmployeeDTO
            String employeeName = FirstName + " " + LastName;

            employeePerformanceMap.put(employeeName, totalResolutionTimeHours);
        }

        return employeePerformanceMap;
    }


}
