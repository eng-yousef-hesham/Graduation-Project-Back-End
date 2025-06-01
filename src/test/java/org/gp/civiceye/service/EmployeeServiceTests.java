package org.gp.civiceye.service;


import org.gp.civiceye.repository.CityRepository;
import org.gp.civiceye.repository.EmployeeRepository;
import org.gp.civiceye.repository.entity.City;
import org.gp.civiceye.repository.entity.Employee;
import org.gp.civiceye.service.impl.EmployeeServiceImpl;
import org.gp.civiceye.service.impl.employee.DeleteEmployeeResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private CityRepository cityRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;
    private City city;

    @BeforeEach
    public void setup() {
        // Create test data
        city = new City();
        city.setCityId(1L);
        city.setName("Test City");

        employee = new Employee();
        employee.setEmpId(1L);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("john.doe@example.com");
        employee.setCity(city);
    }

    @Test
    @DisplayName("Test Case 1: Delete employee who has no assigned reports")
    public void deleteEmployee_WithNoAssignedReports_ShouldDeleteSuccessfully() {
        // Arrange
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        doNothing().when(employeeRepository).deleteById(1L);

        // Act
        DeleteEmployeeResult result = employeeService.deleteEmployee(1L);

        // Assert
        assertTrue(result.isSuccess());
        assertEquals("Employee deleted successfully", result.getMessage());
        verify(employeeRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Test Case 2: Attempt to delete employee with active assigned reports")
    public void deleteEmployee_WithAssignedReports_ShouldFailToDelete() {
        // Arrange
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        doThrow(new DataIntegrityViolationException("Cannot delete employee with assigned reports"))
                .when(employeeRepository).deleteById(1L);

        // Act
        DeleteEmployeeResult result = employeeService.deleteEmployee(1L);

        // Assert
        assertFalse(result.isSuccess());
        assertTrue(result.getMessage().contains("Error deleting employee"));
        verify(employeeRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Test Case: Delete non-existent employee")
    public void deleteEmployee_NonExistentEmployee_ShouldReturnError() {
        // Arrange
        when(employeeRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        DeleteEmployeeResult result = employeeService.deleteEmployee(999L);

        // Assert
        assertFalse(result.isSuccess());
        assertEquals("Error, Employee not found", result.getMessage());
        verify(employeeRepository, never()).deleteById(anyLong());
    }
}