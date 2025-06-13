package org.gp.civiceye.service.analysis.impl;

import org.gp.civiceye.repository.ReportAnalysisRepository;
import org.gp.civiceye.repository.entity.Department;
import org.gp.civiceye.service.analysis.DepartmentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DepartmentAnalysisServiceImpl implements DepartmentAnalysisService {

    private final ReportAnalysisRepository reportAnalysisRepository;

    @Autowired
    public DepartmentAnalysisServiceImpl(ReportAnalysisRepository reportAnalysisRepository) {
        this.reportAnalysisRepository = reportAnalysisRepository;
    }

    @Override
    public Map<Department, Long> getCountOfReportsPerDepartment() {
        return reportAnalysisRepository.findCountOfReportsPerDepartment()
                .stream()
                .collect(Collectors.toMap(
                        obj -> (Department) obj[0],
                        obj -> (Long) obj[1]
                ));
    }

    @Override
    public Map<Department, Long> getCountOfReportsPerDepartmentPerGovernorate(Long govId) {
        return reportAnalysisRepository.findCountOfReportsPerDepartmentPerGovernorate(govId)
                .stream()
                .collect(Collectors.toMap(
                        obj -> (Department) obj[0],
                        obj -> (Long) obj[1]
                ));
    }

    @Override
    public Map<Department, Long> getCountOfReportsPerDepartmentPerCity(Long cityId) {
        return reportAnalysisRepository.findCountOfReportsPerDepartmentPerCity(cityId)
                .stream()
                .collect(Collectors.toMap(
                        obj -> (Department) obj[0],
                        obj -> (Long) obj[1]
                ));
    }
}