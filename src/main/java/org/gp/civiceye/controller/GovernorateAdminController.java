package org.gp.civiceye.controller;


import org.gp.civiceye.mapper.GovernrateAdminDTO;
import org.gp.civiceye.service.GovernorateAdminService;
import org.gp.civiceye.service.GovernorateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/V1")
public class GovernorateAdminController {
    GovernorateAdminService governorateAdminService;

    @Autowired
    public GovernorateAdminController(GovernorateAdminService governorateAdminService) {
        this.governorateAdminService = governorateAdminService;
    }

    @GetMapping("/GovernorateAdmin")
    public ResponseEntity<Page<GovernrateAdminDTO>> getAllGovernorateAdmins(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<GovernrateAdminDTO> governorateAdmins = governorateAdminService.getAllGovernrateAdmins(page, size);

        return new ResponseEntity<>(governorateAdmins, HttpStatus.OK);
    }

    @GetMapping("/GovernorateAdmin/{id}")
    public ResponseEntity<GovernrateAdminDTO> getGovernrateAdminById(@PathVariable(name = "id") Long id) {
//        HashMap<String, Object> response = new HashMap<>();
//        response.put("statusText", HttpStatus.OK.getReasonPhrase());
//        response.put("statusCode", HttpStatus.OK.value());
//        response.put("message", "Retrieved GovernorateAdmin with ID : " + id);
//        response.put("data", governorateAdminService.getGovernrateAdminById(id));

        GovernrateAdminDTO governorateAdminDTO = governorateAdminService.getGovernrateAdminById(id);
        if (governorateAdminDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(governorateAdminDTO, HttpStatus.OK);
    }
}
