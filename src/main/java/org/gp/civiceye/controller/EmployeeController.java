package org.gp.civiceye.controller;

import org.gp.civiceye.mapper.employee.EmployeeCreateDTO;
import org.gp.civiceye.mapper.employee.EmployeeDTO;
import org.gp.civiceye.mapper.employee.EmployeeUpdateDTO;
import org.gp.civiceye.repository.entity.Department;
import org.gp.civiceye.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<Page<EmployeeDTO>> getAllEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Department department,
            @RequestParam(required = false) Long cityId,
            @RequestParam(required = false) Long govId
    ) {
        Page<EmployeeDTO> employees = employeeService
                .getAllEmployees(page, size, department, cityId, govId);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable(name = "employeeId") Long employeeId) {
        EmployeeDTO employee = employeeService.getEmployeeById(employeeId);
        return ResponseEntity.ok(employee);
    }

    @PostMapping
    public ResponseEntity<String> createEmployee(@RequestBody EmployeeCreateDTO employee) {
        Long emp = employeeService.createEmployee(employee);
        return new ResponseEntity<>("Employee Created with ID: " + emp, HttpStatus.OK);
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<String> updateEmployee(@PathVariable(name = "employeeId") Long employeeId,
                                                 @RequestBody EmployeeUpdateDTO employee) {
        Long id = employeeService.updateEmployee(employeeId, employee);
        return new ResponseEntity<>("Employee Updated with ID: " + id, HttpStatus.OK);
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable(name = "employeeId") Long employeeId) {
        Long id = employeeService.deleteEmployee(employeeId);
        return new ResponseEntity<>("Employee Deleted with ID: " + id, HttpStatus.OK);
    }
}
