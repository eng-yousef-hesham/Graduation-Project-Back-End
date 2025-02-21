package org.gp.civiceye.service;

import org.gp.civiceye.dto.CityDTO;
import org.gp.civiceye.repository.entity.City;


public interface CityService {
    CityDTO getCityDTOByName(Integer id);
    City getCityById(Integer id);
}
