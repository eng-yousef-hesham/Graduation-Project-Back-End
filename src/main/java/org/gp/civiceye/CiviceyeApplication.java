package org.gp.civiceye;

import com.github.javafaker.Faker;
import org.gp.civiceye.repository.CitizenRepository;
import org.gp.civiceye.repository.CityRepository;
import org.gp.civiceye.repository.GovernorateRepository;
import org.gp.civiceye.repository.entity.Citizen;
import org.gp.civiceye.repository.entity.City;
import org.gp.civiceye.repository.entity.Governorate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class CiviceyeApplication {

    public static void main(String[] args) {
        SpringApplication.run(CiviceyeApplication.class, args);
    }



//    @Bean
//    public CommandLineRunner commandLineRunner(
//            CityRepository cityRepository,
//            CitizenRepository citizenRepository
//    ) {
//        return args -> {
//            for (int i = 0; i < 100; i++) {
//                Faker faker = new Faker();
//                Citizen citizen = Citizen.builder().nationalId(faker.idNumber().valid())
//                        .firstName(faker.name().firstName())
//                        .lastName(faker.name().lastName())
//                        .age(faker.number().numberBetween(20, 80))
//                        .email(faker.name().username() + "@civiceye.com")
//                        .city(cityRepository.findByCityId(1))
//                        .isActive(true)
//                        .passwordHash(faker.internet().password(59,60).toCharArray())
//                        .build();
//                citizenRepository.save(citizen);
//            }
//        };
//    }


//    @Bean
//    public CommandLineRunner commandLineRunner(
//            GovernorateRepository governorateRepository
//            , CityRepository cityRepository
//            ) {
//        return (args) -> {
//            Governorate governorate = Governorate.builder().name("alex").isActive(true).cities(null)
//                    .createdAt(null).updatedAt(null).build();
//            governorateRepository.save(governorate);
//
//            City city = City.builder().governorate(governorate).createdAt(null).updatedAt(null).isActive(true).name("benha").Employees(null).build();
//
//            cityRepository.save(city);
//
//        };
//    }


}
