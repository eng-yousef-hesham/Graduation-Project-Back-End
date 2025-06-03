//package org.gp.civiceye.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.gp.civiceye.mapper.AdminDeleteDTO;
//import org.gp.civiceye.mapper.CreateAdminDTO;
//import org.gp.civiceye.mapper.UpdateAdminDTO;
//import org.gp.civiceye.service.AdminService;
//import org.gp.civiceye.service.impl.admin.AdminType;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ExtendWith(MockitoExtension.class)
//public class AdminControllerTest {
//
//    @Mock
//    private AdminService adminService;
//
//    @InjectMocks
//    private AdminController adminController;
//
//    private MockMvc mockMvc;
//    private ObjectMapper objectMapper;
//
//    @BeforeEach
//    void setUp() {
//        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
//        objectMapper = new ObjectMapper();
//    }
//
//    @Test
//    void testAddCityAdmin() throws Exception {
//        // Arrange
//        CreateAdminDTO adminDTO = new CreateAdminDTO();
//        adminDTO.setEmail("cityadmin@example.com");
//        adminDTO.setFirstName("City Admin");
//        adminDTO.setHashPassword("password");
//        adminDTO.setType(1998); // City admin type
//        adminDTO.setCityId(1L);
//
//        when(adminService.addAdmin(any(CreateAdminDTO.class)))
//                .thenReturn(new AddAdminResult(true, "City admin created successfully", AdminType.CITY_ADMIN));
//
//        // Act & Assert
//        mockMvc.perform(post("/api/V1/admin")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(adminDTO)))
//                .andExpect(status().isOk())
//                .andExpect(content().string("City admin created successfully"));
//    }
//
//    @Test
//    void testAddGovernorateAdmin() throws Exception {
//        // Arrange
//        CreateAdminDTO adminDTO = new CreateAdminDTO();
//        adminDTO.setEmail("govadmin@example.com");
//        adminDTO.setFirstName("Governorate Admin");
//        adminDTO.setHashPassword("password");
//        adminDTO.setType(1999); // Governorate admin type
//        adminDTO.setGovernorateId(1L);
//
//        when(adminService.addAdmin(any(CreateAdminDTO.class)))
//                .thenReturn(new AddAdminResult(true, "Governorate admin created successfully", AdminType.GOVERNORATE_ADMIN));
//
//        // Act & Assert
//        mockMvc.perform(post("/api/V1/admin")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(adminDTO)))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Governorate admin created successfully"));
//    }
//
//    @Test
//    void testAddMasterAdmin() throws Exception {
//        // Arrange
//        CreateAdminDTO adminDTO = new CreateAdminDTO();
//        adminDTO.setEmail("masteradmin@example.com");
//        adminDTO.setFirstName("Master Admin");
//        adminDTO.setHashPassword("password");
//        adminDTO.setType(2000); // Master admin type
//
//        when(adminService.addAdmin(any(CreateAdminDTO.class)))
//                .thenReturn(new AddAdminResult(true, "Master admin created successfully", AdminType.MASTER_ADMIN));
//
//        // Act & Assert
//        mockMvc.perform(post("/api/V1/admin")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(adminDTO)))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Master admin created successfully"));
//    }
//
//    @Test
//    void testAddInvalidAdminType() throws Exception {
//        // Arrange
//        CreateAdminDTO adminDTO = new CreateAdminDTO();
//        adminDTO.setEmail("invalid@example.com");
//        adminDTO.setFirstName("Invalid Admin");
//        adminDTO.setHashPassword("password");
//        adminDTO.setType(1234); // Invalid admin type
//
//        when(adminService.addAdmin(any(CreateAdminDTO.class)))
//                .thenReturn(new AddAdminResult(false, "Specified admin type not found"));
//
//        // Act & Assert
//        mockMvc.perform(post("/api/V1/admin")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(adminDTO)))
//                .andExpect(status().isUnprocessableEntity())
//                .andExpect(content().string("Specified admin type not found"));
//    }
//
//    @Test
//    void testAddCityAdminWithInvalidCity() throws Exception {
//        // Arrange
//        CreateAdminDTO adminDTO = new CreateAdminDTO();
//        adminDTO.setEmail("cityadmin@example.com");
//        adminDTO.setFirstName("City Admin");
//        adminDTO.setHashPassword("password");
//        adminDTO.setType(1998); // City admin type
//        adminDTO.setCityId(999L); // Non-existent city ID
//
//        when(adminService.addAdmin(any(CreateAdminDTO.class)))
//                .thenReturn(new AddAdminResult(false, "Error, City not found"));
//
//        // Act & Assert
//        mockMvc.perform(post("/api/V1/admin")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(adminDTO)))
//                .andExpect(status().isUnprocessableEntity())
//                .andExpect(content().string("Error, City not found"));
//    }
//
//    @Test
//    void testAddGovernorateAdminWithInvalidGovernorate() throws Exception {
//        // Arrange
//        CreateAdminDTO adminDTO = new CreateAdminDTO();
//        adminDTO.setEmail("govadmin@example.com");
//        adminDTO.setFirstName("Governorate Admin");
//        adminDTO.setHashPassword("password");
//        adminDTO.setType(1999); // Governorate admin type
//        adminDTO.setGovernorateId(999L); // Non-existent governorate ID
//
//        when(adminService.addAdmin(any(CreateAdminDTO.class)))
//                .thenReturn(new AddAdminResult(false, "Error, Governorate not found"));
//
//        // Act & Assert
//        mockMvc.perform(post("/api/V1/admin")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(adminDTO)))
//                .andExpect(status().isUnprocessableEntity())
//                .andExpect(content().string("Error, Governorate not found"));
//    }
//
//    @Test
//    void testUpdateAdmin() throws Exception {
//        // Arrange
//        UpdateAdminDTO adminDTO = new UpdateAdminDTO();
//        adminDTO.setAdminId(1L);
//        adminDTO.setFirstName("Updated Name");
//        adminDTO.setLastName("Updated Last");
//        adminDTO.setType(1998);
//        adminDTO.setCityId(2L);
//
//        when(adminService.updateAdmin(any(UpdateAdminDTO.class)))
//                .thenReturn(new UpdateAdminResult(true, "City admin updated successfully", AdminType.CITY_ADMIN));
//
//        // Act & Assert
//        mockMvc.perform(put("/api/V1/admin")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(adminDTO)))
//                .andExpect(status().isOk())
//                .andExpect(content().string("City admin updated successfully"));
//    }
//
//    @Test
//    void testUpdateAdminById() throws Exception {
//        // Arrange
//        UpdateAdminDTO adminDTO = new UpdateAdminDTO();
//        adminDTO.setFirstName("Updated Name");
//        adminDTO.setType(1998);
//        adminDTO.setCityId(2L);
//
//        // Creating a new UpdateAdminDTO with the path variable ID
//        UpdateAdminDTO expectedDTO = new UpdateAdminDTO();
//        expectedDTO.setAdminId(1L);
//        expectedDTO.setFirstName("Updated Name");
//        expectedDTO.setType(1998);
//        expectedDTO.setCityId(2L);
//
//        when(adminService.updateAdmin(any(UpdateAdminDTO.class)))
//                .thenReturn(new UpdateAdminResult(true, "City admin updated successfully", AdminType.CITY_ADMIN));
//
//        // Act & Assert
//        mockMvc.perform(put("/api/V1/admin/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(adminDTO)))
//                .andExpect(status().isOk())
//                .andExpect(content().string("City admin updated successfully"));
//    }
//
//    @Test
//    void testUpdateAdminNotFound() throws Exception {
//        // Arrange
//        UpdateAdminDTO adminDTO = new UpdateAdminDTO();
//        adminDTO.setAdminId(999L);
//        adminDTO.setFirstName("Updated Name");
//        adminDTO.setType(1998);
//
//        when(adminService.updateAdmin(any(UpdateAdminDTO.class)))
//                .thenReturn(new UpdateAdminResult(false, "Error, City admin not found", AdminType.CITY_ADMIN));
//
//        // Act & Assert
//        mockMvc.perform(put("/api/V1/admin")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(adminDTO)))
//                .andExpect(status().isNotFound())
//                .andExpect(content().string("Error, City admin not found"));
//    }
//
//    @Test
//    void testDeleteAdmin() throws Exception {
//        // Arrange
//        when(adminService.deleteAdmin(anyLong(), anyInt()))
//                .thenReturn(new DeleteAdminResult(true, "City admin deleted successfully", AdminType.CITY_ADMIN));
//
//        // Act & Assert
//        mockMvc.perform(delete("/api/V1/admin/1/type/1998"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("City admin deleted successfully"));
//    }
//
//    @Test
//    void testDeleteAdminNotFound() throws Exception {
//        // Arrange
//        when(adminService.deleteAdmin(anyLong(), anyInt()))
//                .thenReturn(new DeleteAdminResult(false, "Error, City admin not found", AdminType.CITY_ADMIN));
//
//        // Act & Assert
//        mockMvc.perform(delete("/api/V1/admin/999/type/1998"))
//                .andExpect(status().isNotFound())
//                .andExpect(content().string("Error, City admin not found"));
//    }
//
//    @Test
//    void testDeleteAdminWithBody() throws Exception {
//        // Arrange
//        AdminDeleteDTO deleteDTO = new AdminDeleteDTO();
//        deleteDTO.setAdminId(1L);
//        deleteDTO.setAdminType(1998);
//
//        when(adminService.deleteAdmin(anyLong(), anyInt()))
//                .thenReturn(new DeleteAdminResult(true, "City admin deleted successfully", AdminType.CITY_ADMIN));
//
//        // Act & Assert
//        mockMvc.perform(delete("/api/V1/admin")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(deleteDTO)))
//                .andExpect(status().isOk())
//                .andExpect(content().string("City admin deleted successfully"));
//    }
//
//    @Test
//    void testDeleteLastMasterAdmin() throws Exception {
//        // Arrange
//        when(adminService.deleteAdmin(anyLong(), anyInt()))
//                .thenReturn(new DeleteAdminResult(false, "Cannot delete the last master admin", AdminType.MASTER_ADMIN));
//
//        // Act & Assert
//        mockMvc.perform(delete("/api/V1/admin/1/type/2000"))
//                .andExpect(status().isForbidden())
//                .andExpect(content().string("Cannot delete the last master admin"));
//    }
//}