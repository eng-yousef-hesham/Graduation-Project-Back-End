package org.gp.civiceye.service.impl;

import org.gp.civiceye.exception.CitizenAlreadyExistsException;
import org.gp.civiceye.mapper.citizen.CreateCitizenDTO;
import org.gp.civiceye.repository.CitizenRepository;
import org.gp.civiceye.repository.entity.*;
import org.gp.civiceye.service.CitizenService;
import org.gp.civiceye.mapper.citizen.CitizenDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CitizenServiceImpl implements CitizenService {

    private final CitizenRepository citizenRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CitizenServiceImpl(CitizenRepository citizenrepository, PasswordEncoder passwordEncoder) {
        this.citizenRepository = citizenrepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<CitizenDTO> getAllCitizens() {
        return citizenRepository.findAll().stream()
                .map(CitizenDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public Long addCitizen(CreateCitizenDTO citizenData) {
        String password = citizenData.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        citizenData.setPassword(encodedPassword);

        citizenRepository.findByEmailOrNationalId(citizenData.getEmail(),
                citizenData.getNationalId())
                .ifPresent(citizen -> {
            throw new CitizenAlreadyExistsException(citizen.getEmail(), citizen.getNationalId());
        });

        Citizen citizen = Citizen.builder()
                .nationalId(citizenData.getNationalId())
                .firstName(citizenData.getFirstName())
                .lastName(citizenData.getLastName())
                .email(citizenData.getEmail())
                .passwordHash(citizenData.getPassword())
                .age(citizenData.getAge())
                .isActive(true)
                .spamCount(0)
                .build();

        Citizen savedCitizen = citizenRepository.save(citizen);

        return savedCitizen.getCitizenId();
    }
}
