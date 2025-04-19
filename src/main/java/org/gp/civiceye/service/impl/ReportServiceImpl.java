package org.gp.civiceye.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.gp.civiceye.mapper.CreateReportDTO;
import org.gp.civiceye.mapper.ReportDTO;
import org.gp.civiceye.mapper.UpdateReportStatusDTO;
import org.gp.civiceye.repository.*;
import org.gp.civiceye.repository.entity.*;
import org.gp.civiceye.service.ReportService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    ReportRepository reportRepository;
    CityRepository cityRepository;
    CitizenRepository citizenRepository;
    EmployeeRepository employeeRepository;
    StatusHistoryRepository statusHistoryRepository;


    public ReportServiceImpl(ReportRepository reportRepository,CityRepository cityRepository, CitizenRepository citizenRepository,EmployeeRepository employeeRepository ,StatusHistoryRepository statusHistoryRepository) {
        this.reportRepository = reportRepository;
        this.cityRepository = cityRepository;
        this.citizenRepository = citizenRepository;
        this.employeeRepository = employeeRepository;
        this.statusHistoryRepository = statusHistoryRepository;
    }

    public Long submitReport(CreateReportDTO dto) {
        City city = cityRepository.findById(dto.getCityId())
                .orElseThrow(() -> new EntityNotFoundException("City not found with ID: " + dto.getCityId()));

        Citizen citizen = citizenRepository.findById(dto.getCitizenId())
                .orElseThrow(() -> new EntityNotFoundException("Citizen not found with ID: " + dto.getCitizenId()));

        Optional<List<Employee>> employeesOpt = employeeRepository.findByCityAndDepartment(city, dto.getDepartment());
        if (employeesOpt.isEmpty() || employeesOpt.get().isEmpty()) {
            throw new EntityNotFoundException("No employees available for city: " + city.getName());
        }

        Employee assignedEmployee = employeesOpt.get().get(0);

        Report report = dto.toReportEntity(city,citizen,assignedEmployee);

        reportRepository.save(report);

        StatusHistory initialStatus = StatusHistory.builder()
                .status(ReportStatus.Submitted)
                .notes("Initial status")
                .startTime(LocalDateTime.now())
                .endTime(null)
                .report(report)
                .changedByEmployee(assignedEmployee)
                .build();

        statusHistoryRepository.save(initialStatus);
        return report.getReportId();
    }

    @Override
    public List<ReportDTO> getReportsForUser(Long userId) {
        return reportRepository.findAllByCitizen_CitizenId(userId).stream()
                .map(ReportDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReportDTO> getReportsForEmployee(Long employeeId) {
        return reportRepository.findAllByAssignedEmployee_EmpId(employeeId).stream()
                .map(ReportDTO::new).collect(Collectors.toList());
    }

    @Override
    public void updateReportStatus(UpdateReportStatusDTO dto) {
        Report report = reportRepository.findById(dto.getReportId())
                .orElseThrow(() -> new RuntimeException("Report not found"));

        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        StatusHistory lastStatus = statusHistoryRepository.findTopByReportOrderByStartTimeDesc(report)
                .orElseThrow(() -> new RuntimeException("No status history found"));

        lastStatus.setEndTime(LocalDateTime.now());
        statusHistoryRepository.save(lastStatus);

        StatusHistory newStatus = StatusHistory.builder()
                .report(report)
                .status(dto.getNewStatus())
                .startTime(LocalDateTime.now())
                .endTime(null)
                .changedByEmployee(employee)
                .notes(dto.getNotes() != null ? dto.getNotes() : "")
                .build();

        statusHistoryRepository.save(newStatus);

        report.setCurrentStatus(dto.getNewStatus());
        report.setUpdatedAt(LocalDateTime.now());
        reportRepository.save(report);
    }


    @Override
    public List<ReportDTO> GetAllReports() {
            return reportRepository.findAll().stream()
                    .map(ReportDTO::new)
                    .collect(Collectors.toList());
    }
}
