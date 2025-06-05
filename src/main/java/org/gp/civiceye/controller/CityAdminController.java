package org.gp.civiceye.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.gp.civiceye.mapper.cityadmin.CityAdminDTO;
import org.gp.civiceye.service.CityAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;


import java.util.List;

@RestController
@RequestMapping("api/V1")
public class CityAdminController {
    private CityAdminService cityAdminService;


    @Autowired
    public CityAdminController(CityAdminService Cityadminaervice) {
        this.cityAdminService = Cityadminaervice;
    }

    @GetMapping("/cityadmins")
    @Operation(summary = "Get all city admins", description = "Fetches a list of all city administrators.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved city admins")
    public ResponseEntity<Page<CityAdminDTO>> getAllCityAdmins(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        HttpHeaders headers = new HttpHeaders();
        Page<CityAdminDTO> cityAdmins = cityAdminService.getAllCityAdmins(page, size);

        return new ResponseEntity<Page<CityAdminDTO>>(cityAdmins, HttpStatus.OK);
    }


    @GetMapping("/cityadmin/{id}")
    public ResponseEntity<CityAdminDTO> getCityAdminById(@PathVariable(name = "id") Long id) {
        CityAdminDTO cityAdminDTO = cityAdminService.getCityAdminById(id);
        if (cityAdminDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cityAdminDTO, HttpStatus.OK);
    }


}
