package org.gp.civiceye.service;

import org.gp.civiceye.mapper.CreateAdminDTO;
import org.gp.civiceye.mapper.UpdateAdminDTO;
import org.springframework.http.ResponseEntity;


public interface AdminService {
    public ResponseEntity<String> addAdmin(CreateAdminDTO admin);
    ResponseEntity<String> updateAdmin(UpdateAdminDTO admin);
}
