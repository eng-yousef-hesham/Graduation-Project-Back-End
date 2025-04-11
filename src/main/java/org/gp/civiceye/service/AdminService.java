package org.gp.civiceye.service;

import org.gp.civiceye.mapper.CreateAdminDTO;
import org.springframework.http.ResponseEntity;


public interface AdminService {
    public ResponseEntity<String> addAdmin(CreateAdminDTO admin);
}
