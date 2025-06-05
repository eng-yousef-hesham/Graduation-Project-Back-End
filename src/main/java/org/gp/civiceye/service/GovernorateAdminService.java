package org.gp.civiceye.service;

import org.gp.civiceye.mapper.GovernrateAdminDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface GovernorateAdminService {

    public Page<GovernrateAdminDTO> getAllGovernrateAdmins(int page, int size);

    public GovernrateAdminDTO getGovernrateAdminById(Long id);
}
