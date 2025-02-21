package org.gp.civiceye.mapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gp.civiceye.repository.entity.City;
import org.gp.civiceye.repository.entity.CityAdmin;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCityAdminDTO {
    private Integer adminId;
    private String nationalId;
    private String firstName;
    private String lastName;
    private String email;
    private Integer cityId;
    private String password;
    private LocalDateTime createdAt;


    public CreateCityAdminDTO(CityAdmin cityAdmin) {
        this.adminId = cityAdmin.getAdminId();
        this.nationalId = cityAdmin.getNationalId();
        this.firstName = cityAdmin.getFirstName();
        this.lastName = cityAdmin.getLastName();
        this.email = cityAdmin.getEmail();
        this.cityId = cityAdmin.getCity().getCityId();
        this.password = cityAdmin.getPasswordHash();

    }

    public CityAdmin toCityAdmin(City city) {

        return CityAdmin.builder()
                .adminId(adminId)
                .nationalId(nationalId)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .city(city)
                .passwordHash(password)
                .createdAt(createdAt)
                .build();
    }

}
