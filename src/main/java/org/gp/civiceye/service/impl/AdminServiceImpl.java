package org.gp.civiceye.service.impl;

import org.gp.civiceye.mapper.CreateAdminDTO;

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
}
