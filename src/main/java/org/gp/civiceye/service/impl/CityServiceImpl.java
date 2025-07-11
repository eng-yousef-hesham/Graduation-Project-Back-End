package org.gp.civiceye.service.impl;

import org.gp.civiceye.exception.CityNotFoundException;
import org.gp.civiceye.exception.GovernorateNotFoundException;
import org.gp.civiceye.mapper.city.CityDTO;
import org.gp.civiceye.repository.CityRepository;
import org.gp.civiceye.repository.GovernorateRepository;
import org.gp.civiceye.repository.entity.City;
import org.gp.civiceye.repository.entity.Governorate;
import org.gp.civiceye.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final GovernorateRepository governorateRepository;

    @Autowired
    public CityServiceImpl(CityRepository cityRepository, GovernorateRepository governorateRepository) {
        this.cityRepository = cityRepository;
        this.governorateRepository = governorateRepository;
    }

    @Override
    public CityDTO getCityByCityId(Long id) {
        Optional<City> optionalCity = cityRepository.findById(id);
        return optionalCity.map(CityDTO::new)
                .orElseThrow(() -> new CityNotFoundException(id));
    }

    @Override
    public City getCityById(Long id) {
        Optional<City> city = cityRepository.findById(id);
        return city.orElse(null);

    }


    @Override
    public List<CityDTO> getAllCitiesByGovernorateId(Long governorateId) {
        Optional<Governorate> governorate = governorateRepository.findById(governorateId);

        if (governorate.isEmpty()) {
            throw new GovernorateNotFoundException(governorateId);
        }

        return cityRepository.findAllByGovernorate(governorate.get())
                .stream()
                .map(CityDTO::new)
                .collect(Collectors.toList());
    }
}
