package org.gp.civiceye.service.impl;

import org.gp.civiceye.mapper.employee.EmployeeDTO;
import org.gp.civiceye.repository.ReportAnalysisRepository;
import org.gp.civiceye.repository.ReportRepository;
import org.gp.civiceye.service.ReportAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportAnalysisServiceImpl implements ReportAnalysisService {

    private final ReportAnalysisRepository ReportAnalysisRepository;

    @Autowired
    public ReportAnalysisServiceImpl(ReportAnalysisRepository ReportAnalysisRepository) {
        this.ReportAnalysisRepository = ReportAnalysisRepository;
    }

    @Override
    public List<Map<String, Object>> getReportsPerDepartment() {
        return ReportAnalysisRepository.countReportsByDepartment().stream()
                .map(obj -> Map.of("department", obj[0], "reportCount", obj[1]))
                .collect(Collectors.toList());
    }


    @Override
    public List<Map<String, Object>> getReportsPerEmployee() {
        return ReportAnalysisRepository.countReportsPerEmployee().stream()
                .map(obj -> Map.of("employeeId", obj[0], "firstName" ,obj[1],"lastName",obj[2], "reportCount", obj[3]))
                .collect(Collectors.toList());
    }

    @Override
    public List<Map<String, Object>> getReportsPerCity() {
        return ReportAnalysisRepository.countReportsPerCity().stream()
                .map(obj -> Map.of("cityId", obj[0],"cityName", obj[1], "reportCount", obj[2]))
                .collect(Collectors.toList());
    }

}
