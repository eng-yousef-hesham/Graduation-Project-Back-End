package org.gp.civiceye.service;

import org.gp.civiceye.mapper.cityadmin.CityAdminDTO;
import org.gp.civiceye.mapper.cityadmin.CreateCityAdminDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CityAdminService {
    public Page<CityAdminDTO> getAllCityAdmins(int page, int size);

    public CityAdminDTO getCityAdminById(Long id);

    public CreateCityAdminDTO addCityAdmin(CreateCityAdminDTO createCityAdminDTO);
}
