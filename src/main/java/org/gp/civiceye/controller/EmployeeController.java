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
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
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
    public ResponseEntity<Page<EmployeeDTO>> getAllEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Department department
    ) {
        Page<EmployeeDTO> employees;
        if (department == null)
            employees = employeeService.getAllEmployees(page, size);
        employees = employeeService.getAllEmployeesByDepartment(page, size, department);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/employees/{employeeId}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable(name = "employeeId") Long employeeId) {
        EmployeeDTO employee = employeeService.getEmployeeById(employeeId);
        return ResponseEntity.ok(employee);
    }

    @PostMapping("/employees")
    public ResponseEntity<String> createEmployee(@RequestBody EmployeeCreateDTO employee) {
        Long emp = employeeService.createEmployee(employee);
        return new ResponseEntity<>("Employee Created with ID: " + emp, HttpStatus.OK);
    }

    @PutMapping("/employees/{employeeId}")
    public ResponseEntity<String> updateEmployee(@PathVariable(name = "employeeId") Long employeeId,
                                                 @RequestBody EmployeeUpdateDTO employee) {
        Long id = employeeService.updateEmployee(employeeId, employee);
        return new ResponseEntity<>("Employee Updated with ID: " + id, HttpStatus.OK);
    }

    @DeleteMapping("/employees/{employeeId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable(name = "employeeId") Long employeeId) {
        Long id = employeeService.deleteEmployee(employeeId);
        return new ResponseEntity<>("Employee Deleted with ID: " + id, HttpStatus.OK);
    }
}
