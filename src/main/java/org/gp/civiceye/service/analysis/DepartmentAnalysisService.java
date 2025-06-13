package org.gp.civiceye.service.analysis;

import org.gp.civiceye.repository.entity.Department;

import java.util.Map;

public interface DepartmentAnalysisService {
    Map<Department,Long> getCountOfReportsPerDepartment();
    Map<Department,Long> getCountOfReportsPerDepartmentPerGovernorate(Long govId);
    Map<Department,Long> getCountOfReportsPerDepartmentPerCity(Long cityId);
}
