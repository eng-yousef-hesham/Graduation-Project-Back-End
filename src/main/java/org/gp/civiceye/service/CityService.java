package org.gp.civiceye.service;


import org.gp.civiceye.mapper.city.CityDTO;
import org.gp.civiceye.repository.entity.City;

import java.util.List;


public interface CityService {
    CityDTO getCityByCityId(Long id);
    City getCityById(Long id);
    List<CityDTO> getAllCitiesByGovernorateId(Long id);
}
