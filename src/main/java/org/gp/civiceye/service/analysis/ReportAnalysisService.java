package org.gp.civiceye.service.analysis;

import org.gp.civiceye.mapper.report.ReportDTO;

import java.util.List;
import java.util.Map;

public interface ReportAnalysisService {

    public List<Map<String, Object>> getReportsPerDepartment();

    public List<Map<String, Object>> getReportsPerEmployee();


    public List<Map<String, Object>> getReportsPerGovernorate(Long govId);

    public Long getAllReportsCount();
    public Long getReportsCountPerCity(Long CityId);

    public Long getReportsCountPerGovernorate(Long govId);
    public Long countReportsPerCityInProgress(Long CityId);
    public Long countReportsPerGovernorateInProgress(Long govId);

    public Long countReportsPerCityResolved(Long CityId);
    public Long countReportsPerGovernorateResolved(Long govId);

    public Map<String,Long> initReportNumbersForGovernorate(Long govId);
    public Map<String,Long> initReportNumbersForcity(Long cityId);

    public List<ReportDTO> GetTop4Reports();

    public List<ReportDTO> GetTop4ReportsByGovId(Long govId);
    public List<ReportDTO> GetTop4ReportsByCityId(Long cityId);
}
