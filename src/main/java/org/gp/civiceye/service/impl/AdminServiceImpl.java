package org.gp.civiceye.service.impl;

import jakarta.transaction.Transactional;
import org.gp.civiceye.exception.*;
import org.gp.civiceye.mapper.CreateAdminDTO;
import org.gp.civiceye.mapper.UpdateAdminDTO;
import org.gp.civiceye.repository.*;
import org.gp.civiceye.repository.entity.*;
import org.gp.civiceye.service.AdminService;
import org.gp.civiceye.util.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    private final CityRepository cityRepository;
    private final GovernorateRepository governorateRepository;
    private final CityAdminRepository cityAdminRepository;
    private final GovernorateAdminRepository governorateAdminRepository;
    private final MasterAdminRepository masterAdminRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdminRepository adminRepository;
    private final ValidationService validationService;

    @Autowired
    public AdminServiceImpl(CityRepository cityRepository, CityAdminRepository cityAdminRepository,
                            GovernorateRepository governorateRepository, GovernorateAdminRepository governorateAdminRepository,
                            MasterAdminRepository masterAdminRepository, PasswordEncoder passwordEncoder,
                            AdminRepository adminRepository, ValidationService validationService) {
        this.cityRepository = cityRepository;
        this.governorateRepository = governorateRepository;
        this.cityAdminRepository = cityAdminRepository;
        this.governorateAdminRepository = governorateAdminRepository;
        this.masterAdminRepository = masterAdminRepository;
        this.passwordEncoder = passwordEncoder;
        this.adminRepository = adminRepository;
        this.validationService = validationService;
    }

    /*city admin = 1998
    governorate admin = 1999
    master admin = 2000
    */
    @Override
    public Long addAdmin(CreateAdminDTO admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        Admin savedAdmin = switch (admin.getType()) {
            case 1998 -> createCityAdmin(admin);
            case 1999 -> createGovernorateAdmin(admin);
            case 2000 -> createMasterAdmin(admin);
            default -> throw new InvalidAdminTypeException();
        };
        return savedAdmin.getAdminId();
    }

    @Override
    public Long updateAdmin(UpdateAdminDTO admin) {
        switch (admin.getType()) {
            case 1998 -> {
                return updateCityAdmin(admin);
            }
            case 1999 -> {
                return updateGovernorateAdmin(admin);
            }
            case 2000 -> {
                return updateMasterAdmin(admin);
            }
        }
        throw new InvalidAdminTypeException();
    }

    @Override
    public void deleteAdmin(Long adminId) {
        validationService.validateAdminExists(adminId);
        adminRepository.deleteById(adminId);
    }

    private Admin createCityAdmin(CreateAdminDTO admin) {
        Long cityId = admin.getCityId();
        City city = cityRepository.findById(cityId).orElseThrow(() -> new CityNotFoundException(cityId));

        CityAdmin cityAdmin = CityAdmin.builder()
                .nationalId(admin.getNationalId())
                .firstName(admin.getFirstName())
                .lastName(admin.getLastName())
                .email(admin.getEmail())
                .passwordHash(admin.getPassword())
                .isActive(true)
                .city(city)
                .build();

        return cityAdminRepository.save(cityAdmin);
    }

    private Admin createGovernorateAdmin(CreateAdminDTO admin) {
        Long governorateId = admin.getGovernorateId();
        Governorate governorate = governorateRepository.findById(governorateId).orElseThrow(() -> new GovernorateNotFoundException(governorateId));

        GovernorateAdmin governorateAdmin = GovernorateAdmin.builder()
                .nationalId(admin.getNationalId())
                .firstName(admin.getFirstName())
                .lastName(admin.getLastName())
                .email(admin.getEmail())
                .passwordHash(admin.getPassword())
                .governorate(governorate)
                .isActive(true)
                .build();

        return governorateAdminRepository.save(governorateAdmin);
    }

    private Admin createMasterAdmin(CreateAdminDTO admin) {

        MasterAdmin masterAdmin = MasterAdmin.builder()
                .nationalId(admin.getNationalId())
                .firstName(admin.getFirstName())
                .lastName(admin.getLastName())
                .email(admin.getEmail())
                .passwordHash(admin.getPassword())
                .isActive(true)
                .build();

        return masterAdminRepository.save(masterAdmin);
    }

    private Long updateMasterAdmin(UpdateAdminDTO admin) {
        MasterAdmin masterAdmin = masterAdminRepository.findById(admin.getAdminId()).orElseThrow(() -> new AdminNotFoundException("Master admin not found with id: " + admin.getAdminId()));

        updateAdminBasicInfo(masterAdmin, admin);
        MasterAdmin savedMasterAdmin = masterAdminRepository.save(masterAdmin);
        return savedMasterAdmin.getAdminId();
    }

    private Long updateGovernorateAdmin(UpdateAdminDTO admin) {
        GovernorateAdmin governorateAdmin = governorateAdminRepository
                .findById(admin.getAdminId())
                .orElseThrow(() ->
                        new AdminNotFoundException(
                                "Governorate admin not found with id: " + admin.getAdminId()));

        if (admin.getGovernorateId().isPresent()) {
            Governorate governorate = governorateRepository
                    .findById(admin.getGovernorateId().get())
                    .orElseThrow(() -> new GovernorateNotFoundException(admin.getGovernorateId().get()));
            governorateAdmin.setGovernorate(governorate);
        }

        updateAdminBasicInfo(governorateAdmin, admin);
        GovernorateAdmin savedGovernorateAdmin = governorateAdminRepository.save(governorateAdmin);
        return savedGovernorateAdmin.getAdminId();
    }

    private Long updateCityAdmin(UpdateAdminDTO admin) {
        CityAdmin cityAdmin = cityAdminRepository.findById(
                admin.getAdminId()).orElseThrow(() ->
                new AdminNotFoundException(
                        "City admin not found with id: " + admin.getAdminId()));

        if (admin.getCityId().isPresent()) {
            City city = cityRepository
                    .findById(admin.getCityId().get())
                    .orElseThrow(() -> new CityNotFoundException(admin.getCityId().get()));
            cityAdmin.setCity(city);
        }

        updateAdminBasicInfo(cityAdmin, admin);

        CityAdmin savedCityAdmin = cityAdminRepository.save(cityAdmin);
        return savedCityAdmin.getAdminId();
    }

    private <T extends Admin> void updateAdminBasicInfo(T admin, UpdateAdminDTO updateData) {
        if (updateData.getFirstName() != null) {
            admin.setFirstName(updateData.getFirstName());
        }

        if (updateData.getLastName() != null) {
            admin.setLastName(updateData.getLastName());
        }

        if (updateData.getPassword().isPresent() && !updateData.getPassword().get().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(updateData.getPassword().get());
            admin.setPasswordHash(encodedPassword);
        }
    }
}