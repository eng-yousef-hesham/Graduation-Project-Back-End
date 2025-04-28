package org.gp.civiceye.service;

import org.gp.civiceye.mapper.cityadmin.CityAdminDTO;
import org.gp.civiceye.mapper.cityadmin.CreateCityAdminDTO;

import java.util.List;

public interface CityAdminService {
    public List<CityAdminDTO> GetAllCityAdmins();

    public CityAdminDTO getCityAdminById(Long id);

    public CreateCityAdminDTO addCityAdmin(CreateCityAdminDTO createCityAdminDTO);
}
