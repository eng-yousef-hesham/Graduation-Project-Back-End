package org.gp.civiceye.service;

import org.gp.civiceye.mapper.CreateAdminDTO;
import org.gp.civiceye.mapper.UpdateAdminDTO;
import org.gp.civiceye.service.impl.admin.AddAdminResult;
import org.gp.civiceye.service.impl.admin.DeleteAdminResult;
import org.gp.civiceye.service.impl.admin.UpdateAdminResult;

public interface AdminService {
    AddAdminResult addAdmin(CreateAdminDTO admin);
    UpdateAdminResult updateAdmin(UpdateAdminDTO admin);
    DeleteAdminResult deleteAdmin(Long adminId, int adminType);
}