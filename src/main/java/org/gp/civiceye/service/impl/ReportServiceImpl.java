package org.gp.civiceye.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.gp.civiceye.exception.*;
import org.gp.civiceye.mapper.report.*;
import org.gp.civiceye.repository.*;
import org.gp.civiceye.repository.entity.*;
import org.gp.civiceye.service.ReportService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
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

    GovernorateRepository governorateRepository;


    public ReportServiceImpl(ReportRepository reportRepository, CityRepository cityRepository, CitizenRepository citizenRepository, EmployeeRepository employeeRepository, StatusHistoryRepository statusHistoryRepository, GovernorateRepository governorateRepository) {
        this.reportRepository = reportRepository;
        this.cityRepository = cityRepository;
        this.citizenRepository = citizenRepository;
        this.employeeRepository = employeeRepository;
        this.statusHistoryRepository = statusHistoryRepository;
        this.governorateRepository = governorateRepository;
    }

    @Transactional
    public Long submitReport(CreateReportDTO dto) {
        City city = cityRepository.findById(dto.getCityId())
                .orElseThrow(() -> new CityNotFoundException(dto.getCityId()));

        Citizen citizen = citizenRepository.findById(dto.getCitizenId())
                .orElseThrow(() -> new CitizenNotFoundException(dto.getCitizenId()));

        Optional<List<Employee>> employeesOpt = employeeRepository.findByCityAndDepartment(city, dto.getDepartment());
        if (employeesOpt.isEmpty() || employeesOpt.get().isEmpty()) {
            throw new EmployeeNotFoundException("No employees available for city: " + city.getName());
        }

        List<Employee> employees = employeesOpt.get();
        employees.sort(Comparator.comparingInt(e -> e.getAssignedReports().size()));
        Employee assignedEmployee = employees.get(0);

        Report report = dto.toReportEntity(city, citizen, assignedEmployee);

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
    @Transactional(readOnly = true)
    public ReportDTO getReportsById(Long reportId) {

        Optional<Report> report = reportRepository.findById(reportId);
        if (report.isEmpty()) {
            throw new ReportNotFoundException(reportId);
        }

        return new ReportDTO(report.get());
    }

    @Override
    public void closeReport(CloseReportDTO dto) {
        Report report = reportRepository.findById(dto.getReportId())
                .orElseThrow(() -> new ReportNotFoundException(dto.getReportId()));

        StatusHistory lastStatus = statusHistoryRepository.findTopByReportOrderByStartTimeDesc(report)
                .orElseThrow(StatusHistoryNotFoundException::new);

        if (!lastStatus.getStatus().equals(ReportStatus.Resolved)) {
            throw new InvalidReportStatusException(lastStatus.getStatus(), ReportStatus.Resolved);
        }
        lastStatus.setEndTime(LocalDateTime.now());
        statusHistoryRepository.save(lastStatus);

        StatusHistory newStatus = StatusHistory.builder()
                .report(report)
                .status(ReportStatus.Closed)
                .startTime(LocalDateTime.now())
                .endTime(null)
                .changedByEmployee(report.getAssignedEmployee())
                .notes("")
                .build();
        report.setCurrentStatus(ReportStatus.Closed);
        report.setRating(dto.getRating());
        Employee employee = report.getAssignedEmployee();
        int reportCount = (int) employee.getAssignedReports().stream().filter(r -> r.getCurrentStatus().equals(ReportStatus.Closed)).count();
        Double prevRating = employee.getRating();
        employee.setRating((prevRating * reportCount + dto.getRating()) / (reportCount + 1));
        reportRepository.save(report);
        statusHistoryRepository.save(newStatus);
    }

    @Override
    public List<ReportDTO> getReportsForEmployee(Long employeeId) {
        return reportRepository.findAllByAssignedEmployee_EmpId(employeeId).stream()
                .map(ReportDTO::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateReportStatus(UpdateReportStatusDTO dto) {
        Report report = reportRepository.findById(dto.getReportId())
                .orElseThrow(() -> new ReportNotFoundException(dto.getReportId()));

        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() -> new EmployeeNotFoundException(dto.getEmployeeId()));

        StatusHistory lastStatus = statusHistoryRepository.findTopByReportOrderByStartTimeDesc(report)
                .orElseThrow(StatusHistoryNotFoundException::new);

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
    public Page<ReportDTO> getAllReports(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return reportRepository.findAll(pageable)
                .map(ReportDTO::new);
    }

    public Page<ReportDTO> getReportsByStatusAndDepartment(ReportStatus currentStatus, Department department, int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return reportRepository.findByCurrentStatusAndDepartment(currentStatus, department, pageable)
                .map(ReportDTO::new);
    }

    public Page<ReportDTO> getReportsByDepartment(Department department, int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return reportRepository.findByDepartment(department, pageable)
                .map(ReportDTO::new);
    }
    public Page<ReportDTO> getReportsByStatus(ReportStatus currentStatus, int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        if (currentStatus == null) {
            return reportRepository.findAll(pageable)
                    .map(ReportDTO::new);
        }

        return reportRepository.findByCurrentStatus(currentStatus, pageable)
                .map(ReportDTO::new);
    }
    @Override
    public List<ReportCountDTO> getReportsCountByGovernorate() {
        return governorateRepository.findAll().
                stream().
                map(governorate -> new ReportCountDTO(governorate.getName(), reportRepository.
                        countByCity_Governorate(governorate)))
                .collect(Collectors.toList());

    }
}
