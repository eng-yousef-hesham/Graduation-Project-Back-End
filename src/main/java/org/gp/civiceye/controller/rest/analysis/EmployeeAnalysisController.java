package org.gp.civiceye.controller.rest.analysis;

import org.gp.civiceye.service.analysis.EmployeeAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/V1")
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


}
