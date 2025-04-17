package org.gp.civiceye.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.gp.civiceye.mapper.CreateAdminDTO;
import org.gp.civiceye.mapper.CreateCityAdminDTO;
import org.gp.civiceye.mapper.UpdateAdminDTO;
import org.gp.civiceye.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @ApiResponse(responseCode = "200", description = "when admin created successfully[City admin created successfully, Governorate admin created successfully, Master admin created successfully]\n"+
            "when admin type not added [Specified admin type not found]\n"+
            "when city not found [Error, City not found]\n"+
            "when governorate not found [Error, Governorate not found]\n")
    public ResponseEntity<String> addAdmin(@RequestBody CreateAdminDTO admin) {

        return adminService.addAdmin(admin);

    }

    @PutMapping("/admin")
    public ResponseEntity<String> updateAdmin(@Valid @RequestBody UpdateAdminDTO adminDTO) {
        return adminService.updateAdmin(adminDTO);
    }

    @PutMapping("/admin/{adminId}")
    public ResponseEntity<String> updateAdminById(
            @PathVariable Long adminId,
            @Valid @RequestBody UpdateAdminDTO adminDTO) {
        adminDTO.setAdminId(adminId);
        return adminService.updateAdmin(adminDTO);
    }




}
