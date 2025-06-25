package org.gp.civiceye.controller.rest.analysis;

import org.gp.civiceye.repository.entity.Department;
import org.gp.civiceye.service.analysis.DepartmentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/V1")
public class DepartmentAnalysisController {

    private final DepartmentAnalysisService departmentAnalysisService;

    @Autowired
    public DepartmentAnalysisController(DepartmentAnalysisService departmentAnalysisService) {
        this.departmentAnalysisService = departmentAnalysisService;

    }

    @GetMapping("/init/department/report/numbers/master")
    public ResponseEntity<Map<Department,Long>> getCountOfReportsPerDepartment() {
        return ResponseEntity.ok(departmentAnalysisService.getCountOfReportsPerDepartment());
    }
    @GetMapping("/init/department/report/numbers/gov/{govId}")
    public ResponseEntity<Map<Department,Long>> getCountOfReportsPerDepartmentPerGovernorate(@PathVariable(name = "govId" ) Long govId) {
        return ResponseEntity.ok(departmentAnalysisService.getCountOfReportsPerDepartmentPerGovernorate(govId));
    }
    @GetMapping("/init/department/report/numbers/city/{cityId}")
    public ResponseEntity<Map<Department,Long>> getCountOfReportsPerDepartmentPerCity(@PathVariable(name = "cityId" ) Long cityId) {
        return ResponseEntity.ok(departmentAnalysisService.getCountOfReportsPerDepartmentPerCity(cityId));
    }

}
