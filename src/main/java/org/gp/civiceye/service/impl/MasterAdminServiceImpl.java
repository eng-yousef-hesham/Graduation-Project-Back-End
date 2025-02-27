package org.gp.civiceye.service.impl;

import org.gp.civiceye.mapper.MasterAdminDTO;
import org.gp.civiceye.repository.MasterAdminRepository;
import org.gp.civiceye.service.MasterAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MasterAdminServiceImpl implements MasterAdminService {
    MasterAdminRepository masterAdminRepository;

    @Autowired
    public MasterAdminServiceImpl(MasterAdminRepository masterAdminRepository) {
        this.masterAdminRepository = masterAdminRepository;
    }

    public List<MasterAdminDTO> getAllMasterAdmins() {
        return masterAdminRepository.findAll().stream()
                .map(MasterAdminDTO::new)
                .collect(Collectors.toList());
    }
}
