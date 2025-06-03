//package org.gp.civiceye.controller;
//
//import org.gp.civiceye.mapper.employee.EmployeeCreateDTO;
//import org.gp.civiceye.mapper.employee.EmployeeDTO;
//import org.gp.civiceye.mapper.employee.EmployeeUpdateDTO;
//import org.gp.civiceye.service.EmployeeService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Collections;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//public class EmployeeControllerTest {
//
//    @Mock
//    private EmployeeService employeeService;
//
//    @InjectMocks
//    private EmployeeController employeeController;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void testGetAllEmployees_Success() {
//        // Arrange
//        List<EmployeeDTO> employees = new ArrayList<>();
//        EmployeeDTO employee = new EmployeeDTO();
//        employees.add(employee);
//        when(employeeService.getAllEmployees()).thenReturn(employees);
//
//        // Act
//        ResponseEntity<List<EmployeeDTO>> response = employeeController.getAllEmployees();
//
//        // Assert
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//        assertEquals(1, response.getBody().size());
//        verify(employeeService, times(1)).getAllEmployees();
//    }
//
//    @Test
//    public void testGetAllEmployees_NotFound() {
//        // Arrange
//        when(employeeService.getAllEmployees()).thenReturn(Collections.emptyList());
//
//        // Act
//        ResponseEntity<List<EmployeeDTO>> response = employeeController.getAllEmployees();
//
//        // Assert
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//        verify(employeeService, times(1)).getAllEmployees();
//    }
//
//    @Test
//    public void testGetEmployeeById_Success() {
//        // Arrange
//        Long employeeId = 1L;
//        EmployeeDTO employee = new EmployeeDTO();
//        when(employeeService.getEmployeeById(employeeId)).thenReturn(employee);
//
//        // Act
//        ResponseEntity<EmployeeDTO> response = employeeController.getEmployeeById(employeeId);
//
//        // Assert
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//        assertEquals(employee, response.getBody());
//        verify(employeeService, times(1)).getEmployeeById(employeeId);
//    }
//
//    @Test
//    public void testGetEmployeeById_NotFound() {
//        // Arrange
//        Long employeeId = 1L;
//        when(employeeService.getEmployeeById(employeeId)).thenReturn(null);
//
//        // Act
//        ResponseEntity<EmployeeDTO> response = employeeController.getEmployeeById(employeeId);
//
//        // Assert
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//        assertNull(response.getBody());
//        verify(employeeService, times(1)).getEmployeeById(employeeId);
//    }
//
//    @Test
//    public void testCreateEmployee_Success() {
//        // Arrange
//        EmployeeCreateDTO employeeCreateDTO = new EmployeeCreateDTO();
//        AddEmployeeResult result = new AddEmployeeResult(true, "Employee created successfully");
//        when(employeeService.createEmployee(employeeCreateDTO)).thenReturn(result);
//
//        // Act
//        ResponseEntity<String> response = employeeController.createEmployee(employeeCreateDTO);
//
//        // Assert
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("Employee created successfully", response.getBody());
//        verify(employeeService, times(1)).createEmployee(employeeCreateDTO);
//    }
//
//    @Test
//    public void testCreateEmployee_DependencyNotFound() {
//        // Arrange
//        EmployeeCreateDTO employeeCreateDTO = new EmployeeCreateDTO();
//        AddEmployeeResult result = new AddEmployeeResult(false, "Department not found");
//        when(employeeService.createEmployee(employeeCreateDTO)).thenReturn(result);
//
//        // Act
//        ResponseEntity<String> response = employeeController.createEmployee(employeeCreateDTO);
//
//        // Assert
//        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
//        assertEquals("Department not found", response.getBody());
//        verify(employeeService, times(1)).createEmployee(employeeCreateDTO);
//    }
//
//    @Test
//    public void testCreateEmployee_Conflict() {
//        // Arrange
//        EmployeeCreateDTO employeeCreateDTO = new EmployeeCreateDTO();
//        AddEmployeeResult result = new AddEmployeeResult(false, "Employee already exists");
//        when(employeeService.createEmployee(employeeCreateDTO)).thenReturn(result);
//
//        // Act
//        ResponseEntity<String> response = employeeController.createEmployee(employeeCreateDTO);
//
//        // Assert
//        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
//        assertEquals("Employee already exists", response.getBody());
//        verify(employeeService, times(1)).createEmployee(employeeCreateDTO);
//    }
//
//    @Test
//    public void testUpdateEmployee_Success() {
//        // Arrange
//        Long employeeId = 1L;
//        EmployeeUpdateDTO employeeUpdateDTO = new EmployeeUpdateDTO();
//        UpdateEmployeeResult result = new UpdateEmployeeResult(true, "Employee updated successfully");
//        when(employeeService.updateEmployee(employeeId, employeeUpdateDTO)).thenReturn(result);
//
//        // Act
//        ResponseEntity<String> response = employeeController.updateEmployee(employeeId, employeeUpdateDTO);
//
//        // Assert
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("Employee updated successfully", response.getBody());
//        verify(employeeService, times(1)).updateEmployee(employeeId, employeeUpdateDTO);
//    }
//
//    @Test
//    public void testUpdateEmployee_DependencyNotFound() {
//        // Arrange
//        Long employeeId = 1L;
//        EmployeeUpdateDTO employeeUpdateDTO = new EmployeeUpdateDTO();
//        UpdateEmployeeResult result = new UpdateEmployeeResult(false, "Department not found");
//        when(employeeService.updateEmployee(employeeId, employeeUpdateDTO)).thenReturn(result);
//
//        // Act
//        ResponseEntity<String> response = employeeController.updateEmployee(employeeId, employeeUpdateDTO);
//
//        // Assert
//        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
//        assertEquals("Department not found", response.getBody());
//        verify(employeeService, times(1)).updateEmployee(employeeId, employeeUpdateDTO);
//    }
//
//    @Test
//    public void testUpdateEmployee_EntityNotFound() {
//        // Arrange
//        Long employeeId = 1L;
//        EmployeeUpdateDTO employeeUpdateDTO = new EmployeeUpdateDTO();
//        UpdateEmployeeResult result = new UpdateEmployeeResult(false, "Employee not found");
//        when(employeeService.updateEmployee(employeeId, employeeUpdateDTO)).thenReturn(result);
//
//        // Act
//        ResponseEntity<String> response = employeeController.updateEmployee(employeeId, employeeUpdateDTO);
//
//        // Assert
//        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
//        assertEquals("Employee not found", response.getBody());
//        verify(employeeService, times(1)).updateEmployee(employeeId, employeeUpdateDTO);
//    }
//
//    @Test
//    public void testUpdateEmployee_Conflict() {
//        // Arrange
//        Long employeeId = 1L;
//        EmployeeUpdateDTO employeeUpdateDTO = new EmployeeUpdateDTO();
//        UpdateEmployeeResult result = new UpdateEmployeeResult(false, "Conflicting data");
//        when(employeeService.updateEmployee(employeeId, employeeUpdateDTO)).thenReturn(result);
//
//        // Act
//        ResponseEntity<String> response = employeeController.updateEmployee(employeeId, employeeUpdateDTO);
//
//        // Assert
//        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
//        assertEquals("Conflicting data", response.getBody());
//        verify(employeeService, times(1)).updateEmployee(employeeId, employeeUpdateDTO);
//    }
//
//    @Test
//    public void testDeleteEmployee_Success() {
//        // Arrange
//        Long employeeId = 1L;
//        DeleteEmployeeResult result = new DeleteEmployeeResult(true, "Employee deleted successfully");
//        when(employeeService.deleteEmployee(employeeId)).thenReturn(result);
//
//        // Act
//        ResponseEntity<String> response = employeeController.deleteEmployee(employeeId);
//
//        // Assert
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("Employee deleted successfully", response.getBody());
//        verify(employeeService, times(1)).deleteEmployee(employeeId);
//    }
//
//    @Test
//    public void testDeleteEmployee_EntityNotFound() {
//        // Arrange
//        Long employeeId = 1L;
//        DeleteEmployeeResult result = new DeleteEmployeeResult(false, "Employee not found");
//        when(employeeService.deleteEmployee(employeeId)).thenReturn(result);
//
//        // Act
//        ResponseEntity<String> response = employeeController.deleteEmployee(employeeId);
//
//        // Assert
//        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
//        assertEquals("Employee not found", response.getBody());
//        verify(employeeService, times(1)).deleteEmployee(employeeId);
//    }
//
//    @Test
//    public void testDeleteEmployee_Conflict() {
//        // Arrange
//        Long employeeId = 1L;
//        DeleteEmployeeResult result = new DeleteEmployeeResult(false, "Cannot delete employee");
//        when(employeeService.deleteEmployee(employeeId)).thenReturn(result);
//
//        // Act
//        ResponseEntity<String> response = employeeController.deleteEmployee(employeeId);
//
//        // Assert
//        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
//        assertEquals("Cannot delete employee", response.getBody());
//        verify(employeeService, times(1)).deleteEmployee(employeeId);
//    }
//}