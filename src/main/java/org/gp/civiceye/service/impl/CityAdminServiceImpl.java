package org.gp.civiceye.service.impl;

import org.gp.civiceye.mapper.cityadmin.CityAdminDTO;
import org.gp.civiceye.mapper.cityadmin.CreateCityAdminDTO;
import org.gp.civiceye.repository.CityAdminRepository;
import org.gp.civiceye.repository.entity.City;
import org.gp.civiceye.repository.entity.CityAdmin;
import org.gp.civiceye.service.CityAdminService;
import org.gp.civiceye.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public Page<CityAdminDTO> getAllCityAdmins(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return cityAdminRepository.findAll(pageable).map(CityAdminDTO::new);
    }


    @Override
    public CityAdminDTO getCityAdminById(Long id) {
        Optional<CityAdmin> cityAdminOptional = cityAdminRepository.findById(id);
        return cityAdminOptional.map(CityAdminDTO::new).orElse(null);
    }

    public CreateCityAdminDTO addCityAdmin(CreateCityAdminDTO CreateCityAdminDTO) {

        City city = cityService.getCityById(CreateCityAdminDTO.getCityId());

        CityAdmin cityAdmin = CreateCityAdminDTO.toCityAdmin(city);
        return new CreateCityAdminDTO(cityAdminRepository.save(cityAdmin));
    }
}
