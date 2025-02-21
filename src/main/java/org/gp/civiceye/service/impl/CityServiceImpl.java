package org.gp.civiceye.service.impl;

import org.gp.civiceye.dto.CityDTO;
import org.gp.civiceye.repository.CityRepository;
import org.gp.civiceye.repository.entity.City;
import org.gp.civiceye.service.CityService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CityServiceImpl implements CityService {

    CityRepository cityRepository;

    public CityServiceImpl(CityRepository cityrepository) {
        this.cityRepository = cityrepository;
    }

    @Override
    public CityDTO getCityDTOByName(Long id) {

        Optional<City> optionalCity = cityRepository.findById(id);
        if (optionalCity.isPresent()) {
            return new CityDTO(optionalCity.get());
        }
        return null;
    }

    @Override
    public City getCityById(Long id) {
        Optional<City> city = cityRepository.findById(id);
        return city.orElse(null);

    }
}
