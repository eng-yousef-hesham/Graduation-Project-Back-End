package org.gp.civiceye.service;

import org.gp.civiceye.mapper.employee.EmployeeCreateDTO;
import org.gp.civiceye.mapper.employee.EmployeeDTO;
import org.gp.civiceye.mapper.employee.EmployeeUpdateDTO;
import org.gp.civiceye.repository.CityRepository;
import org.gp.civiceye.repository.EmployeeRepository;
import org.gp.civiceye.repository.entity.City;
import org.gp.civiceye.repository.entity.Department;
import org.gp.civiceye.repository.entity.Employee;
import org.gp.civiceye.repository.entity.Governorate;
import org.gp.civiceye.service.impl.EmployeeServiceImpl;
import org.gp.civiceye.service.impl.employee.AddEmployeeResult;
import org.gp.civiceye.service.impl.employee.DeleteEmployeeResult;
import org.gp.civiceye.service.impl.employee.UpdateEmployeeResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EmployeeServiceTests {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private CityRepository cityRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllEmployees_Success() {
        // Arrange
        List<Employee> employees = new ArrayList<>();
        Employee employee = new Employee();
        employee.setEmpId(1L);
        employee.setNationalId("123");
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("test@test.com");
        employee.setDepartment(Department.Roads_and_Transportation_Department);
        employee.setIsActive(true);
        City city = new City();
        city.setName("testCity");
        Governorate governorate = new Governorate();
        governorate.setName("testGovernorate");
        city.setGovernorate(governorate);
        employee.setCity(city);
        employees.add(employee);

        when(employeeRepository.findAll()).thenReturn(employees);

        // Act
        List<EmployeeDTO> result = employeeService.getAllEmployees();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John Doe", result.getFirst().getFullName());
    }

    @Test
    void testGetAllEmployees_Empty() {
        // Arrange
        when(employeeRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<EmployeeDTO> result = employeeService.getAllEmployees();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetEmployeeById_Success() {
        // Arrange
        Employee employee = new Employee();
        employee.setEmpId(1L);
        employee.setNationalId("123");
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("test@test.com");
        employee.setDepartment(Department.Roads_and_Transportation_Department);
        employee.setIsActive(true);
        City city = new City();
        city.setName("testCity");
        Governorate governorate = new Governorate();
        governorate.setName("testGovernorate");
        city.setGovernorate(governorate);
        employee.setCity(city);

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        // Act
        EmployeeDTO result = employeeService.getEmployeeById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("John Doe", result.getFullName());
    }

    @Test
    void testGetEmployeeById_NotFound() {
        // Arrange
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        EmployeeDTO result = employeeService.getEmployeeById(1L);

        // Assert
        assertNull(result);
    }

    @Test
    void testCreateEmployee_Success() {
        // Arrange
        EmployeeCreateDTO createDTO = new EmployeeCreateDTO();
        createDTO.setNationalId("123456789");
        createDTO.setFirstName("John");
        createDTO.setLastName("Doe");
        createDTO.setEmail("john.doe@example.com");
        createDTO.setPassword("password");
        createDTO.setDepartment(Department.Roads_and_Transportation_Department);
        createDTO.setCityId(1L);

        City mockCity = new City();
        mockCity.setCityId(1L);
        mockCity.setName("City");

        when(cityRepository.findById(1L)).thenReturn(Optional.of(mockCity));
        when(employeeRepository.findByEmailOrNationalId("john.doe@example.com", "123456789")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        Employee mockEmployee = new Employee();
        mockEmployee.setEmpId(1L);

        when(employeeRepository.save(any(Employee.class))).thenReturn(mockEmployee);

        // Act
        AddEmployeeResult result = employeeService.createEmployee(createDTO);

        // Assert
        assertTrue(result.isSuccess());
        assertEquals("Employee created successfully", result.getMessage());
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void testCreateEmployee_DuplicateEmailOrNationalId() {
        // Arrange
        EmployeeCreateDTO createDTO = new EmployeeCreateDTO();
        createDTO.setEmail("john.doe@example.com");
        createDTO.setNationalId("123456789");
        Employee existingEmployee = new Employee();
        existingEmployee.setEmail("john.doe@example.com");
        existingEmployee.setNationalId("123456789");

        when(employeeRepository.findByEmailOrNationalId("john.doe@example.com", "123456789")).thenReturn(Optional.of(existingEmployee));

        // Act
        AddEmployeeResult result = employeeService.createEmployee(createDTO);

        // Assert
        assertFalse(result.isSuccess());
        assertEquals("Error, Email or National ID Found, Employee already exists", result.getMessage());
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void testCreateEmployee_InvalidCityId() {
        // Arrange
        EmployeeCreateDTO createDTO = new EmployeeCreateDTO();
        createDTO.setCityId(1L);

        when(cityRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        AddEmployeeResult result = employeeService.createEmployee(createDTO);

        // Assert
        assertFalse(result.isSuccess());
        assertEquals("Error, City not found", result.getMessage());
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void testUpdateEmployee_Success() {
        // Arrange
        Long employeeId = 1L;
        EmployeeUpdateDTO updateDTO = new EmployeeUpdateDTO();
        updateDTO.setFirstName("UpdatedFirstName");
        updateDTO.setCityId(2L);

        Employee existingEmployee = new Employee();
        existingEmployee.setEmpId(employeeId);
        existingEmployee.setFirstName("OriginalFirstName");
        existingEmployee.setDepartment(Department.Roads_and_Transportation_Department);
        City mockCity = new City();
        mockCity.setCityId(2L);
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(existingEmployee));
        when(cityRepository.findById(2L)).thenReturn(Optional.of(mockCity));
        when(employeeRepository.save(any(Employee.class))).thenReturn(existingEmployee);

        // Act
        UpdateEmployeeResult result = employeeService.updateEmployee(employeeId, updateDTO);

        // Assert
        assertTrue(result.isSuccess());
        assertEquals("Employee updated successfully", result.getMessage());
        assertEquals("UpdatedFirstName", existingEmployee.getFirstName());
    }

    @Test
    void testUpdateEmployee_NotFound() {
        // Arrange
        EmployeeUpdateDTO updateDTO = new EmployeeUpdateDTO();
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        UpdateEmployeeResult result = employeeService.updateEmployee(1L, updateDTO);

        // Assert
        assertFalse(result.isSuccess());
        assertEquals("Error, Employee not found", result.getMessage());
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void testUpdateEmployee_InvalidCityId() {
        // Arrange
        Long employeeId = 1L;
        EmployeeUpdateDTO updateDTO = new EmployeeUpdateDTO();
        updateDTO.setCityId(2L);

        Employee existingEmployee = new Employee();
        existingEmployee.setEmpId(employeeId);

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(existingEmployee));
        when(cityRepository.findById(2L)).thenReturn(Optional.empty());

        // Act
        UpdateEmployeeResult result = employeeService.updateEmployee(employeeId, updateDTO);

        // Assert
        assertFalse(result.isSuccess());
        assertEquals("Error, City not found", result.getMessage());
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void testUpdateEmployee_NoData() {
        // Arrange
        Long employeeId = 1L;
        EmployeeUpdateDTO updateDTO = new EmployeeUpdateDTO();

        Employee existingEmployee = new Employee();
        existingEmployee.setEmpId(employeeId);
        existingEmployee.setFirstName("OriginalFirstName");
        existingEmployee.setDepartment(Department.Roads_and_Transportation_Department);


        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(existingEmployee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(existingEmployee);

        // Act
        UpdateEmployeeResult result = employeeService.updateEmployee(employeeId, updateDTO);

        // Assert
        assertTrue(result.isSuccess());
        assertEquals("Employee updated successfully", result.getMessage());
        assertEquals("OriginalFirstName", existingEmployee.getFirstName());
    }

    @Test
    void testUpdateEmployee_PartialUpdate() {
        // Arrange
        Long employeeId = 1L;
        EmployeeUpdateDTO updateDTO = new EmployeeUpdateDTO();
        updateDTO.setFirstName("UpdatedFirstName");

        Employee existingEmployee = new Employee();
        existingEmployee.setEmpId(employeeId);
        existingEmployee.setFirstName("OriginalFirstName");
        existingEmployee.setLastName("OriginalLastName");
        existingEmployee.setDepartment(Department.Roads_and_Transportation_Department);


        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(existingEmployee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(existingEmployee);

        // Act
        UpdateEmployeeResult result = employeeService.updateEmployee(employeeId, updateDTO);

        // Assert
        assertTrue(result.isSuccess());
        assertEquals("Employee updated successfully", result.getMessage());
        assertEquals("UpdatedFirstName", existingEmployee.getFirstName());
        assertEquals("OriginalLastName", existingEmployee.getLastName());
        assertEquals(Department.Roads_and_Transportation_Department, existingEmployee.getDepartment());
    }


    @Test
    void testDeleteEmployee_Success() {
        // Arrange
        Long employeeId = 1L;
        Employee employee = new Employee();
        employee.setEmpId(employeeId);
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        doNothing().when(employeeRepository).deleteById(employeeId);

        // Act
        DeleteEmployeeResult result = employeeService.deleteEmployee(employeeId);

        // Assert
        assertTrue(result.isSuccess());
        assertEquals("Employee deleted successfully", result.getMessage());
        verify(employeeRepository, times(1)).deleteById(employeeId);
    }

    @Test
    void testDeleteEmployee_NotFound() {
        // Arrange
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        DeleteEmployeeResult result = employeeService.deleteEmployee(1L);

        // Assert
        assertFalse(result.isSuccess());
        assertEquals("Error, Employee not found", result.getMessage());
        verify(employeeRepository, never()).deleteById(any());
    }

    @Test
    void testDeleteEmployee_Exception() {
        // Arrange
        Long employeeId = 1L;
        Employee employee = new Employee();
        employee.setEmpId(employeeId);
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        doThrow(new RuntimeException("Database error")).when(employeeRepository).deleteById(employeeId);

        // Act
        DeleteEmployeeResult result = employeeService.deleteEmployee(employeeId);

        // Assert
        assertFalse(result.isSuccess());
        assertTrue(result.getMessage().contains("Error deleting employee"));
    }
}