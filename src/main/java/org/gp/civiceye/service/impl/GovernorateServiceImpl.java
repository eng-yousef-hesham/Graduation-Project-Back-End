package org.gp.civiceye.service.impl;

import org.gp.civiceye.mapper.GovernorateDTO;
import org.gp.civiceye.repository.GovernorateRepository;
import org.gp.civiceye.repository.entity.Governorate;
import org.gp.civiceye.service.GovernorateService;

import java.util.Optional;

public class GovernorateServiceImpl implements GovernorateService {

    GovernorateRepository governorateRepository;

    public GovernorateServiceImpl(GovernorateRepository governoraterepository) {
        this.governorateRepository = governoraterepository;
    }

    @Override
    public GovernorateDTO getGovernorateByID(Long id) {
        Optional<Governorate> governorateOptional =governorateRepository.findById(id);
        if (governorateOptional.isPresent()) {
            return new GovernorateDTO(governorateOptional.get());
        }
       return new GovernorateDTO();

    }
}
