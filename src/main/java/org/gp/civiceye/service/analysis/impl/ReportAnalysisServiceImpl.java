package org.gp.civiceye.service.analysis.impl;

import org.gp.civiceye.exception.ReportsIsEmptyException;
import org.gp.civiceye.mapper.CityReportCountDTO;
import org.gp.civiceye.mapper.report.ReportDTO;
import org.gp.civiceye.repository.ReportAnalysisRepository;
import org.gp.civiceye.service.analysis.ReportAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public List<Map<String, Object>> getReportsPerGovernorate(Long govId) {
        return ReportAnalysisRepository.countReportsPerCityByGovernorate(govId).stream()
                .map(obj -> Map.of("cityId", obj[0],"cityName", obj[1], "reportCount", obj[2]))
                .collect(Collectors.toList());
    }

    @Override
    public Map<String,Long> initReportNumbersForGovernorate(Long govId){
        return Stream.of(Map.entry("allReports",getReportsCountPerGovernorate(govId)),
                Map.entry("allInProgressReports",countReportsPerGovernorateInProgress(govId)),
                Map.entry("allResolvedReports",countReportsPerGovernorateResolved(govId)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public Map<String, Long> initReportNumbersForcity(Long cityId) {
        return Stream.of(
                Map.entry("allReports", getReportsCountPerCity(cityId)),
                Map.entry("allInProgressReports", countReportsPerCityInProgress(cityId)),
                Map.entry("allResolvedReports", countReportsPerCityResolved(cityId)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public Long getAllReportsCount() {
        return ReportAnalysisRepository.findCountOfAllReports().describeConstable().
                orElseThrow(ReportsIsEmptyException::new);
    }

    @Override
    public Long getReportsCountPerCity(Long CityId) {
        return ReportAnalysisRepository.countReportsPerCity(CityId).describeConstable().
                orElseThrow(ReportsIsEmptyException::new);
    }

    @Override
    public Long getReportsCountPerGovernorate(Long govId) {
        return ReportAnalysisRepository.countReportsInGovernorate(govId).describeConstable().
                orElseThrow(ReportsIsEmptyException::new);
    }

    @Override
    public Long countReportsPerCityInProgress(Long CityId) {
        return ReportAnalysisRepository.countReportsPerCityInProgress(CityId).describeConstable().
                orElseThrow(ReportsIsEmptyException::new);
    }

    @Override
    public Long countReportsPerGovernorateInProgress(Long govId) {
        return ReportAnalysisRepository.countReportsPerGovernorateInProgress(govId).describeConstable().
                orElseThrow(ReportsIsEmptyException::new);
    }

    @Override
    public Long countReportsPerCityResolved(Long CityId) {
        return ReportAnalysisRepository.countReportsPerCityResolved(CityId).describeConstable().
                orElseThrow(ReportsIsEmptyException::new);
    }

    @Override
    public Long countReportsPerGovernorateResolved(Long govId) {
        return ReportAnalysisRepository.countReportsPerGovernorateResolved(govId).describeConstable().
                orElseThrow(ReportsIsEmptyException::new);
    }

    @Override
    @Transactional
    public List<ReportDTO> GetTop4Reports() {
        return ReportAnalysisRepository.findTop4ByOrderByCreatedAtDesc().stream()
                .map(ReportDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<ReportDTO> GetTop4ReportsByGovId(Long govId) {
        return ReportAnalysisRepository.findTop4ByGovernorateId(govId).stream()
                .map(ReportDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<ReportDTO> GetTop4ReportsByCityId(Long cityId) {
        return ReportAnalysisRepository.findTop4ByCityId(cityId).stream()
                .map(ReportDTO::new)
                .collect(Collectors.toList());
    }



    @Override
    public List<CityReportCountDTO> getAverageTimeToSolveReportInCities() {
        return ReportAnalysisRepository.findAverageTimeToSolveReportInCities().stream()
                .map(obj -> new CityReportCountDTO((String) obj[1], (Double) obj[2]))
                .collect(Collectors.toList());
    }

    @Override
    public List<CityReportCountDTO> getAverageTimeToSolveReportInCity(Long cityId) {
        return ReportAnalysisRepository.findAverageTimeToSolveReportInCity(cityId).stream()
                .map(obj -> new CityReportCountDTO((String) obj[1], (Double) obj[2]))
                .collect(Collectors.toList());
    }

    @Override
    public List<CityReportCountDTO> getAverageTimeToSolveReportInCitiesPerGovernorate(Long govId) {
        return ReportAnalysisRepository.findAverageTimeToSolveReportInCitiesPerGovernorate(govId).stream()
                .map(obj -> new CityReportCountDTO((String) obj[1], (Double) obj[2]))
                .collect(Collectors.toList());
    }
}
