package org.gp.civiceye.util;

import org.gp.civiceye.exception.AdminNotFoundException;
import org.gp.civiceye.exception.CityNotFoundException;
import org.gp.civiceye.exception.GovernorateNotFoundException;
import org.gp.civiceye.repository.AdminRepository;
import org.gp.civiceye.repository.CityRepository;
import org.gp.civiceye.repository.GovernorateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidationService {

    private final CityRepository cityRepository;
    private final GovernorateRepository governorateRepository;
    private final AdminRepository adminRepository;

    @Autowired
    public ValidationService(CityRepository cityRepository, GovernorateRepository governorateRepository, AdminRepository adminRepository) {
        this.cityRepository = cityRepository;
        this.governorateRepository = governorateRepository;
        this.adminRepository = adminRepository;
    }

    public void validateCityExists(Long cityId) {
        if (!cityRepository.existsById(cityId)) {
            throw new CityNotFoundException(cityId);
        }
    }

    public void validateGovernorateExists(Long govId) {
        if (!governorateRepository.existsById(govId)) {
            throw new GovernorateNotFoundException(govId);
        }
    }

    public void validateAdminExists(Long id) {
        if (!adminRepository.existsById(id)) {
            throw new AdminNotFoundException(id);
        }
    }
}