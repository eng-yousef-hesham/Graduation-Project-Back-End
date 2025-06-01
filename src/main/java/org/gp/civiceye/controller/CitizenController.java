package org.gp.civiceye.controller;


import org.gp.civiceye.mapper.CreateAdminDTO;
import org.gp.civiceye.mapper.citizen.CitizenDTO;
import org.gp.civiceye.mapper.citizen.CreateCitizenDTO;
import org.gp.civiceye.service.CitizenService;
import org.gp.civiceye.service.impl.admin.AddAdminResult;
import org.gp.civiceye.service.impl.citizen.AddCitizenResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/V1")
public class CitizenController {

    private CitizenService citizenService;

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
        AddCitizenResult result = citizenService.addCitizen(Citizen);

        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getMessage(), HttpStatus.OK);
        } else if (result.getMessage().contains("not found")) {
            return new ResponseEntity<>(result.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } else {
            return new ResponseEntity<>(result.getMessage(), HttpStatus.CONFLICT);
        }
    }


}
