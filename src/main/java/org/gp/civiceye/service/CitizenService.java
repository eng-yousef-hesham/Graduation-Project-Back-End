package org.gp.civiceye.service;

import org.gp.civiceye.mapper.citizen.CitizenDTO;
import org.gp.civiceye.mapper.citizen.CreateCitizenDTO;

import java.util.List;

public interface CitizenService {
    public  List<CitizenDTO> GetAllCitizens();

    public Long addCitizen(CreateCitizenDTO citizen);
}
