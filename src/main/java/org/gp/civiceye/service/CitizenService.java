package org.gp.civiceye.service;

import org.gp.civiceye.mapper.citizen.CitizenDTO;
import org.gp.civiceye.mapper.citizen.CreateCitizenDTO;
import org.gp.civiceye.service.impl.citizen.AddCitizenResult;

import java.util.List;

public interface CitizenService {
    public  List<CitizenDTO> GetAllCitizens();

    public AddCitizenResult addCitizen(CreateCitizenDTO citizen);
}
