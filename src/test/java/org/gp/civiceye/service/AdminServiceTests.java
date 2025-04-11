package org.gp.civiceye.service;

import org.gp.civiceye.mapper.CreateAdminDTO;
import org.gp.civiceye.repository.*;
import org.gp.civiceye.repository.entity.*;
import org.gp.civiceye.service.impl.AdminServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

class AdminServiceTests {

    @Mock
    private CityRepository cityRepository;

    @Mock
    private GovernorateRepository governorateRepository;

    @Mock
    private CityAdminRepository cityAdminRepository;

    @Mock
    private GovernorateAdminRepository governorateAdminRepository;

    @Mock
    private MasterAdminRepository masterAdminRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private AdminServiceImpl adminService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        adminService = new AdminServiceImpl(cityRepository, cityAdminRepository, governorateRepository,
                governorateAdminRepository, masterAdminRepository, passwordEncoder);
    }

    @Test
    void testAddCityAdmin_Success() {
        CreateAdminDTO createAdminDTO = new CreateAdminDTO();
        createAdminDTO.setType(1998);
        createAdminDTO.setCityId(1L);
        createAdminDTO.setNationalId("12345");
        createAdminDTO.setFirstName("John");
        createAdminDTO.setLastName("Doe");
        createAdminDTO.setEmail("john.doe@example.com");
        createAdminDTO.setHashPassword("password");

        City city = new City();
        city.setCityId(1L);
        when(cityRepository.findById(1L)).thenReturn(Optional.of(city));
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        ResponseEntity<String> response = adminService.addAdmin(createAdminDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("City admin created successfully", response.getBody());
        verify(cityAdminRepository).save(any(CityAdmin.class));
    }

    @Test
    void testAddGovernorateAdmin_Success() {
        CreateAdminDTO createAdminDTO = new CreateAdminDTO();
        createAdminDTO.setType(1999);
        createAdminDTO.setGovernorateId(1L);
        createAdminDTO.setNationalId("12345");
        createAdminDTO.setFirstName("Jane");
        createAdminDTO.setLastName("Doe");
        createAdminDTO.setEmail("jane.doe@example.com");
        createAdminDTO.setHashPassword("password");

        Governorate governorate = new Governorate();
        governorate.setGovernorateId(1L);
        when(governorateRepository.findById(1L)).thenReturn(Optional.of(governorate));
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        ResponseEntity<String> response = adminService.addAdmin(createAdminDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("governorate admin created successfully", response.getBody());
        verify(governorateAdminRepository).save(any(GovernorateAdmin.class));
    }

    @Test
    void testAddMasterAdmin_Success() {
        CreateAdminDTO createAdminDTO = new CreateAdminDTO();
        createAdminDTO.setType(2000);
        createAdminDTO.setNationalId("67890");
        createAdminDTO.setFirstName("Alice");
        createAdminDTO.setLastName("Smith");
        createAdminDTO.setEmail("alice.smith@example.com");
        createAdminDTO.setHashPassword("password");

        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        ResponseEntity<String> response = adminService.addAdmin(createAdminDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("master admin created successfully", response.getBody());
        verify(masterAdminRepository).save(any(MasterAdmin.class));
    }

    @Test
    void testAddCityAdmin_CityNotFound() {
        CreateAdminDTO createAdminDTO = new CreateAdminDTO();
        createAdminDTO.setType(1998);
        createAdminDTO.setCityId(1L);
        createAdminDTO.setNationalId("12345");
        createAdminDTO.setFirstName("John");
        createAdminDTO.setLastName("Doe");
        createAdminDTO.setEmail("john.doe@example.com");
        createAdminDTO.setHashPassword("password");

        when(cityRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<String> response = adminService.addAdmin(createAdminDTO);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals("Error, City not found", response.getBody());
    }

    @Test
    void testAddGovernorateAdmin_GovernorateNotFound() {
        CreateAdminDTO createAdminDTO = new CreateAdminDTO();
        createAdminDTO.setType(1999);
        createAdminDTO.setGovernorateId(1L);
        createAdminDTO.setNationalId("12345");
        createAdminDTO.setFirstName("Jane");
        createAdminDTO.setLastName("Doe");
        createAdminDTO.setEmail("jane.doe@example.com");
        createAdminDTO.setHashPassword("password");

        when(governorateRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<String> response = adminService.addAdmin(createAdminDTO);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals("Error, Governorate not found", response.getBody());
    }

    @Test
    void testAddAdmin_InvalidAdminType() {
        CreateAdminDTO createAdminDTO = new CreateAdminDTO();
        createAdminDTO.setType(9999); // Invalid type
        createAdminDTO.setNationalId("12345");
        createAdminDTO.setFirstName("Bob");
        createAdminDTO.setLastName("Jones");
        createAdminDTO.setEmail("bob.jones@example.com");
        createAdminDTO.setHashPassword("password");

        ResponseEntity<String> response = adminService.addAdmin(createAdminDTO);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals("Specified admin type not found", response.getBody());
    }
}