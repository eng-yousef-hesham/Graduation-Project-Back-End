package org.gp.civiceye.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.gp.civiceye.mapper.cityadmin.CityAdminDTO;
import org.gp.civiceye.service.CityAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;


import java.util.List;

@RestController
@RequestMapping("api/v1/cityadmins")
public class CityAdminController {
    private final CityAdminService cityAdminService;

    @Autowired
    public CityAdminController(CityAdminService CityAdminService) {
        this.cityAdminService = CityAdminService;
    }

    @GetMapping
    @Operation(summary = "Get all city admins", description = "Fetches a list of all city administrators.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved city admins")
    public ResponseEntity<Page<CityAdminDTO>> getAllCityAdmins(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Long cityId,
            @RequestParam(required = false) Long govId
    ) {
        Page<CityAdminDTO> cityAdmins = cityAdminService.getAdmins(page, size, cityId, govId);
        return new ResponseEntity<>(cityAdmins, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityAdminDTO> getCityAdminById(@PathVariable(name = "id") Long id) {
        CityAdminDTO cityAdminDTO = cityAdminService.getCityAdminById(id);
        return new ResponseEntity<>(cityAdminDTO, HttpStatus.OK);
    }
}
