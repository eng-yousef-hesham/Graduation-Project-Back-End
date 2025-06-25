package org.gp.civiceye.service.impl;

import org.gp.civiceye.mapper.governorate.GovernorateDTO;
import org.gp.civiceye.repository.GovernorateRepository;
import org.gp.civiceye.repository.entity.Governorate;
import org.gp.civiceye.service.GovernorateService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GovernorateServiceImpl implements GovernorateService {

    GovernorateRepository governorateRepository;

    public GovernorateServiceImpl(GovernorateRepository governoraterepository) {
        this.governorateRepository = governoraterepository;
    }

    @Override
    public List<GovernorateDTO> GetAllGovernorates() {

        return governorateRepository.findAll().stream()
                .map(GovernorateDTO::new)
                .collect(Collectors.toList());
    }
}
