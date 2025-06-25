package org.gp.civiceye.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.gp.civiceye.exception.*;
import org.gp.civiceye.mapper.employee.EmployeeReportsCountDTO;
import org.gp.civiceye.mapper.employee.EmployeeUpdateDTO;
import org.gp.civiceye.mapper.report.*;
import org.gp.civiceye.repository.*;
import org.gp.civiceye.repository.entity.*;
import org.gp.civiceye.service.ReportService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
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

        Citizen citizen = citizenRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new CitizenNotFoundException("Citizen not found with email: " + SecurityContextHolder.getContext().getAuthentication().getName()));

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
    public List<ReportDTO> getReportsForAuthenticatedUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return reportRepository.findAllByCitizen_Email(email)
                .stream()
                .map(ReportDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeReportsCountDTO getReportsForEmployeeByStatus(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));

        EmployeeReportsCountDTO employeeReportsCountDTO = new EmployeeReportsCountDTO();

        Arrays.stream(ReportStatus.values()).forEach(status -> {
            int count = reportRepository
                    .countByCurrentStatusAndAssignedEmployee_EmpId(status, employeeId);
            employeeReportsCountDTO.getReportsCount().put(status, count);
        });

        employeeReportsCountDTO.setTotalCount(employeeReportsCountDTO.getReportsCount()
                .values().stream().reduce(0, Integer::sum));
        return employeeReportsCountDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public ReportDTO getReportsById(Long reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new ReportNotFoundException(reportId));
        return new ReportDTO(report);
    }

    @Override
    public void closeReport(CloseReportDTO dto) {
        Report report = reportRepository.findById(dto.getReportId())
                .orElseThrow(() -> new ReportNotFoundException(dto.getReportId()));

        if (report.getCitizen().getEmail()
                .equals(SecurityContextHolder.getContext()
                        .getAuthentication().getName())) {
            throw new UnAuthorizedActionException();
        }

        StatusHistory lastStatus = statusHistoryRepository
                .findTopByReportOrderByStartTimeDesc(report)
                .orElseThrow(StatusHistoryNotFoundException::new);

        if (!lastStatus.getStatus().equals(ReportStatus.Resolved)) {
            throw new InvalidReportStatusException(lastStatus.getStatus(),
                    ReportStatus.Resolved);
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
        employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));
        return reportRepository.findAllByAssignedEmployee_EmpId(employeeId).stream()
                .map(ReportDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<ReportDTO> getReportsForAuthenticatedEmployee() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        employeeRepository.findByEmail(email)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with email: " + email));
        return reportRepository.findAllByAssignedEmployee_Email(email).stream()
                .map(ReportDTO::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateReportStatus(UpdateReportStatusDTO dto) {
        Report report = reportRepository.findById(dto.getReportId())
                .orElseThrow(() -> new ReportNotFoundException(dto.getReportId()));
        String empEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        Employee employee = employeeRepository.findByEmail(empEmail)
                .orElseThrow(() -> new EmployeeNotFoundException(
                        "Employee not found with email: " + empEmail));

        if (!employee.getEmpId().equals(report.getAssignedEmployee().getEmpId())) {
            throw new UnAuthorizedActionException();
        }

        StatusHistory lastStatus = statusHistoryRepository
                .findTopByReportOrderByStartTimeDesc(report)
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
        boolean isEmployee = SecurityContextHolder.getContext()
                .getAuthentication().getAuthorities().contains("ROLE_EMPLOYEE");
        boolean isSpam = newStatus.getStatus().equals(ReportStatus.Cancelled) && isEmployee;
        if (isSpam) {
            newStatus.setEndTime(LocalDateTime.now());
            Citizen citizen = citizenRepository.findById(report.getCitizen()
                            .getCitizenId())
                    .orElseThrow(() -> new CitizenNotFoundException(report
                            .getCitizen().getCitizenId()));
            citizen.setSpamCount(citizen.getSpamCount() + 1);
            if (citizen.getSpamCount() >= 3) {
                citizen.setIsActive(false);
            }
            citizenRepository.save(citizen);
        }
        statusHistoryRepository.save(newStatus);

        report.setCurrentStatus(dto.getNewStatus());
        report.setUpdatedAt(LocalDateTime.now());
        reportRepository.save(report);
    }

    @Override
    public List<ReportDTO> getAllReportsWithOutClosedAndCancelled() {

        List<ReportStatus> statuses = new ArrayList<>();
        statuses.add(ReportStatus.Closed);
        statuses.add(ReportStatus.Cancelled);
        List<Report> reports = reportRepository.findByCurrentStatusNotIn(statuses);
        if (reports.isEmpty()) {
            return null;
        }

        return reports.stream().map(ReportDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<ReportCountDTO> getReportsCountByGovernorate() {
        return governorateRepository.findAll().
                stream().
                map(governorate -> new ReportCountDTO(governorate.getName(), reportRepository.
                        countByCity_Governorate(governorate)))
                .collect(Collectors.toList());
    }

    @Override
    public Page<ReportDTO> getReportsWithFilters(ReportFilterCriteria criteria) {
        Sort sort = criteria.getSortDir().equalsIgnoreCase("desc") ?
                Sort.by(criteria.getSortBy()).descending() :
                Sort.by(criteria.getSortBy()).ascending();

        Pageable pageable = PageRequest.of(criteria.getPage(), criteria.getSize(), sort);

        // Validate and get entities if needed
        City city = null;
        Governorate governorate = null;

        if (criteria.getCityId() != null) {
            city = cityRepository.findById(criteria.getCityId())
                    .orElseThrow(() -> new CityNotFoundException(criteria.getCityId()));
        }

        if (criteria.getGovId() != null) {
            governorate = governorateRepository.findById(criteria.getGovId())
                    .orElseThrow(() -> new GovernorateNotFoundException(criteria.getGovId()));
        }

        // Use the appropriate repository method based on filter combination
        return getReportsPage(criteria, city, governorate, pageable);
    }

    private Page<ReportDTO> getReportsPage(ReportFilterCriteria criteria, City city, Governorate governorate, Pageable pageable) {
        ReportStatus status = criteria.getCurrentStatus();
        Department department = criteria.getDepartment();

        // City-based filtering
        if (city != null) {
            if (status != null && department != null) {
                return reportRepository.findByCurrentStatusAndDepartmentAndCity(status, department, city, pageable)
                        .map(ReportDTO::new);
            } else if (status != null) {
                return reportRepository.findByCurrentStatusAndCity(status, city, pageable)
                        .map(ReportDTO::new);
            } else if (department != null) {
                return reportRepository.findByDepartmentAndCity(department, city, pageable)
                        .map(ReportDTO::new);
            } else {
                return reportRepository.findByCity(city, pageable)
                        .map(ReportDTO::new);
            }
        }

        // Governorate-based filtering
        if (governorate != null) {
            if (status != null && department != null) {
                return reportRepository.findByCurrentStatusAndDepartmentAndCity_Governorate(status, department, governorate, pageable)
                        .map(ReportDTO::new);
            } else if (status != null) {
                return reportRepository.findByCurrentStatusAndCity_Governorate(status, governorate, pageable)
                        .map(ReportDTO::new);
            } else if (department != null) {
                return reportRepository.findByDepartmentAndCity_Governorate(department, governorate, pageable)
                        .map(ReportDTO::new);
            } else {
                return reportRepository.findByCity_Governorate(governorate, pageable)
                        .map(ReportDTO::new);
            }
        }

        // Global filtering (no city or governorate)
        if (status != null && department != null) {
            return reportRepository.findByCurrentStatusAndDepartment(status, department, pageable)
                    .map(ReportDTO::new);
        } else if (status != null) {
            return reportRepository.findByCurrentStatus(status, pageable)
                    .map(ReportDTO::new);
        } else if (department != null) {
            return reportRepository.findByDepartment(department, pageable)
                    .map(ReportDTO::new);
        } else {
            return reportRepository.findAll(pageable)
                    .map(ReportDTO::new);
        }
    }

}