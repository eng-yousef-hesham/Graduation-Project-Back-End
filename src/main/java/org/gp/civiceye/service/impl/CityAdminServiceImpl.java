package org.gp.civiceye.service.impl;

import org.gp.civiceye.exception.AdminNotFoundException;
import org.gp.civiceye.mapper.cityadmin.CityAdminDTO;
import org.gp.civiceye.mapper.cityadmin.CreateCityAdminDTO;
import org.gp.civiceye.repository.CityAdminRepository;
import org.gp.civiceye.repository.entity.City;
import org.gp.civiceye.repository.entity.CityAdmin;
import org.gp.civiceye.service.CityAdminService;
import org.gp.civiceye.service.CityService;
import org.gp.civiceye.util.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CityAdminServiceImpl implements CityAdminService {
    CityAdminRepository cityAdminRepository;
    ValidationService validationService;
    CityService cityService;

    @Autowired
    public CityAdminServiceImpl(CityAdminRepository cityadminrepository, CityService cityservice,ValidationService validationService) {
        this.cityAdminRepository = cityadminrepository;
        this.validationService = validationService;
        this.cityService = cityservice;
    }

    @Override
    public Page<CityAdminDTO> getAdmins(int page, int size, Long cityId, Long govId) {
        Pageable pageable = PageRequest.of(page, size);
        if (cityId != null) {
            validationService.validateCityExists(cityId);
            return cityAdminRepository.findByCity_CityId(cityId, pageable).map(CityAdminDTO::new);
        }
        if (govId != null) {
            validationService.validateGovernorateExists(govId);
            return cityAdminRepository.findByCity_Governorate_GovernorateId(govId, pageable).map(CityAdminDTO::new);
        }
        return cityAdminRepository.findAll(pageable).map(CityAdminDTO::new);
    }

    @Override
    public CityAdminDTO getCityAdminById(Long id) {
        Optional<CityAdmin> cityAdminOptional = cityAdminRepository.findById(id);
        return cityAdminOptional.map(CityAdminDTO::new)
                .orElseThrow(() ->
                        new AdminNotFoundException("City admin not found with id: " + id));
    }

    public CreateCityAdminDTO addCityAdmin(CreateCityAdminDTO CreateCityAdminDTO) {

        City city = cityService.getCityById(CreateCityAdminDTO.getCityId());

        CityAdmin cityAdmin = CreateCityAdminDTO.toCityAdmin(city);
        return new CreateCityAdminDTO(cityAdminRepository.save(cityAdmin));
    }
}