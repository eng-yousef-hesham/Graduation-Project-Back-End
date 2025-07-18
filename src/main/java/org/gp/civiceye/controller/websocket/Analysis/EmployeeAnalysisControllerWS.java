package org.gp.civiceye.controller.websocket.Analysis;

import org.gp.civiceye.mapper.employee.EmployeeCreateDTO;
import org.gp.civiceye.service.analysis.EmployeeAnalysisService;
import org.gp.civiceye.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;


@Controller
public class EmployeeAnalysisControllerWS {
    EmployeeAnalysisService employeeAnalysisService;
    EmployeeService employeeService;


    @Autowired
    public EmployeeAnalysisControllerWS(EmployeeAnalysisService employeeAnalysisService, EmployeeService employeeService) {
        this.employeeService = employeeService;
        this.employeeAnalysisService = employeeAnalysisService;
    }


    @MessageMapping("/addempoyee")
    @SendTo("/topic/employeescount")
    public Long createEmployeeWS(@Payload EmployeeCreateDTO employee) {
        Long emp = employeeService.createEmployee(employee);

        Long employeesCount = employeeAnalysisService.getEmployeesCount();
        return employeesCount;

    }
}
