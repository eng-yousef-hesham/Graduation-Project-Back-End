package org.gp.civiceye.service.impl;

import org.gp.civiceye.dto.GovernorateDTO;
import org.gp.civiceye.repository.GovernorateRepository;
import org.gp.civiceye.repository.entity.Governorate;
import org.gp.civiceye.service.GovernorateService;

public class GovernorateServiceImpl implements GovernorateService {

    GovernorateRepository governorateRepository;

    public GovernorateServiceImpl(GovernorateRepository governoraterepository) {
        this.governorateRepository = governoraterepository;
    }

    @Override
    public GovernorateDTO getGovernorateByID(Integer id) {
       return new GovernorateDTO(governorateRepository.findByGovernorateId(id));

    }
}
