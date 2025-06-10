package org.gp.civiceye.controller;


import org.gp.civiceye.mapper.GovernrateAdminDTO;
import org.gp.civiceye.service.GovernorateAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Long govId
    ) {
        Page<GovernrateAdminDTO> governorateAdmins;
        if (govId != null) {
            governorateAdmins = governorateAdminService.getAllGovernorateAdminsByGovernorate(govId, page, size);
        } else {
            governorateAdmins = governorateAdminService.getAllGovernorateAdmins(page, size);
        }
        return new ResponseEntity<>(governorateAdmins, HttpStatus.OK);
    }

    @GetMapping("/GovernorateAdmin/{id}")
    public ResponseEntity<GovernrateAdminDTO> getGovernorateAdminById(@PathVariable(name = "id") Long id) {
        GovernrateAdminDTO governorateAdminDTO = governorateAdminService.getGovernorateAdminById(id);
        if (governorateAdminDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(governorateAdminDTO, HttpStatus.OK);
    }
}
