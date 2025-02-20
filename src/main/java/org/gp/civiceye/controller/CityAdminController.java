package org.gp.civiceye.controller;

import org.gp.civiceye.repository.CityAdminRepository;
import org.gp.civiceye.service.CityAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/V1")
public class CityAdminController {
    CityAdminService cityAdminService;

    @Autowired
    public CityAdminController(CityAdminService Cityadminaervice) {
        this.cityAdminService = Cityadminaervice;
    }

    @GetMapping("/cityadmins")
    public ResponseEntity<Map<String, Object>> getAllCityAdmins() {

        HashMap<String, Object> response = new HashMap<>();

        response.put("statusText", HttpStatus.OK.getReasonPhrase());
        response.put("statusCode",HttpStatus.OK.value());
        response.put("message", "Retrieved All city admins ");
        response.put("data", cityAdminService.GetAllCityAdmins());

//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Content-Type", "application/json");
//        headers.add("Custom-Header", "EmployeeCreated");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
