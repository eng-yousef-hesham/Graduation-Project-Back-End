package org.gp.civiceye.controller;


import org.gp.civiceye.mapper.citizen.CitizenDTO;
import org.gp.civiceye.mapper.citizen.CreateCitizenDTO;
import org.gp.civiceye.service.CitizenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/V1")
public class CitizenController {

    private final CitizenService citizenService;

    public CitizenController(CitizenService citizenservice) {
        this.citizenService = citizenservice;
    }


    @GetMapping("/Citizen")
    public ResponseEntity<List<CitizenDTO>> getAllCitizens() {


//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Content-Type", "application/json");
//        headers.add("Custom-Header", "EmployeeCreated");
        List<CitizenDTO> citizens = citizenService.GetAllCitizens();
        if (citizens.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(citizens, HttpStatus.OK);
    }

    @PostMapping("/citizenRegister")
    public ResponseEntity<String> addCitizen(@RequestBody CreateCitizenDTO Citizen) {
        Long result = citizenService.addCitizen(Citizen);
        return new ResponseEntity<>("Citizen Created with ID: " + result, HttpStatus.OK);
    }
}
