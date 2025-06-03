//package org.gp.civiceye.service;
//
//import org.gp.civiceye.mapper.CreateAdminDTO;
//import org.gp.civiceye.mapper.UpdateAdminDTO;
//import org.gp.civiceye.repository.*;
//import org.gp.civiceye.repository.entity.*;
//import org.gp.civiceye.service.impl.AdminServiceImpl;
//import org.gp.civiceye.service.impl.admin.AddAdminResult;
//import org.gp.civiceye.service.impl.admin.AdminType;
//import org.gp.civiceye.service.impl.admin.DeleteAdminResult;
//import org.gp.civiceye.service.impl.admin.UpdateAdminResult;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.*;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//import java.util.Optional;
//
//class AdminServiceTests {
//
//    @Mock
//    private CityRepository cityRepository;
//
//    @Mock
//    private GovernorateRepository governorateRepository;
//
//    @Mock
//    private CityAdminRepository cityAdminRepository;
//
//    @Mock
//    private GovernorateAdminRepository governorateAdminRepository;
//
//    @Mock
//    private MasterAdminRepository masterAdminRepository;
//
//    @Mock
//    private PasswordEncoder passwordEncoder;
//
//    private AdminServiceImpl adminService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        adminService = new AdminServiceImpl(cityRepository, cityAdminRepository, governorateRepository,
//                governorateAdminRepository, masterAdminRepository, passwordEncoder);
//    }
//
//    @Test
//    void testAddCityAdmin_Success() {
//        CreateAdminDTO createAdminDTO = new CreateAdminDTO();
//        createAdminDTO.setType(1998);
//        createAdminDTO.setCityId(1L);
//        createAdminDTO.setNationalId("12345");
//        createAdminDTO.setFirstName("John");
//        createAdminDTO.setLastName("Doe");
//        createAdminDTO.setEmail("john.doe@example.com");
//        createAdminDTO.setHashPassword("password");
//
//        City city = new City();
//        city.setCityId(1L);
//        when(cityRepository.findById(1L)).thenReturn(Optional.of(city));
//        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
//
//        AddAdminResult result = adminService.addAdmin(createAdminDTO);
//
//        assertTrue(result.isSuccess());
//        assertEquals("City admin created successfully", result.getMessage());
//        assertEquals(AdminType.CITY_ADMIN, result.getAdminType());
//        verify(cityAdminRepository).save(any(CityAdmin.class));
//    }
//
//    @Test
//    void testAddGovernorateAdmin_Success() {
//        CreateAdminDTO createAdminDTO = new CreateAdminDTO();
//        createAdminDTO.setType(1999);
//        createAdminDTO.setGovernorateId(1L);
//        createAdminDTO.setNationalId("12345");
//        createAdminDTO.setFirstName("Jane");
//        createAdminDTO.setLastName("Doe");
//        createAdminDTO.setEmail("jane.doe@example.com");
//        createAdminDTO.setHashPassword("password");
//
//        Governorate governorate = new Governorate();
//        governorate.setGovernorateId(1L);
//        when(governorateRepository.findById(1L)).thenReturn(Optional.of(governorate));
//        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
//
//        AddAdminResult result = adminService.addAdmin(createAdminDTO);
//
//        assertTrue(result.isSuccess());
//        assertEquals("Governorate admin created successfully", result.getMessage());
//        assertEquals(AdminType.GOVERNORATE_ADMIN, result.getAdminType());
//        verify(governorateAdminRepository).save(any(GovernorateAdmin.class));
//    }
//
//    @Test
//    void testAddMasterAdmin_Success() {
//        CreateAdminDTO createAdminDTO = new CreateAdminDTO();
//        createAdminDTO.setType(2000);
//        createAdminDTO.setNationalId("67890");
//        createAdminDTO.setFirstName("Alice");
//        createAdminDTO.setLastName("Smith");
//        createAdminDTO.setEmail("alice.smith@example.com");
//        createAdminDTO.setHashPassword("password");
//
//        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
//
//        AddAdminResult result = adminService.addAdmin(createAdminDTO);
//
//        assertTrue(result.isSuccess());
//        assertEquals("Master admin created successfully", result.getMessage());
//        assertEquals(AdminType.MASTER_ADMIN, result.getAdminType());
//        verify(masterAdminRepository).save(any(MasterAdmin.class));
//    }
//
//    @Test
//    void testAddCityAdmin_CityNotFound() {
//        CreateAdminDTO createAdminDTO = new CreateAdminDTO();
//        createAdminDTO.setType(1998);
//        createAdminDTO.setCityId(1L);
//        createAdminDTO.setNationalId("12345");
//        createAdminDTO.setFirstName("John");
//        createAdminDTO.setLastName("Doe");
//        createAdminDTO.setEmail("john.doe@example.com");
//        createAdminDTO.setHashPassword("password");
//
//        when(cityRepository.findById(1L)).thenReturn(Optional.empty());
//
//        AddAdminResult result = adminService.addAdmin(createAdminDTO);
//
//        assertFalse(result.isSuccess());
//        assertEquals("Error, City not found", result.getMessage());
//        assertNull(result.getAdminType());
//    }
//
//    @Test
//    void testAddGovernorateAdmin_GovernorateNotFound() {
//        CreateAdminDTO createAdminDTO = new CreateAdminDTO();
//        createAdminDTO.setType(1999);
//        createAdminDTO.setGovernorateId(1L);
//        createAdminDTO.setNationalId("12345");
//        createAdminDTO.setFirstName("Jane");
//        createAdminDTO.setLastName("Doe");
//        createAdminDTO.setEmail("jane.doe@example.com");
//        createAdminDTO.setHashPassword("password");
//
//        when(governorateRepository.findById(1L)).thenReturn(Optional.empty());
//
//        AddAdminResult result = adminService.addAdmin(createAdminDTO);
//
//        assertFalse(result.isSuccess());
//        assertEquals("Error, Governorate not found", result.getMessage());
//        assertNull(result.getAdminType());
//    }
//
//    @Test
//    void testAddAdmin_InvalidAdminType() {
//        CreateAdminDTO createAdminDTO = new CreateAdminDTO();
//        createAdminDTO.setType(9999); // Invalid type
//        createAdminDTO.setNationalId("12345");
//        createAdminDTO.setFirstName("Bob");
//        createAdminDTO.setLastName("Jones");
//        createAdminDTO.setEmail("bob.jones@example.com");
//        createAdminDTO.setHashPassword("password");
//
//        AddAdminResult result = adminService.addAdmin(createAdminDTO);
//
//        assertFalse(result.isSuccess());
//        assertEquals("Specified admin type not found", result.getMessage());
//        assertNull(result.getAdminType());
//    }
//
//    @Test
//    void testDeleteCityAdmin_Success() {
//        Long adminId = 1L;
//        int adminType = 1998;
//
//        CityAdmin cityAdmin = new CityAdmin();
//        cityAdmin.setAdminId(adminId);
//        when(cityAdminRepository.findById(adminId)).thenReturn(Optional.of(cityAdmin));
//
//        DeleteAdminResult result = adminService.deleteAdmin(adminId, adminType);
//
//        assertTrue(result.isSuccess());
//        assertEquals("City admin deleted successfully", result.getMessage());
//        assertEquals(AdminType.CITY_ADMIN, result.getAdminType());
//        verify(cityAdminRepository).deleteById(adminId);
//    }
//
//    @Test
//    void testDeleteGovernorateAdmin_Success() {
//        Long adminId = 1L;
//        int adminType = 1999;
//
//        GovernorateAdmin governorateAdmin = new GovernorateAdmin();
//        governorateAdmin.setAdminId(adminId);
//        when(governorateAdminRepository.findById(adminId)).thenReturn(Optional.of(governorateAdmin));
//
//        DeleteAdminResult result = adminService.deleteAdmin(adminId, adminType);
//
//        assertTrue(result.isSuccess());
//        assertEquals("Governorate admin deleted successfully", result.getMessage());
//        assertEquals(AdminType.GOVERNORATE_ADMIN, result.getAdminType());
//        verify(governorateAdminRepository).deleteById(adminId);
//    }
//
//    @Test
//    void testDeleteAdmin_NotFound() {
//        Long adminId = 99L;
//        int adminType = 1998;
//
//        when(cityAdminRepository.findById(adminId)).thenReturn(Optional.empty());
//
//        DeleteAdminResult result = adminService.deleteAdmin(adminId, adminType);
//
//        assertFalse(result.isSuccess());
//        assertEquals("Error, City admin not found", result.getMessage());
//        assertEquals(AdminType.CITY_ADMIN, result.getAdminType());
//        verify(cityAdminRepository, never()).deleteById(adminId);
//    }
//
//    @Test
//    void testDeleteAdmin_InvalidType() {
//        Long adminId = 1L;
//        int adminType = 9999; // Invalid type
//
//        DeleteAdminResult result = adminService.deleteAdmin(adminId, adminType);
//
//        assertFalse(result.isSuccess());
//        assertEquals("Specified admin type not found", result.getMessage());
//        assertNull(result.getAdminType());
//    }
//
//    @Test
//    void testUpdateCityAdmin_Success() {
//        UpdateAdminDTO updateDTO = new UpdateAdminDTO();
//        updateDTO.setAdminId(1L);
//        updateDTO.setType(1998);
//        updateDTO.setFirstName("UpdatedFirstName");
//        updateDTO.setLastName("UpdatedLastName");
//        updateDTO.setCityId(2L);
//
//        CityAdmin existingAdmin = new CityAdmin();
//        existingAdmin.setAdminId(1L);
//
//        City city = new City();
//        city.setCityId(2L);
//
//        when(cityAdminRepository.findById(1L)).thenReturn(Optional.of(existingAdmin));
//        when(cityRepository.findById(2L)).thenReturn(Optional.of(city));
//
//        UpdateAdminResult result = adminService.updateAdmin(updateDTO);
//
//        assertTrue(result.isSuccess());
//        assertEquals("City admin updated successfully", result.getMessage());
//        assertEquals(AdminType.CITY_ADMIN, result.getAdminType());
//        verify(cityAdminRepository).save(existingAdmin);
//    }
//}