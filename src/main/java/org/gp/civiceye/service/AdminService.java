package org.gp.civiceye.service;

import org.gp.civiceye.mapper.CreateAdminDTO;
import org.gp.civiceye.mapper.UpdateAdminDTO;

public interface AdminService {
    Long addAdmin(CreateAdminDTO admin);
    Long updateAdmin(UpdateAdminDTO admin);
    void deleteAdmin(Long adminId);
}