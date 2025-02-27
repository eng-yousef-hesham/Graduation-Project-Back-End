package org.gp.civiceye.service;

import org.gp.civiceye.mapper.GovernrateAdminDTO;

import java.util.List;

public interface GovernorateAdminService {

    public List<GovernrateAdminDTO> GetAllGovernrateAdmins();

    public GovernrateAdminDTO getGovernrateAdminById(Long id);
}
