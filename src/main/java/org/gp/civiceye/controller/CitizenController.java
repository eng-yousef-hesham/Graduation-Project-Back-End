package org.gp.civiceye.controller;


import org.gp.civiceye.mapper.CitizenDTO;
import org.gp.civiceye.repository.entity.Citizen;
import org.gp.civiceye.service.CitizenService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/V1")
public class CitizenController {

    private CitizenService citizenService;

    public CitizenController(CitizenService citizenservice) {
        this.citizenService = citizenservice;
    }


    @GetMapping("/Citizen")
    public ResponseEntity<Map<String, Object>> getAllCitizens() {

        HashMap<String, Object> response = new HashMap<>();

        response.put("statusText", HttpStatus.OK.getReasonPhrase());
        response.put("statusCode", HttpStatus.OK.value());
        response.put("message", "Retrieved All Citizens ");
        response.put("data", citizenService.GetAllCitizens());

//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Content-Type", "application/json");
//        headers.add("Custom-Header", "EmployeeCreated");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
