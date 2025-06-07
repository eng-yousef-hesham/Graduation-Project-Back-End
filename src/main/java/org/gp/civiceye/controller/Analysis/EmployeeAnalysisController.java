package org.gp.civiceye.controller.Analysis;

import org.gp.civiceye.mapper.employee.EmployeeCreateDTO;
import org.gp.civiceye.service.EmployeeAnalysisService;
import org.gp.civiceye.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;


@Controller
public class EmployeeAnalysisController {
    EmployeeAnalysisService employeeAnalysisService;
    EmployeeService employeeService;


    @Autowired
    public EmployeeAnalysisController(EmployeeAnalysisService employeeAnalysisService, EmployeeService employeeService) {
        this.employeeService = employeeService;
        this.employeeAnalysisService = employeeAnalysisService;
    }


    @MessageMapping("/addempoyee")
    @SendTo("/topic/employeescount")
    public Long createEmployeews(@Payload EmployeeCreateDTO employee) {
        Long emp = employeeService.createEmployee(employee);

        Long employeesCount = employeeAnalysisService.getEmployeesCount();
        return employeesCount;

    }
}
