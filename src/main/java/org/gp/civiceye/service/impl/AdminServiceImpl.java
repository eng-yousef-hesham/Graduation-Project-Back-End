package org.gp.civiceye.service.impl;

import org.gp.civiceye.exception.*;
import org.gp.civiceye.mapper.CreateAdminDTO;
import org.gp.civiceye.mapper.UpdateAdminDTO;
import org.gp.civiceye.repository.*;
import org.gp.civiceye.repository.entity.*;
import org.gp.civiceye.service.AdminService;
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

    @Autowired
    public AdminServiceImpl(CityRepository cityRepository, CityAdminRepository cityAdminRepository,
                            GovernorateRepository governorateRepository, GovernorateAdminRepository governorateAdminRepository,
                            MasterAdminRepository masterAdminRepository, PasswordEncoder passwordEncoder) {
        this.cityRepository = cityRepository;
        this.governorateRepository = governorateRepository;
        this.cityAdminRepository = cityAdminRepository;
        this.governorateAdminRepository = governorateAdminRepository;
        this.masterAdminRepository = masterAdminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /*city admin = 1998
    governorate admin = 1999
    master admin = 2000
    */
    @Override
    public Long addAdmin(CreateAdminDTO admin) {
        String password = admin.getHashPassword();
        String encodedPassword = passwordEncoder.encode(password);
        admin.setHashPassword(encodedPassword);
        Admin savedAdmin = null;
        if (admin.getType() == 1998) {
            Long cityId = admin.getCityId();
            City city = cityRepository.findById(cityId).orElseThrow(() -> new CityNotFoundException(cityId));

            CityAdmin cityAdmin = CityAdmin.builder()
                    .nationalId(admin.getNationalId())
                    .firstName(admin.getFirstName())
                    .lastName(admin.getLastName())
                    .email(admin.getEmail())
                    .passwordHash(admin.getHashPassword())
                    .isActive(true)
                    .city(city)
                    .build();

            savedAdmin = cityAdminRepository.save(cityAdmin);
        } else if (admin.getType() == 1999) {
            Long govenorateId = admin.getGovernorateId();
            Governorate governorate = governorateRepository.findById(govenorateId).orElseThrow(() -> new GovernorateNotFoundException(govenorateId));

            GovernorateAdmin governorateAdmin = GovernorateAdmin.builder()
                    .nationalId(admin.getNationalId())
                    .firstName(admin.getFirstName())
                    .lastName(admin.getLastName())
                    .email(admin.getEmail())
                    .passwordHash(admin.getHashPassword())
                    .governorate(governorate)
                    .isActive(true)
                    .build();

            savedAdmin = governorateAdminRepository.save(governorateAdmin);

        } else if (admin.getType() == 2000) {
            MasterAdmin masterAdmin = MasterAdmin.builder()
                    .nationalId(admin.getNationalId())
                    .firstName(admin.getFirstName())
                    .lastName(admin.getLastName())
                    .email(admin.getEmail())
                    .passwordHash(admin.getHashPassword())
                    .isActive(true)
                    .build();

            savedAdmin = masterAdminRepository.save(masterAdmin);
        } else {
            throw new InvalidAdminTypeException();
        }
        return savedAdmin.getAdminId();
    }

    @Override
    public Long updateAdmin(UpdateAdminDTO admin) {
        if (admin.getType() == 1998) {
            CityAdmin cityAdmin = cityAdminRepository.findById(admin.getAdminId()).orElseThrow(() -> new AdminNotFoundException("City admin not found with id: " + admin.getAdminId()));

            if (admin.getCityId() != null) {
                City city = cityRepository.findById(admin.getCityId()).orElseThrow(() -> new CityNotFoundException(admin.getCityId()));
                cityAdmin.setCity(city);
            }

            updateAdminBasicInfo(cityAdmin, admin);

            CityAdmin savedCityAdmin = cityAdminRepository.save(cityAdmin);
            return savedCityAdmin.getAdminId();
        } else if (admin.getType() == 1999) {
            GovernorateAdmin governorateAdmin = governorateAdminRepository.findById(admin.getAdminId()).orElseThrow(() -> new AdminNotFoundException("Governorate admin not found with id: " + admin.getAdminId()));

            if (admin.getGovernorateId() != null) {
                Governorate governorate = governorateRepository.findById(admin.getGovernorateId()).orElseThrow(() -> new GovernorateNotFoundException(admin.getGovernorateId()));
                governorateAdmin.setGovernorate(governorate);
            }

            updateAdminBasicInfo(governorateAdmin, admin);
            GovernorateAdmin savedGovernorateAdmin = governorateAdminRepository.save(governorateAdmin);
            return savedGovernorateAdmin.getAdminId();
        } else if (admin.getType() == 2000) {
            MasterAdmin masterAdmin = masterAdminRepository.findById(admin.getAdminId()).orElseThrow(() -> new AdminNotFoundException("Master admin not found with id: " + admin.getAdminId()));

            updateAdminBasicInfo(masterAdmin, admin);
            MasterAdmin savedMasterAdmin = masterAdminRepository.save(masterAdmin);
            return savedMasterAdmin.getAdminId();
        }
        throw new InvalidAdminTypeException();
    }

    private <T extends Admin> void updateAdminBasicInfo(T admin, UpdateAdminDTO updateData) {
        if (updateData.getFirstName() != null) {
            admin.setFirstName(updateData.getFirstName());
        }

        if (updateData.getLastName() != null) {
            admin.setLastName(updateData.getLastName());
        }

        if (updateData.getHashPassword() != null && !updateData.getHashPassword().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(updateData.getHashPassword());
            admin.setPasswordHash(encodedPassword);
        }
    }

    @Override
    public Long deleteAdmin(Long adminId, int adminType) {
        if (adminType == 1998) {
            CityAdmin existingAdmin = cityAdminRepository.findById(adminId).orElseThrow(() -> new AdminNotFoundException("City admin not found with id: " + adminId));
            cityAdminRepository.deleteById(adminId);
            return existingAdmin.getAdminId();
        } else if (adminType == 1999) {
            GovernorateAdmin existingAdmin = governorateAdminRepository.findById(adminId).orElseThrow(() -> new AdminNotFoundException("Governorate admin not found with id: " + adminId));
            governorateAdminRepository.deleteById(adminId);
            return existingAdmin.getAdminId();
        } else if (adminType == 2000) {
            MasterAdmin existingAdmin = masterAdminRepository.findById(adminId).orElseThrow(() -> new AdminNotFoundException("Master admin not found with id: " + adminId));
            long masterAdminCount = masterAdminRepository.count();
            if (masterAdminCount <= 1) {
                throw new IllegalOperationException("Cannot delete the last master admin");
            }
            masterAdminRepository.deleteById(adminId);
            return existingAdmin.getAdminId();
        }
        throw new InvalidAdminTypeException();
    }
}