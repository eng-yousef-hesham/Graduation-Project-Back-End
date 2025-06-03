package org.gp.civiceye.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.gp.civiceye.mapper.AdminDeleteDTO;
import org.gp.civiceye.mapper.CreateAdminDTO;
import org.gp.civiceye.mapper.UpdateAdminDTO;
import org.gp.civiceye.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/V1")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/admin")
    @Operation(summary = "add new admin from any type", description = "insert new admin from any type \n" +
            "city admin = 1998\n" +
            "  governorate admin = 1999\n" +
            "  master admin = 2000")
    @ApiResponse(responseCode = "200", description = "when admin created successfully[City admin created successfully, Governorate admin created successfully, Master admin created successfully]\n" +
            "when admin type not added [Specified admin type not found]\n" +
            "when city not found [Error, City not found]\n" +
            "when governorate not found [Error, Governorate not found]\n")
    public ResponseEntity<String> addAdmin(@RequestBody CreateAdminDTO admin) {
        Long result = adminService.addAdmin(admin);
        return new ResponseEntity<>("Admin Created with ID: " + result, HttpStatus.OK);
    }

    @PutMapping("/admin")
    public ResponseEntity<String> updateAdmin(@Valid @RequestBody UpdateAdminDTO adminDTO) {
        Long id = adminService.updateAdmin(adminDTO);
        return new ResponseEntity<>("Admin Updated with ID: " + id, HttpStatus.OK);
    }

    @PutMapping("/admin/{adminId}")
    public ResponseEntity<String> updateAdminById(
            @PathVariable Long adminId,
            @Valid @RequestBody UpdateAdminDTO adminDTO) {
        adminDTO.setAdminId(adminId);
        return updateAdmin(adminDTO);
    }

    @DeleteMapping("/admin/{adminId}/type/{adminType}")
    @Operation(summary = "Delete an admin by ID and type",
            description = "Deletes an admin of the specified type\n" +
                    "city admin = 1998\n" +
                    "governorate admin = 1999\n" +
                    "master admin = 2000")
    @ApiResponse(responseCode = "200", description = "When admin deleted successfully")
    @ApiResponse(responseCode = "404", description = "When admin not found")
    @ApiResponse(responseCode = "422", description = "When admin type not valid")
    public ResponseEntity<String> deleteAdmin(
            @PathVariable Long adminId,
            @PathVariable int adminType) {

        Long id = adminService.deleteAdmin(adminId, adminType);
        return new ResponseEntity<>("Admin Deleted with ID: " + id, HttpStatus.OK);
    }

    @DeleteMapping("/admin")
    @Operation(summary = "Delete an admin with request body",
            description = "Deletes an admin based on provided ID and type\n" +
                    "city admin = 1998\n" +
                    "governorate admin = 1999\n" +
                    "master admin = 2000")
    public ResponseEntity<String> deleteAdminWithBody(@RequestBody AdminDeleteDTO deleteDTO) {
        Long result = adminService.deleteAdmin(deleteDTO.getAdminId(), deleteDTO.getAdminType());
        return new ResponseEntity<>("Admin Deleted with ID: " + result, HttpStatus.OK);
    }
}