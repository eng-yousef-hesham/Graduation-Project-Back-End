package org.gp.civiceye.service;

import jakarta.persistence.EntityNotFoundException;
import org.gp.civiceye.mapper.report.CloseReportDTO;
import org.gp.civiceye.mapper.report.CreateReportDTO;
import org.gp.civiceye.repository.*;
import org.gp.civiceye.repository.entity.*;
import org.gp.civiceye.service.impl.ReportServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReportServiceTests {

    @Mock
    private CityRepository cityRepository;
    @Mock
    private CitizenRepository citizenRepository;
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private ReportRepository reportRepository;
    @Mock
    private StatusHistoryRepository statusHistoryRepository;

    @InjectMocks
    private ReportServiceImpl reportService;

    @Captor
    private ArgumentCaptor<Report> reportCaptor;
    @Captor
    private ArgumentCaptor<StatusHistory> statusCaptor;

    private CreateReportDTO reportDTO;
    private City city;
    private Citizen citizen;
    private Employee employee;
    private Report savedReport;

    @BeforeEach
    public void setup() {
        // Create test data
        reportDTO = CreateReportDTO.builder()
                .title("Broken Street Light")
                .description("Street light not working")
                .contactInfo("test@example.com")
                .cityId(1L)
                .citizenId(2L)
                .latitude(40.7128F)
                .longitude(-74.0060F)
                .department(Department.Roads_and_Transportation_Department)
                .build();

        city = new City();
        city.setCityId(1L);

        citizen = new Citizen();
        citizen.setCitizenId(2L);

        employee = new Employee();
        employee.setEmpId(3L);

        savedReport = new Report();
        savedReport.setReportId(10L);
    }

    @Test
    public void testSubmitReport_SuccessfulSubmission() {
        // Setup mocks
        when(cityRepository.findById(1L)).thenReturn(Optional.of(city));
        when(citizenRepository.findById(2L)).thenReturn(Optional.of(citizen));
        when(employeeRepository.findByCityAndDepartment(city, reportDTO.getDepartment()))
                .thenReturn(Optional.of(new ArrayList<>(List.of(employee))));
        doAnswer(invocation -> {
            Report report = invocation.getArgument(0);
            report.setReportId(10L);
            return report;
        }).when(reportRepository).save(any(Report.class));

        // Call method
        Long reportId = reportService.submitReport(reportDTO);

        // Verify result
        assertEquals(10L, reportId);

        // Verify report was created correctly
        verify(reportRepository).save(reportCaptor.capture());
        Report capturedReport = reportCaptor.getValue();
        assertEquals("Broken Street Light", capturedReport.getTitle());
        assertEquals(city, capturedReport.getCity());
        assertEquals(citizen, capturedReport.getCitizen());
        assertEquals(employee, capturedReport.getAssignedEmployee());
        assertEquals(ReportStatus.Submitted, capturedReport.getCurrentStatus());

        // Verify status history was created
        verify(statusHistoryRepository).save(statusCaptor.capture());
        StatusHistory status = statusCaptor.getValue();
        assertEquals(ReportStatus.Submitted, status.getStatus());
        assertEquals("Initial status", status.getNotes());
        assertEquals(capturedReport, status.getReport()); // Updated to use captured report
    }

    @Test
    public void testSubmitReport_CityNotFound() {
        // Setup mocks
        when(cityRepository.findById(1L)).thenReturn(Optional.empty());

        // Call and verify exception
        Exception exception = assertThrows(RuntimeException.class, () ->
                reportService.submitReport(reportDTO)
        );

        assertEquals("City not found with ID: 1", exception.getMessage());

        // Verify nothing else was called
        verify(cityRepository).findById(1L);
        verifyNoInteractions(reportRepository, statusHistoryRepository);
    }

    @Test
    public void testSubmitReport_CitizenNotFound() {
        // Setup mocks
        when(cityRepository.findById(1L)).thenReturn(Optional.of(city));
        when(citizenRepository.findById(2L)).thenReturn(Optional.empty());

        // Call and verify exception
        Exception exception = assertThrows(RuntimeException.class, () ->
                reportService.submitReport(reportDTO)
        );

        assertEquals("Citizen not found with ID: 2", exception.getMessage());
    }

    @Test
    public void testSubmitReport_NoEmployeeFound() {
        // Setup mocks
        when(cityRepository.findById(1L)).thenReturn(Optional.of(city));
        when(citizenRepository.findById(2L)).thenReturn(Optional.of(citizen));
        when(employeeRepository.findByCityAndDepartment(city, reportDTO.getDepartment())).thenReturn(Optional.of(Collections.emptyList()));

        // Call and verify exception
        assertThrows(EntityNotFoundException.class, () ->
                reportService.submitReport(reportDTO)
        );
    }
    @Test
    public void closeReport_WhenReportIsResolved_ShouldCloseSuccessfully() {
        // Arrange
        CloseReportDTO closeReportDTO = new CloseReportDTO();
        closeReportDTO.setReportId(10L);

        Report report = new Report();
        report.setReportId(10L);
        report.setAssignedEmployee(employee);
        report.setCurrentStatus(ReportStatus.Resolved);

        StatusHistory lastStatus = StatusHistory.builder()
                .report(report)
                .status(ReportStatus.Resolved)
                .startTime(LocalDateTime.now().minusDays(1))
                .endTime(null)
                .build();

        when(reportRepository.findById(10L)).thenReturn(Optional.of(report));
        when(statusHistoryRepository.findTopByReportOrderByStartTimeDesc(report))
                .thenReturn(Optional.of(lastStatus));

        // Act
        reportService.closeReport(closeReportDTO);

        // Assert
        // Use ArgumentCaptor to capture both saves to statusHistoryRepository
        verify(statusHistoryRepository, times(2)).save(statusCaptor.capture());
        verify(reportRepository).save(reportCaptor.capture());

        // Get both captured values (last one is the new status, first one is the updated old status)
        List<StatusHistory> capturedStatuses = statusCaptor.getAllValues();
        StatusHistory updatedOldStatus = capturedStatuses.get(0);
        StatusHistory newClosedStatus = capturedStatuses.get(1);
        Report capturedReport = reportCaptor.getValue();

        // Verify the old status was ended
        assertNotNull(updatedOldStatus.getEndTime());
        assertEquals(ReportStatus.Resolved, updatedOldStatus.getStatus());

        // Verify the new status was created correctly
        assertEquals(ReportStatus.Closed, newClosedStatus.getStatus());
        assertEquals(report, newClosedStatus.getReport());
        assertNotNull(newClosedStatus.getStartTime());
        assertNull(newClosedStatus.getEndTime());

        // Verify the report was updated
        assertEquals(ReportStatus.Closed, capturedReport.getCurrentStatus());
    }
    @Test
    @DisplayName("Test Case 2: Citizen cannot close an unresolved report")
    public void closeReport_WhenReportIsNotResolved_ShouldThrowException() {
        // Arrange
        CloseReportDTO closeReportDTO = new CloseReportDTO();
        closeReportDTO.setReportId(10L);

        Report report = new Report();
        report.setReportId(10L);
        report.setCurrentStatus(ReportStatus.In_Progress);

        StatusHistory lastStatus = StatusHistory.builder()
                .report(report)
                .status(ReportStatus.In_Progress)
                .startTime(LocalDateTime.now().minusDays(1))
                .endTime(null)
                .build();

        when(reportRepository.findById(10L)).thenReturn(Optional.of(report));
        when(statusHistoryRepository.findTopByReportOrderByStartTimeDesc(report))
                .thenReturn(Optional.of(lastStatus));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reportService.closeReport(closeReportDTO);
        });

        assertEquals("Report is not resolved yet", exception.getMessage());

        // Verify no status updates were saved
        verify(statusHistoryRepository, never()).save(any(StatusHistory.class));
        verify(reportRepository, never()).save(any(Report.class));
    }

    @Test
    @DisplayName("Test Case 3: Citizen cannot close a report that has already been closed")
    public void closeReport_WhenReportIsAlreadyClosed_ShouldThrowException() {
        // Arrange
        CloseReportDTO closeReportDTO = new CloseReportDTO();
        closeReportDTO.setReportId(10L);

        Report report = new Report();
        report.setReportId(10L);
        report.setCurrentStatus(ReportStatus.Closed);

        StatusHistory lastStatus = StatusHistory.builder()
                .report(report)
                .status(ReportStatus.Closed)
                .startTime(LocalDateTime.now().minusDays(1))
                .endTime(null)
                .build();

        when(reportRepository.findById(10L)).thenReturn(Optional.of(report));
        when(statusHistoryRepository.findTopByReportOrderByStartTimeDesc(report))
                .thenReturn(Optional.of(lastStatus));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reportService.closeReport(closeReportDTO);
        });

        assertEquals("Report is not resolved yet", exception.getMessage());

        // Verify no status updates were saved
        verify(statusHistoryRepository, never()).save(any(StatusHistory.class));
        verify(reportRepository, never()).save(any(Report.class));
    }
}