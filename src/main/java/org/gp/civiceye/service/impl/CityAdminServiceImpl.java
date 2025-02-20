package org.gp.civiceye.service.impl;

import org.gp.civiceye.mapper.CitizenDTO;
import org.gp.civiceye.mapper.CityAdminDTO;
import org.gp.civiceye.repository.CitizenRepository;
import org.gp.civiceye.repository.CityAdminRepository;
import org.gp.civiceye.service.CityAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityAdminServiceImpl implements CityAdminService {
    CityAdminRepository cityAdminRepository;

    @Autowired
    public CityAdminServiceImpl(CityAdminRepository cityadminrepository) {
        this.cityAdminRepository = cityadminrepository;

    }

    @Override
    public List<CityAdminDTO> GetAllCityAdmins() {
        return cityAdminRepository.findAll().stream()
                .map(CityAdminDTO::new)
                .collect(Collectors.toList());
    }
}
