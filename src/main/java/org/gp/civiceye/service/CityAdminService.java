package org.gp.civiceye.service;

import org.gp.civiceye.mapper.CitizenDTO;
import org.gp.civiceye.mapper.CityAdminDTO;

import java.util.List;

public interface CityAdminService {
    public List<CityAdminDTO> GetAllCityAdmins();
}
