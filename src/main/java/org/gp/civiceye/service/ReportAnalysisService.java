package org.gp.civiceye.service;

import java.util.List;
import java.util.Map;

public interface ReportAnalysisService {

    public List<Map<String, Object>> getReportsPerDepartment();

    public List<Map<String, Object>> getReportsPerEmployee();


    public List<Map<String, Object>> getReportsPerGovernorate(Long govId);

    public Long getReportsCountPerCity(Long CityId);

    public Long getReportsCountPerGovernorate(Long govId);
    public Long countReportsPerCityInProgress(Long CityId);
    public Long countReportsPerGovernorateInProgress(Long govId);

    public Long countReportsPerCityResolved(Long CityId);
    public Long countReportsPerGovernorateResolved(Long govId);
}
