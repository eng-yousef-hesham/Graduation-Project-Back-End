package org.gp.civiceye.controller;

import org.gp.civiceye.repository.entity.Department;
import org.gp.civiceye.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/V1")
public class DepartmentController {

    DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }



    @GetMapping("/departments")
    public ResponseEntity<Department[]> getAllDepartments() {

        return new ResponseEntity<>(departmentService.getAllDepartments(), HttpStatus.OK);

    }
}
