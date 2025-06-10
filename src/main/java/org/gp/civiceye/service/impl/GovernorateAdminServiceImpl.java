package org.gp.civiceye.service.impl;

import org.gp.civiceye.exception.GovernorateNotFoundException;
import org.gp.civiceye.mapper.GovernrateAdminDTO;
import org.gp.civiceye.repository.GovernorateAdminRepository;
import org.gp.civiceye.repository.GovernorateRepository;
import org.gp.civiceye.repository.entity.Governorate;
import org.gp.civiceye.repository.entity.GovernorateAdmin;
import org.gp.civiceye.service.GovernorateAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GovernorateAdminServiceImpl implements GovernorateAdminService {
    GovernorateAdminRepository governorateAdminRepository;
    GovernorateRepository governorateRepository;

    @Autowired
    public GovernorateAdminServiceImpl(GovernorateAdminRepository governorateadminrepository, GovernorateRepository governorateRepository) {
        this.governorateAdminRepository = governorateadminrepository;
        this.governorateRepository = governorateRepository;
    }


    @Override
    public Page<GovernrateAdminDTO> getAllGovernorateAdmins(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return governorateAdminRepository.findAll(pageable)
                .map(GovernrateAdminDTO::new);
    }

    @Override
    public Page<GovernrateAdminDTO> getAllGovernorateAdminsByGovernorate(Long govId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Governorate governorate = governorateRepository.findById(govId).orElseThrow(() -> new GovernorateNotFoundException(govId));
        return governorateAdminRepository.findByGovernorate(governorate, pageable)
                .map(GovernrateAdminDTO::new);
    }

    @Override
    public GovernrateAdminDTO getGovernorateAdminById(Long id) {
        Optional<GovernorateAdmin> governorateAdmin = governorateAdminRepository.findById(id);
        return governorateAdmin.map(GovernrateAdminDTO::new).orElse(null);
    }


}
