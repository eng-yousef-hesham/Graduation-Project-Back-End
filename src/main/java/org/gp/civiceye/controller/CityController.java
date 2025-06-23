package org.gp.civiceye.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.gp.civiceye.mapper.city.CityDTO;
import org.gp.civiceye.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cities")
public class CityController {
    private final CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get one city by city id", description = "Fetches only one city.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved city ")
    public ResponseEntity<CityDTO> getCityById(@PathVariable Long id) {
        CityDTO city = cityService.getCityByCityId(id);
        return ResponseEntity.ok(city);
    }

    @GetMapping
    @Operation(summary = "Get all cities by governorate id", description = "Fetches a list of all cities.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved cities")
    public ResponseEntity<List<CityDTO>> getAllCitiesByGovernorateId(@RequestParam Long govId) {
        List<CityDTO> cities = cityService.getAllCitiesByGovernorateId(govId);
        return ResponseEntity.ok(cities);
    }
}
