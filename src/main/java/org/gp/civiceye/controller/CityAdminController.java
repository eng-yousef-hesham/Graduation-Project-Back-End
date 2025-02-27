package org.gp.civiceye.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.gp.civiceye.mapper.CityAdminDTO;
import org.gp.civiceye.mapper.CreateCityAdminDTO;
import org.gp.civiceye.repository.CityAdminRepository;
import org.gp.civiceye.repository.entity.CityAdmin;
import org.gp.civiceye.service.CityAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/V1")
public class CityAdminController {
    private CityAdminService cityAdminService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public CityAdminController(CityAdminService Cityadminaervice, PasswordEncoder passwordencoder) {
        this.cityAdminService = Cityadminaervice;
        this.passwordEncoder = passwordencoder;
    }

    @GetMapping("/cityadmins")
    @Operation(summary = "Get all city admins", description = "Fetches a list of all city administrators.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved city admins" )
    public ResponseEntity<List<CityAdminDTO>> getAllCityAdmins() {

//        HashMap<String, Object> response = new HashMap<>();
//        response.put("data", cityAdminService.GetAllCityAdmins());


        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
//        headers.add("Custom-Header", "yousef hesham");

        return new ResponseEntity<List<CityAdminDTO>>(cityAdminService.GetAllCityAdmins(), headers,HttpStatus.OK);
    }


    @GetMapping("/cityadmin/{id}")
    public ResponseEntity<CityAdminDTO> getCityAdminById(@PathVariable(name = "id") Long id) {
        CityAdminDTO cityAdminDTO = cityAdminService.getCityAdminById(id);
        if (cityAdminDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cityAdminDTO, HttpStatus.OK);
    }


    @PostMapping("/cityadmin")
    public ResponseEntity<String> addCityAdmin(@RequestBody CreateCityAdminDTO createCityAdminDTO) {
        try {
            String password = createCityAdminDTO.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            createCityAdminDTO.setPassword(encodedPassword);
            CreateCityAdminDTO savedCityAdmin = cityAdminService.addCityAdmin(createCityAdminDTO);

            if (savedCityAdmin.getAdminId() > 0) {
                return new ResponseEntity<>("City admin created successfully", HttpStatus.CREATED);
            }else {
                return new ResponseEntity<>("City admin creation failed", HttpStatus.BAD_REQUEST);
            }


        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }


}
