package org.gp.civiceye.service.impl;

import org.gp.civiceye.dto.CityDTO;
import org.gp.civiceye.repository.CityRepository;
import org.gp.civiceye.repository.entity.City;
import org.gp.civiceye.service.CityService;
import org.springframework.stereotype.Service;

@Service
public class CityServiceImpl implements CityService {

    CityRepository cityRepository;

    public CityServiceImpl(CityRepository cityrepository) {
        this.cityRepository = cityrepository;
    }

    @Override
    public CityDTO getCityDTOByName(Integer id) {
        return new CityDTO(cityRepository.findByCityId(id));

    }

    @Override
    public City getCityById(Integer id) {
        return cityRepository.findByCityId(id);

    }
}
