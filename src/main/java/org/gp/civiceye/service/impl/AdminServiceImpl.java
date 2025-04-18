package org.gp.civiceye.service.impl;

import org.gp.civiceye.mapper.CreateAdminDTO;
import org.gp.civiceye.mapper.UpdateAdminDTO;
import org.gp.civiceye.repository.*;
import org.gp.civiceye.repository.entity.*;
import org.gp.civiceye.service.impl.admin.AddAdminResult;
import org.gp.civiceye.service.AdminService;
import org.gp.civiceye.service.impl.admin.AdminType;
import org.gp.civiceye.service.impl.admin.DeleteAdminResult;
import org.gp.civiceye.service.impl.admin.UpdateAdminResult;
import org.springframework.beans.factory.annotation.Autowired;
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
    public AddAdminResult addAdmin(CreateAdminDTO admin) {
        String password = admin.getHashPassword();
        String encodedPassword = passwordEncoder.encode(password);
        admin.setHashPassword(encodedPassword);

        try {
            if (admin.getType() == 1998) {
                Long cityId = admin.getCityId();
                Optional<City> city = cityRepository.findById(cityId);

                if (!city.isPresent()) {
                    return new AddAdminResult(false, "Error, City not found");
                }

                CityAdmin cityAdmin = CityAdmin.builder()
                        .nationalId(admin.getNationalId())
                        .firstName(admin.getFirstName())
                        .lastName(admin.getLastName())
                        .email(admin.getEmail())
                        .passwordHash(admin.getHashPassword())
                        .isActive(true)
                        .city(city.get())
                        .build();

                cityAdminRepository.save(cityAdmin);
                return new AddAdminResult(true, "City admin created successfully", AdminType.CITY_ADMIN);
            } else if (admin.getType() == 1999) {
                Long govenorateId = admin.getGovernorateId();
                Optional<Governorate> governorate = governorateRepository.findById(govenorateId);

                if (!governorate.isPresent()) {
                    return new AddAdminResult(false, "Error, Governorate not found");
                }

                GovernorateAdmin governorateAdmin = GovernorateAdmin.builder()
                        .nationalId(admin.getNationalId())
                        .firstName(admin.getFirstName())
                        .lastName(admin.getLastName())
                        .email(admin.getEmail())
                        .passwordHash(admin.getHashPassword())
                        .governorate(governorate.get())
                        .isActive(true)
                        .build();

                governorateAdminRepository.save(governorateAdmin);
                return new AddAdminResult(true, "Governorate admin created successfully", AdminType.GOVERNORATE_ADMIN);

            } else if (admin.getType() == 2000) {
                MasterAdmin masterAdmin = MasterAdmin.builder()
                        .nationalId(admin.getNationalId())
                        .firstName(admin.getFirstName())
                        .lastName(admin.getLastName())
                        .email(admin.getEmail())
                        .passwordHash(admin.getHashPassword())
                        .isActive(true)
                        .build();

                masterAdminRepository.save(masterAdmin);
                return new AddAdminResult(true, "Master admin created successfully", AdminType.MASTER_ADMIN);
            }

            return new AddAdminResult(false, "Specified admin type not found");
        } catch (Exception e) {
            return new AddAdminResult(false, "Error creating admin: " + e.getMessage());
        }
    }

    @Override
    public UpdateAdminResult updateAdmin(UpdateAdminDTO admin) {
        try {
            if (admin.getType() == 1998) {
                Optional<CityAdmin> existingAdmin = cityAdminRepository.findById(admin.getAdminId());
                if (!existingAdmin.isPresent()) {
                    return new UpdateAdminResult(false, "Error, City admin not found", AdminType.CITY_ADMIN);
                }

                CityAdmin cityAdmin = existingAdmin.get();

                if (admin.getCityId() != null) {
                    Optional<City> city = cityRepository.findById(admin.getCityId());
                    if (!city.isPresent()) {
                        return new UpdateAdminResult(false, "Error, City not found", AdminType.CITY_ADMIN);
                    }
                    cityAdmin.setCity(city.get());
                }

                updateAdminBasicInfo(cityAdmin, admin);

                cityAdminRepository.save(cityAdmin);
                return new UpdateAdminResult(true, "City admin updated successfully", AdminType.CITY_ADMIN);

            } else if (admin.getType() == 1999) {
                Optional<GovernorateAdmin> existingAdmin = governorateAdminRepository.findById(admin.getAdminId());
                if (!existingAdmin.isPresent()) {
                    return new UpdateAdminResult(false, "Error, Governorate admin not found", AdminType.GOVERNORATE_ADMIN);
                }

                GovernorateAdmin governorateAdmin = existingAdmin.get();

                if (admin.getGovernorateId() != null) {
                    Optional<Governorate> governorate = governorateRepository.findById(admin.getGovernorateId());
                    if (!governorate.isPresent()) {
                        return new UpdateAdminResult(false, "Error, Governorate not found", AdminType.GOVERNORATE_ADMIN);
                    }
                    governorateAdmin.setGovernorate(governorate.get());
                }

                updateAdminBasicInfo(governorateAdmin, admin);

                governorateAdminRepository.save(governorateAdmin);
                return new UpdateAdminResult(true, "Governorate admin updated successfully", AdminType.GOVERNORATE_ADMIN);

            } else if (admin.getType() == 2000) {
                Optional<MasterAdmin> existingAdmin = masterAdminRepository.findById(admin.getAdminId());
                if (!existingAdmin.isPresent()) {
                    return new UpdateAdminResult(false, "Error, Master admin not found", AdminType.MASTER_ADMIN);
                }

                MasterAdmin masterAdmin = existingAdmin.get();

                updateAdminBasicInfo(masterAdmin, admin);

                masterAdminRepository.save(masterAdmin);
                return new UpdateAdminResult(true, "Master admin updated successfully", AdminType.MASTER_ADMIN);
            }

            return new UpdateAdminResult(false, "Specified admin type not found", AdminType.UNKNOWN);
        } catch (Exception e) {
            return new UpdateAdminResult(false, "Error updating admin: " + e.getMessage(), AdminType.UNKNOWN);
        }
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
    public DeleteAdminResult deleteAdmin(Long adminId, int adminType) {
        try {
            if (adminType == 1998) {
                Optional<CityAdmin> existingAdmin = cityAdminRepository.findById(adminId);
                if (!existingAdmin.isPresent()) {
                    return new DeleteAdminResult(false, "Error, City admin not found", AdminType.CITY_ADMIN);
                }

                cityAdminRepository.deleteById(adminId);
                return new DeleteAdminResult(true, "City admin deleted successfully", AdminType.CITY_ADMIN);

            } else if (adminType == 1999) {
                Optional<GovernorateAdmin> existingAdmin = governorateAdminRepository.findById(adminId);
                if (!existingAdmin.isPresent()) {
                    return new DeleteAdminResult(false, "Error, Governorate admin not found", AdminType.GOVERNORATE_ADMIN);
                }

                governorateAdminRepository.deleteById(adminId);
                return new DeleteAdminResult(true, "Governorate admin deleted successfully", AdminType.GOVERNORATE_ADMIN);

            } else if (adminType == 2000) {
                Optional<MasterAdmin> existingAdmin = masterAdminRepository.findById(adminId);
                if (!existingAdmin.isPresent()) {
                    return new DeleteAdminResult(false, "Error, Master admin not found", AdminType.MASTER_ADMIN);
                }

                // You might want to add a check to prevent deleting the last master admin
                long masterAdminCount = masterAdminRepository.count();
                if (masterAdminCount <= 1) {
                    return new DeleteAdminResult(false, "Cannot delete the last master admin", AdminType.MASTER_ADMIN);
                }

                masterAdminRepository.deleteById(adminId);
                return new DeleteAdminResult(true, "Master admin deleted successfully", AdminType.MASTER_ADMIN);
            }

            return new DeleteAdminResult(false, "Specified admin type not found");
        } catch (Exception e) {
            return new DeleteAdminResult(false, "Error deleting admin: " + e.getMessage());
        }
    }
}