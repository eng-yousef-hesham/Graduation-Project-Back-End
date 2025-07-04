package org.gp.civiceye.controller;


import org.gp.civiceye.mapper.citizen.CitizenDTO;
import org.gp.civiceye.mapper.citizen.CreateCitizenDTO;
import org.gp.civiceye.service.CitizenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/citizens")
public class CitizenController {
    private final CitizenService citizenService;

    public CitizenController(CitizenService citizenservice) {
        this.citizenService = citizenservice;
    }

    @GetMapping
    public ResponseEntity<List<CitizenDTO>> getAllCitizens() {
        List<CitizenDTO> citizens = citizenService.getAllCitizens();
        return new ResponseEntity<>(citizens, HttpStatus.OK);
    }

    @PostMapping("/Register")
    public ResponseEntity<String> addCitizen(@RequestBody CreateCitizenDTO Citizen) {
        Long result = citizenService.addCitizen(Citizen);
        return new ResponseEntity<>("Citizen Created with ID: " + result, HttpStatus.OK);
    }
}
