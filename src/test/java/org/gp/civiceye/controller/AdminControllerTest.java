package org.gp.civiceye.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.gp.civiceye.mapper.CreateAdminDTO;
import org.gp.civiceye.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AdminControllerTest {

    @Mock
    private AdminService adminService;

    @InjectMocks
    private AdminController adminController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testAddCityAdmin() throws Exception {
        // Arrange
        CreateAdminDTO adminDTO = new CreateAdminDTO();
        adminDTO.setEmail("cityadmin@example.com");
        adminDTO.setFirstName("City Admin");
        adminDTO.setHashPassword("password");
        adminDTO.setType(1998); // City admin type
        adminDTO.setCityId(1L);

        when(adminService.addAdmin(any(CreateAdminDTO.class)))
                .thenReturn(new ResponseEntity<>("City admin created successfully", HttpStatus.OK));

        // Act & Assert
        mockMvc.perform(post("/api/V1/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(adminDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("City admin created successfully"));
    }

    @Test
    void testAddGovernorateAdmin() throws Exception {
        // Arrange
        CreateAdminDTO adminDTO = new CreateAdminDTO();
        adminDTO.setEmail("govadmin@example.com");
        adminDTO.setFirstName("Governorate Admin");
        adminDTO.setHashPassword("password");
        adminDTO.setType(1999); // Governorate admin type
        adminDTO.setGovernorateId(1L);

        when(adminService.addAdmin(any(CreateAdminDTO.class)))
                .thenReturn(new ResponseEntity<>("Governorate admin created successfully", HttpStatus.OK));

        // Act & Assert
        mockMvc.perform(post("/api/V1/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(adminDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("Governorate admin created successfully"));
    }

    @Test
    void testAddMasterAdmin() throws Exception {
        // Arrange
        CreateAdminDTO adminDTO = new CreateAdminDTO();
        adminDTO.setEmail("masteradmin@example.com");
        adminDTO.setFirstName("Master Admin");
        adminDTO.setHashPassword("password");
        adminDTO.setType(2000); // Master admin type

        when(adminService.addAdmin(any(CreateAdminDTO.class)))
                .thenReturn(new ResponseEntity<>("Master admin created successfully", HttpStatus.OK));

        // Act & Assert
        mockMvc.perform(post("/api/V1/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(adminDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("Master admin created successfully"));
    }

    @Test
    void testAddInvalidAdminType() throws Exception {
        // Arrange
        CreateAdminDTO adminDTO = new CreateAdminDTO();
        adminDTO.setEmail("invalid@example.com");
        adminDTO.setFirstName("Invalid Admin");
        adminDTO.setHashPassword("password");
        adminDTO.setType(1234); // Invalid admin type

        when(adminService.addAdmin(any(CreateAdminDTO.class)))
                .thenReturn(new ResponseEntity<>("Specified admin type not found", HttpStatus.BAD_REQUEST));

        // Act & Assert
        mockMvc.perform(post("/api/V1/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(adminDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Specified admin type not found"));
    }

    @Test
    void testAddCityAdminWithInvalidCity() throws Exception {
        // Arrange
        CreateAdminDTO adminDTO = new CreateAdminDTO();
        adminDTO.setEmail("cityadmin@example.com");
        adminDTO.setFirstName("City Admin");
        adminDTO.setHashPassword("password");
        adminDTO.setType(1998); // City admin type
        adminDTO.setCityId(999L); // Non-existent city ID

        when(adminService.addAdmin(any(CreateAdminDTO.class)))
                .thenReturn(new ResponseEntity<>("Error, City not found", HttpStatus.BAD_REQUEST));

        // Act & Assert
        mockMvc.perform(post("/api/V1/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(adminDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error, City not found"));
    }

    @Test
    void testAddGovernorateAdminWithInvalidGovernorate() throws Exception {
        // Arrange
        CreateAdminDTO adminDTO = new CreateAdminDTO();
        adminDTO.setEmail("govadmin@example.com");
        adminDTO.setFirstName("Governorate Admin");
        adminDTO.setHashPassword("password");
        adminDTO.setType(1999); // Governorate admin type
        adminDTO.setGovernorateId(999L); // Non-existent governorate ID

        when(adminService.addAdmin(any(CreateAdminDTO.class)))
                .thenReturn(new ResponseEntity<>("Error, Governorate not found", HttpStatus.BAD_REQUEST));

        // Act & Assert
        mockMvc.perform(post("/api/V1/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(adminDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error, Governorate not found"));
    }
}