package org.gp.civiceye.service.impl;

import org.gp.civiceye.mapper.CreateAdminDTO;

import org.gp.civiceye.mapper.UpdateAdminDTO;
import org.gp.civiceye.repository.*;
import org.gp.civiceye.repository.entity.*;
import org.gp.civiceye.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    public ResponseEntity<String> addAdmin(CreateAdminDTO admin) {

        String password = admin.getHashPassword();
        String encodedPassword = passwordEncoder.encode(password);
        admin.setHashPassword(encodedPassword);

        try {
            if (admin.getType() == 1998) {

                Long cityId = admin.getCityId();
                Optional<City> city = cityRepository.findById(cityId);

                if (!city.isPresent()) {
                    return new ResponseEntity<>("Error, City not found",HttpStatus.UNPROCESSABLE_ENTITY);
                }

                CityAdmin cityAdmin = CityAdmin.builder()
                        .nationalId(admin.getNationalId())
                        .firstName(admin.getFirstName())
                        .lastName(admin.getLastName())
                        .email(admin.getEmail())
                        .passwordHash(admin.getHashPassword())
                        .city(city.get())
                        .build();

                cityAdminRepository.save(cityAdmin);
                return  new ResponseEntity<>("City admin created successfully",HttpStatus.OK);
            } else if (admin.getType() == 1999) {
                Long govenorateId = admin.getGovernorateId();
                Optional<Governorate> governorate = governorateRepository.findById(govenorateId);

                if (!governorate.isPresent()) {
                    return  new ResponseEntity<>("Error, Governorate not found",HttpStatus.UNPROCESSABLE_ENTITY);
                }

                GovernorateAdmin governorateAdmin = GovernorateAdmin.builder()
                        .nationalId(admin.getNationalId())
                        .firstName(admin.getFirstName())
                        .lastName(admin.getLastName())
                        .email(admin.getEmail())
                        .passwordHash(admin.getHashPassword())
                        .governorate(governorate.get())
                        .build();

                governorateAdminRepository.save(governorateAdmin);
                return  new ResponseEntity<>("governorate admin created successfully",HttpStatus.OK);

            } else if (admin.getType() == 2000) {
                MasterAdmin governorateAdmin = MasterAdmin.builder()
                        .nationalId(admin.getNationalId())
                        .firstName(admin.getFirstName())
                        .lastName(admin.getLastName())
                        .email(admin.getEmail())
                        .passwordHash(admin.getHashPassword())
                        .build();

                masterAdminRepository.save(governorateAdmin);
                return  new ResponseEntity<>("master admin created successfully",HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>("",HttpStatus.CONFLICT);
        }
        return  new ResponseEntity<>("Specified admin type not found",HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Override
    public ResponseEntity<String> updateAdmin(UpdateAdminDTO admin) {
        try {
            if (admin.getType() == 1998) {
                Optional<CityAdmin> existingAdmin = cityAdminRepository.findById(admin.getAdminId());
                if (!existingAdmin.isPresent()) {
                    return new ResponseEntity<>("Error, City admin not found", HttpStatus.NOT_FOUND);
                }

                CityAdmin cityAdmin = existingAdmin.get();

                if (admin.getCityId() != null) {
                    Optional<City> city = cityRepository.findById(admin.getCityId());
                    if (!city.isPresent()) {
                        return new ResponseEntity<>("Error, City not found", HttpStatus.UNPROCESSABLE_ENTITY);
                    }
                    cityAdmin.setCity(city.get());
                }

                updateAdminBasicInfo(cityAdmin, admin);

                cityAdminRepository.save(cityAdmin);
                return new ResponseEntity<>("City admin updated successfully", HttpStatus.OK);

            } else if (admin.getType() == 1999) {
                Optional<GovernorateAdmin> existingAdmin = governorateAdminRepository.findById(admin.getAdminId());
                if (!existingAdmin.isPresent()) {
                    return new ResponseEntity<>("Error, Governorate admin not found", HttpStatus.NOT_FOUND);
                }

                GovernorateAdmin governorateAdmin = existingAdmin.get();

                if (admin.getGovernorateId() != null) {
                    Optional<Governorate> governorate = governorateRepository.findById(admin.getGovernorateId());
                    if (!governorate.isPresent()) {
                        return new ResponseEntity<>("Error, Governorate not found", HttpStatus.UNPROCESSABLE_ENTITY);
                    }
                    governorateAdmin.setGovernorate(governorate.get());
                }

                updateAdminBasicInfo(governorateAdmin, admin);

                governorateAdminRepository.save(governorateAdmin);
                return new ResponseEntity<>("Governorate admin updated successfully", HttpStatus.OK);

            } else if (admin.getType() == 2000) {
                Optional<MasterAdmin> existingAdmin = masterAdminRepository.findById(admin.getAdminId());
                if (!existingAdmin.isPresent()) {
                    return new ResponseEntity<>("Error, Master admin not found", HttpStatus.NOT_FOUND);
                }

                MasterAdmin masterAdmin = existingAdmin.get();

                updateAdminBasicInfo(masterAdmin, admin);

                masterAdminRepository.save(masterAdmin);
                return new ResponseEntity<>("Master admin updated successfully", HttpStatus.OK);
            }

            return new ResponseEntity<>("Specified admin type not found", HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating admin: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private <T extends Admin> void updateAdminBasicInfo(T admin, UpdateAdminDTO updateData) {
        if (updateData.getFirstName() != null) {
            admin.setFirstName(updateData.getFirstName());
        }

        if (updateData.getLastName() != null) {
            admin.setLastName(updateData.getLastName());
        }

//        if (updateData.getEmail() != null) {
//            admin.setEmail(updateData.getEmail());
//        }
//
//        if (updateData.getNationalId() != null) {
//            admin.setNationalId(updateData.getNationalId());
//        }

        if (updateData.getHashPassword() != null && !updateData.getHashPassword().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(updateData.getHashPassword());
            admin.setPasswordHash(encodedPassword);
        }
    }
}
