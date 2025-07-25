package org.gp.civiceye.controller;

import org.gp.civiceye.mapper.MasterAdminDTO;
import org.gp.civiceye.service.MasterAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/masteradmins")
public class MasterAdminController {
    MasterAdminService masterAdminService;

    @Autowired
    public MasterAdminController(MasterAdminService masterAdminService) {
        this.masterAdminService = masterAdminService;
    }

    @GetMapping
    public ResponseEntity<List<MasterAdminDTO>> getAllMasterAdmins() {
        List<MasterAdminDTO> masterAdmins = masterAdminService.getAllMasterAdmins();
        return new ResponseEntity<>(masterAdmins, HttpStatus.OK);
    }

}
