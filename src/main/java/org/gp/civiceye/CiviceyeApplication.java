package org.gp.civiceye;


import org.gp.civiceye.repository.CitizenRepository;
import org.gp.civiceye.repository.CityRepository;
import org.gp.civiceye.repository.entity.Citizen;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class CiviceyeApplication {
    public static void main(String[] args) {
        SpringApplication.run(CiviceyeApplication.class, args);
    }

}
