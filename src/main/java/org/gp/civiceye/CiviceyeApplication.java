package org.gp.civiceye;

import com.github.javafaker.Faker;
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



//    @Bean
//    public CommandLineRunner commandLineRunner(
//            CitizenRepository citizenRepository,
//            PasswordEncoder passwordEncoder
//
//    ) {
//        return args -> {
//            for (int i = 0; i < 100; i++) {
//                Faker faker = new Faker();
//                String password = faker.internet().password(10,20).toString();
//                String hashpassword =  passwordEncoder.encode(password);
//                Citizen citizen = Citizen.builder().nationalId(faker.idNumber().valid())
//                        .firstName(faker.name().firstName())
//                        .lastName(faker.name().lastName())
//                        .age(faker.number().numberBetween(20, 80))
//                        .email(faker.name().username() + "@civiceye.com")
//                        .isActive(true)
//                        .passwordHash(hashpassword)
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
