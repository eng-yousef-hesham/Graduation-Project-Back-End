package org.gp.civiceye.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.gp.civiceye.mapper.CityDTO;
import org.gp.civiceye.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/V1")
public class CityController {
    private CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/city/{id}")
    @Operation(summary = "Get one city by city id", description = "Fetches only one city.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved city " )
    public ResponseEntity<CityDTO> getCityById(@PathVariable Long id) {
        CityDTO city = cityService.getCityDTOByName(id);
        return ResponseEntity.ok(city);
    }

    @GetMapping("/cities/{id}")
    @Operation(summary = "Get all cities by governorate id", description = "Fetches a list of all cities.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved cities" )
    public ResponseEntity<List<CityDTO>> getAllCitiesByGovernorateId(@PathVariable Long id) {
        List<CityDTO> cities = cityService.getAllCitiesByGovernorateId(id);
        return ResponseEntity.ok(cities);
    }

}
