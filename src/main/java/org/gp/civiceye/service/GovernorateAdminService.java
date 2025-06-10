package org.gp.civiceye.service;

import org.gp.civiceye.mapper.GovernrateAdminDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface GovernorateAdminService {

    public Page<GovernrateAdminDTO> getAllGovernorateAdmins(int page, int size);
    Page<GovernrateAdminDTO> getAllGovernorateAdminsByGovernorate(Long govId, int page,int size);

    public GovernrateAdminDTO getGovernorateAdminById(Long id);
}
