package org.gp.civiceye.controller.rest.analysis;

import org.gp.civiceye.mapper.employee.EmployeeDTO;
import org.gp.civiceye.service.analysis.EmployeeAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class EmployeeAnalysisController {

    EmployeeAnalysisService employeeAnalysisService;

    @Autowired
    public EmployeeAnalysisController(EmployeeAnalysisService employeeAnalysisService) {
        this.employeeAnalysisService = employeeAnalysisService;
    }

    @GetMapping("/init/employees/rate/gov/{govId}")
    public ResponseEntity<Double> initEmployeesRateForGovernorate(@PathVariable(name = "govId" ) Long govId) {

        return ResponseEntity.ok(employeeAnalysisService.getAverageRatingPerGovernorate(govId));
    }
    @GetMapping("/init/employees/rate/city/{cityId}")
    public ResponseEntity<Double> initEmployeesRateForCity(@PathVariable(name = "cityId" ) Long cityId) {

        return ResponseEntity.ok(employeeAnalysisService.getAverageRatingPerCity(cityId));
    }
    @GetMapping("/init/employees/rate/master")
    public ResponseEntity<Double> initEmployeesRateForMAster() {

        return ResponseEntity.ok(employeeAnalysisService.getAverageRating());
    }

    @GetMapping("/init/employees/rate/top-8/master")
    public ResponseEntity<List<EmployeeDTO>> GetTop8Employees() {

        return ResponseEntity.ok(employeeAnalysisService.getTop8RatedEmployees());
    }
    @GetMapping("/init/employees/rate/top-8/gov/{govId}")
    public ResponseEntity<List<EmployeeDTO>> GetTop8EmployeesForGovernorate(@PathVariable(name = "govId" ) Long govId) {

        return ResponseEntity.ok(employeeAnalysisService.getTop8RatedEmployeesPerGovernorate(govId));
    }
    @GetMapping("/init/employees/rate/top-8/city/{cityId}")
    public ResponseEntity<List<EmployeeDTO>> GetTop8EmployeesForCity(@PathVariable(name = "cityId" ) Long cityId) {

        return ResponseEntity.ok(employeeAnalysisService.getTop8RatedEmployeesPerCity(cityId));
    }

    @GetMapping("/init/employees/fast/top-8/master")
    public ResponseEntity<Map<String, Double>> GetTop8FastEmployees() {

        return ResponseEntity.ok(employeeAnalysisService.get8FastestEmployeeToSolveReports());
    }
    @GetMapping("/init/employees/fast/top-8/gov/{govId}")
    public ResponseEntity<Map<String, Double>> GetTop8FastEmployeesForGovernorate(@PathVariable(name = "govId" ) Long govId) {

        return ResponseEntity.ok(employeeAnalysisService.get8FastestEmployeeToSolveReportsPerGovernorate(govId));
    }
    @GetMapping("/init/employees/fast/top-8/city/{cityId}")
    public ResponseEntity<Map<String, Double>> GetTop8FastEmployeesForCity(@PathVariable(name = "cityId" ) Long cityId) {

        return ResponseEntity.ok(employeeAnalysisService.get8FastestEmployeeToSolveReportsPerCity(cityId));
    }


}
