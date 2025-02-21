package org.gp.civiceye.service.impl;

import org.gp.civiceye.mapper.CityAdminDTO;
import org.gp.civiceye.mapper.CreateCityAdminDTO;
import org.gp.civiceye.repository.CityAdminRepository;
import org.gp.civiceye.repository.entity.City;
import org.gp.civiceye.repository.entity.CityAdmin;
import org.gp.civiceye.service.CityAdminService;
import org.gp.civiceye.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CityAdminServiceImpl implements CityAdminService {
    CityAdminRepository cityAdminRepository;
    CityService cityService;

    @Autowired
    public CityAdminServiceImpl(CityAdminRepository cityadminrepository, CityService cityservice) {
        this.cityAdminRepository = cityadminrepository;
        this.cityService = cityservice;

    }

    @Override
    public List<CityAdminDTO> GetAllCityAdmins() {
        return cityAdminRepository.findAll().stream()
                .map(CityAdminDTO::new)
                .collect(Collectors.toList());
    }



    @Override
    public CityAdminDTO getCityAdminById(Long id) {

        Optional<CityAdmin> cityAdmin = cityAdminRepository.findById(id);
        if (cityAdmin.isPresent()) {
            return new CityAdminDTO(cityAdmin.get());
        }
        return null;


    }

    public CreateCityAdminDTO addCityAdmin(CreateCityAdminDTO CreateCityAdminDTO) {

        City city = cityService.getCityById(CreateCityAdminDTO.getCityId());

        CityAdmin cityAdmin = CreateCityAdminDTO.toCityAdmin(city);
        return new CreateCityAdminDTO(cityAdminRepository.save(cityAdmin));
    }
}
