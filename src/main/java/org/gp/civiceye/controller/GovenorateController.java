package org.gp.civiceye.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.gp.civiceye.mapper.GovernorateDTO;
import org.gp.civiceye.service.GovernorateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/V1")
public class GovenorateController {
    private GovernorateService goverorateService;

    @Autowired
    public GovenorateController(GovernorateService goverorateService) {
        this.goverorateService = goverorateService;
    }

    @GetMapping("/governorates")
    @Operation(summary = "Get all governorates", description = "Fetches a list of all governorates without their cities.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved governorates" )
    public ResponseEntity<List<GovernorateDTO>> getAllGovernorates() {

        return ResponseEntity.ok(goverorateService.GetAllGovernorates());
    }

}
