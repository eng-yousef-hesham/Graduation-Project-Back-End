package org.gp.civiceye.controller;

import org.gp.civiceye.mapper.employee.EmployeeCreateDTO;
import org.gp.civiceye.mapper.employee.EmployeeDTO;
import org.gp.civiceye.service.EmployeeService;
import org.gp.civiceye.service.impl.employee.AddEmployeeResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/V1")
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employee")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        List<EmployeeDTO> employees = employeeService.getAllEmployees();
        if (!employees.isEmpty()) {
            return ResponseEntity.ok(employees);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable(name = "employeeId" ) Long employeeId) {
        EmployeeDTO employees = employeeService.getEmployeeById(employeeId);
        if (!(employees ==null)) {
            return ResponseEntity.ok(employees);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/employee")
    public ResponseEntity<String> createEmployee(@RequestBody EmployeeCreateDTO employee) {
        AddEmployeeResult emp = employeeService.createEmployee(employee);
        if (emp.isSuccess()) {
            return new ResponseEntity<>(emp.getMessage(), HttpStatus.OK);
        } else if (emp.getMessage().contains("not found")) {
            return new ResponseEntity<>(emp.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } else {
            return new ResponseEntity<>(emp.getMessage(), HttpStatus.CONFLICT);
        }
    }
}
