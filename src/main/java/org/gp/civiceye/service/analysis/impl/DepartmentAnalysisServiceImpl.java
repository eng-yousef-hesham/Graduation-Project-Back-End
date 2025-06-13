package org.gp.civiceye.service.analysis.impl;

import org.gp.civiceye.repository.ReportAnalysisRepository;
import org.gp.civiceye.repository.entity.Department;
import org.gp.civiceye.service.analysis.DepartmentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
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
        List<Object[]> results = reportAnalysisRepository.findCountOfReportsPerDepartment();

        // Convert to map
        Map<Department, Long> countMap = results.stream()
                .collect(Collectors.toMap(
                        row -> (Department) row[0],
                        row -> (Long) row[1]
                ));

        // Step 2: Ensure all enums are present, fill in missing with 0
        Map<Department, Long> finalMap = new LinkedHashMap<>();
        for (Department dept : Department.values()) {
            finalMap.put(dept, countMap.getOrDefault(dept, 0L));
        }

        return finalMap;
    }

    @Override
    public Map<Department, Long> getCountOfReportsPerDepartmentPerGovernorate(Long govId) {
        List<Object[]> results = reportAnalysisRepository.findCountOfReportsPerDepartmentPerGovernorate(govId);

        // Convert to map
        Map<Department, Long> countMap = results.stream()
                .collect(Collectors.toMap(
                        row -> (Department) row[0],
                        row -> (Long) row[1]
                ));

        // Step 2: Ensure all enums are present, fill in missing with 0
        Map<Department, Long> finalMap = new LinkedHashMap<>();
        for (Department dept : Department.values()) {
            finalMap.put(dept, countMap.getOrDefault(dept, 0L));
        }

        return finalMap;
    }

    @Override
    public Map<Department, Long> getCountOfReportsPerDepartmentPerCity(Long cityId) {
        List<Object[]> results = reportAnalysisRepository.findCountOfReportsPerDepartmentPerCity(cityId);

        // Convert to map
        Map<Department, Long> countMap = results.stream()
                .collect(Collectors.toMap(
                        row -> (Department) row[0],
                        row -> (Long) row[1]
                ));

        // Step 2: Ensure all enums are present, fill in missing with 0
        Map<Department, Long> finalMap = new LinkedHashMap<>();
        for (Department dept : Department.values()) {
            finalMap.put(dept, countMap.getOrDefault(dept, 0L));
        }

        return finalMap;
    }
}