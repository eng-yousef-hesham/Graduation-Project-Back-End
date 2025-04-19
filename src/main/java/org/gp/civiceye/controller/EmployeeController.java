package org.gp.civiceye.controller;

import org.gp.civiceye.mapper.employee.EmployeeCreateDTO;
import org.gp.civiceye.mapper.employee.EmployeeDTO;
import org.gp.civiceye.mapper.employee.EmployeeUpdateDTO;
import org.gp.civiceye.service.EmployeeService;
import org.gp.civiceye.service.impl.employee.AddEmployeeResult;
import org.gp.civiceye.service.impl.employee.DeleteEmployeeResult;
import org.gp.civiceye.service.impl.employee.UpdateEmployeeResult;
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

    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        List<EmployeeDTO> employees = employeeService.getAllEmployees();
        if (!employees.isEmpty()) {
            return ResponseEntity.ok(employees);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/employees/{employeeId}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable(name = "employeeId" ) Long employeeId) {
        EmployeeDTO employees = employeeService.getEmployeeById(employeeId);
        if (!(employees ==null)) {
            return ResponseEntity.ok(employees);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/employees")
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

    @PutMapping("/employees/{employeeId}")
    public ResponseEntity<String> updateEmployee(@PathVariable(name = "employeeId") Long employeeId,
                                                 @RequestBody EmployeeUpdateDTO employee) {
        UpdateEmployeeResult emp = employeeService.updateEmployee(employeeId, employee);
        if (emp.isSuccess()) {
            return new ResponseEntity<>(emp.getMessage(), HttpStatus.OK);
        } else if (emp.getMessage().contains("not found")) {
            return new ResponseEntity<>(emp.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } else {
            return new ResponseEntity<>(emp.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/employees/{employeeId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable(name = "employeeId") Long employeeId) {
        DeleteEmployeeResult emp = employeeService.deleteEmployee(employeeId);
        if (emp.isSuccess()) {
            return new ResponseEntity<>(emp.getMessage(), HttpStatus.OK);
        } else if (emp.getMessage().contains("not found")) {
            return new ResponseEntity<>(emp.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } else {
            return new ResponseEntity<>(emp.getMessage(), HttpStatus.CONFLICT);
        }
    }
}
