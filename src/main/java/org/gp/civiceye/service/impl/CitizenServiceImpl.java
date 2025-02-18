package org.gp.civiceye.service.impl;

import org.gp.civiceye.repository.CitizenRepository;
import org.gp.civiceye.repository.entity.Citizen;
import org.gp.civiceye.service.CitizenService;
import org.gp.civiceye.mapper.CitizenDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CitizenServiceImpl implements CitizenService {

    private  CitizenRepository citizenRepository;

    @Autowired
    public CitizenServiceImpl(CitizenRepository citizenrepository) {
        this.citizenRepository = citizenrepository;

    }

    @Override
    public List<CitizenDTO> GetAllCitizens() {
        return citizenRepository.findAll().stream()
                .map(CitizenDTO::new)
                .collect(Collectors.toList());
    }
}
