package org.gp.civiceye.service;

import org.gp.civiceye.mapper.CitizenDTO;
import org.gp.civiceye.mapper.CityAdminDTO;
import org.gp.civiceye.mapper.CreateCityAdminDTO;
import org.gp.civiceye.repository.entity.CityAdmin;

import java.util.List;

public interface CityAdminService {
    public List<CityAdminDTO> GetAllCityAdmins();

    public CityAdminDTO getCityAdminById(Long id);

    public CreateCityAdminDTO addCityAdmin(CreateCityAdminDTO createCityAdminDTO);
}
