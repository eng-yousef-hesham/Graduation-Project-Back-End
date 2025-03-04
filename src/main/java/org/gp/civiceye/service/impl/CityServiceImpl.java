package org.gp.civiceye.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.gp.civiceye.dto.CityDTO;
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


    @Override
    public List<CityDTO> getAllCitiesByGovernorateId(Long governorateId) {
        Optional<Governorate> governorate = governorateRepository.findById(governorateId);

        if (governorate.isEmpty()) {
            throw new EntityNotFoundException("Governorate not found with id: " + governorateId);
        }

        return cityRepository.findAllByGovernorate(governorate.get())
                .stream()
                .map(CityDTO::new)
                .collect(Collectors.toList());
    }
}
