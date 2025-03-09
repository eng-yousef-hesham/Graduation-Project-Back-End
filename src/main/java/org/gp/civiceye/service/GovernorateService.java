package org.gp.civiceye.service;


import org.gp.civiceye.mapper.GovernorateDTO;

import java.util.List;

public interface GovernorateService {

    GovernorateDTO getGovernorateByID(Long id);
    List<GovernorateDTO> GetAllGovernorates();
}
