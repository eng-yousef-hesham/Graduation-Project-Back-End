package org.gp.civiceye.service.impl;

import org.gp.civiceye.mapper.GovernrateAdminDTO;
import org.gp.civiceye.repository.GovernorateAdminRepository;
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
     GovernorateAdminRepository governrateAdminRepository;

     @Autowired
     public GovernorateAdminServiceImpl(GovernorateAdminRepository governrateadminrepository) {
         this.governrateAdminRepository = governrateadminrepository;
     }


    @Override
    public Page<GovernrateAdminDTO> getAllGovernrateAdmins(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return governrateAdminRepository.findAll(pageable)
                .map(GovernrateAdminDTO::new);
    }

    @Override
    public GovernrateAdminDTO getGovernrateAdminById(Long id) {
        Optional<GovernorateAdmin> governrateAdmin = governrateAdminRepository.findById(id);
        if (governrateAdmin.isPresent()) {
            return new GovernrateAdminDTO(governrateAdmin.get());
        }
        return null;
    }


}
