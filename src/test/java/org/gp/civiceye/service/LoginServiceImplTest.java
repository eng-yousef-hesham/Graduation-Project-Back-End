package org.gp.civiceye.service;

import org.gp.civiceye.mapper.UserDTO;
import org.gp.civiceye.repository.*;
import org.gp.civiceye.repository.entity.*;
import org.gp.civiceye.service.impl.LoginServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class LoginServiceImplTest {

    @Mock
    private CityAdminRepository cityAdminRepository;

    @Mock
    private GovernorateAdminRepository governorateAdminRepository;

    @Mock
    private MasterAdminRepository masterAdminRepository;

    @Mock
    private CitizenRepository citizenRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private LoginServiceImpl loginService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void userInfo_MasterAdmin() {
        String email = "master@example.com";
        List<String> Level = List.of("ROLE_MASTERADMIN");
        MasterAdmin masterAdmin = new MasterAdmin();
        masterAdmin.setAdminId(1L);
        masterAdmin.setNationalId("12345");
        masterAdmin.setFirstName("Master");
        masterAdmin.setLastName("Admin");
        masterAdmin.setEmail(email);

        when(masterAdminRepository.findByEmail(email)).thenReturn(Optional.of(masterAdmin));

        UserDTO userDTO = loginService.userInfo(email, Level);

        assertNotNull(userDTO);
        assertEquals(masterAdmin.getAdminId(), userDTO.getId());
        assertEquals(masterAdmin.getNationalId(), userDTO.getNationalId());
        assertEquals(masterAdmin.getFirstName() + " " + masterAdmin.getLastName(), userDTO.getFullName());
        assertEquals(masterAdmin.getFirstName(), userDTO.getFirstName());
        assertEquals(masterAdmin.getLastName(), userDTO.getLastName());
        assertEquals(masterAdmin.getEmail(), userDTO.getEmail());
        assertNull(userDTO.getGovernorateName());
        assertNull(userDTO.getCityName());
        assertNull(userDTO.getDepartment());
        assertEquals(Level, userDTO.getLevel());
    }

    @Test
    void userInfo_GovernorateAdmin() {
        String email = "governorate@example.com";
        List<String> Level = List.of("ROLE_GOVERNORATEADMIN");
        GovernorateAdmin governorateAdmin = new GovernorateAdmin();
        governorateAdmin.setAdminId(2L);
        governorateAdmin.setNationalId("67890");
        governorateAdmin.setFirstName("Governorate");
        governorateAdmin.setLastName("Admin");
        governorateAdmin.setEmail(email);
        Governorate governorate = new Governorate();
        governorate.setName("Governorate Name");
        governorateAdmin.setGovernorate(governorate);

        when(governorateAdminRepository.findByEmail(email)).thenReturn(Optional.of(governorateAdmin));

        UserDTO userDTO = loginService.userInfo(email, Level);

        assertNotNull(userDTO);
        assertEquals(governorateAdmin.getAdminId(), userDTO.getId());
        assertEquals(governorateAdmin.getNationalId(), userDTO.getNationalId());
        assertEquals(governorateAdmin.getFirstName() + " " + governorateAdmin.getLastName(), userDTO.getFullName());
        assertEquals(governorateAdmin.getFirstName(), userDTO.getFirstName());
        assertEquals(governorateAdmin.getLastName(), userDTO.getLastName());
        assertEquals(governorateAdmin.getEmail(), userDTO.getEmail());
        assertEquals(governorate.getName(), userDTO.getGovernorateName());
        assertNull(userDTO.getCityName());
        assertNull(userDTO.getDepartment());
        assertEquals(Level, userDTO.getLevel());
    }

    @Test
    void userInfo_CityAdmin() {
        String email = "city@example.com";
        List<String> Level = List.of("ROLE_CITYADMIN");
        CityAdmin cityAdmin = new CityAdmin();
        cityAdmin.setAdminId(3L);
        cityAdmin.setNationalId("13579");
        cityAdmin.setFirstName("City");
        cityAdmin.setLastName("Admin");
        cityAdmin.setEmail(email);
        Governorate governorate = new Governorate();
        governorate.setName("Governorate Name");
        City city = new City();
        city.setName("City Name");
        city.setGovernorate(governorate);
        cityAdmin.setCity(city);

        when(cityAdminRepository.findByEmail(email)).thenReturn(Optional.of(cityAdmin));

        UserDTO userDTO = loginService.userInfo(email, Level);

        assertNotNull(userDTO);
        assertEquals(cityAdmin.getAdminId(), userDTO.getId());
        assertEquals(cityAdmin.getNationalId(), userDTO.getNationalId());
        assertEquals(cityAdmin.getFirstName() + " " + cityAdmin.getLastName(), userDTO.getFullName());
        assertEquals(cityAdmin.getFirstName(), userDTO.getFirstName());
        assertEquals(cityAdmin.getLastName(), userDTO.getLastName());
        assertEquals(cityAdmin.getEmail(), userDTO.getEmail());
        assertEquals(governorate.getName(), userDTO.getGovernorateName());
        assertEquals(city.getName(), userDTO.getCityName());
        assertNull(userDTO.getDepartment());
        assertEquals(Level, userDTO.getLevel());
    }

    @Test
    void userInfo_Employee() {
        String email = "employee@example.com";
        List<String> Level = List.of("ROLE_EMPLOYEE");
        Employee employee = new Employee();
        employee.setEmpId(4L);
        employee.setNationalId("24680");
        employee.setFirstName("Employee");
        employee.setLastName("User");
        employee.setEmail(email);
        Governorate governorate = new Governorate();
        governorate.setName("Governorate Name");
        City city = new City();
        city.setName("City Name");
        city.setGovernorate(governorate);
        employee.setCity(city);
        Department department = Department.Roads_and_Transportation_Department;

        employee.setDepartment(department);

        when(employeeRepository.findByEmail(email)).thenReturn(Optional.of(employee));

        UserDTO userDTO = loginService.userInfo(email, Level);

        assertNotNull(userDTO);
        assertEquals(employee.getEmpId(), userDTO.getId());
        assertEquals(employee.getNationalId(), userDTO.getNationalId());
        assertEquals(employee.getFirstName() + " " + employee.getLastName(), userDTO.getFullName());
        assertEquals(employee.getFirstName(), userDTO.getFirstName());
        assertEquals(employee.getLastName(), userDTO.getLastName());
        assertEquals(employee.getEmail(), userDTO.getEmail());
        assertEquals(governorate.getName(), userDTO.getGovernorateName());
        assertEquals(city.getName(), userDTO.getCityName());
        assertEquals(department.name(), userDTO.getDepartment());
        assertEquals(Level, userDTO.getLevel());
    }

    @Test
    void userInfo_Citizen() {
        String email = "citizen@example.com";
        List<String> Level = List.of("ROLE_CITIZEN");
        Citizen citizen = new Citizen();
        citizen.setCitizenId(5L);
        citizen.setNationalId("98765");
        citizen.setFirstName("Citizen");
        citizen.setLastName("User");
        citizen.setEmail(email);

        when(citizenRepository.findByEmail(email)).thenReturn(Optional.of(citizen));

        UserDTO userDTO = loginService.userInfo(email, Level);

        assertNotNull(userDTO);
        assertEquals(citizen.getCitizenId(), userDTO.getId());
        assertEquals(citizen.getNationalId(), userDTO.getNationalId());
        assertEquals(citizen.getFirstName() + " " + citizen.getLastName(), userDTO.getFullName());
        assertEquals(citizen.getFirstName(), userDTO.getFirstName());
        assertEquals(citizen.getLastName(), userDTO.getLastName());
        assertEquals(citizen.getEmail(), userDTO.getEmail());
        assertNull(userDTO.getGovernorateName());
        assertNull(userDTO.getCityName());
        assertNull(userDTO.getDepartment());
        assertEquals(Level, userDTO.getLevel());
    }

    @Test
    void userInfo_MasterAdmin_NotFound() {
        String email = "notfound@example.com";
        List<String> Level = List.of("ROLE_MASTERADMIN");

        when(masterAdminRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> loginService.userInfo(email, Level));
    }

    @Test
    void userInfo_GovernorateAdmin_NotFound() {
        String email = "notfound@example.com";
        List<String> Level = List.of("ROLE_GOVERNORATEADMIN");

        when(governorateAdminRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> loginService.userInfo(email, Level));
    }

    @Test
    void userInfo_CityAdmin_NotFound() {
        String email = "notfound@example.com";
        List<String> Level = List.of("ROLE_CITYADMIN");

        when(cityAdminRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> loginService.userInfo(email, Level));
    }

    @Test
    void userInfo_Employee_NotFound() {
        String email = "notfound@example.com";
        List<String> Level = List.of("ROLE_EMPLOYEE");

        when(employeeRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> loginService.userInfo(email, Level));
    }

    @Test
    void userInfo_Citizen_NotFound() {
        String email = "notfound@example.com";
        List<String> Level = List.of("ROLE_CITIZEN");

        when(citizenRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> loginService.userInfo(email, Level));
    }
}