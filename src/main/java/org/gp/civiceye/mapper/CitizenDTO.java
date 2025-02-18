package org.gp.civiceye.mapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gp.civiceye.repository.entity.Citizen;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CitizenDTO {
    private Long citizenId;
    private String nationalId;
    private String firstName;
    private String lastName;
    private String email;
    private Integer age;
    private String cityName;
    private String governorateName;

    public CitizenDTO(Citizen citizen) {
        this.citizenId = citizen.getCitizenId();
        this.nationalId = citizen.getNationalId();
        this.firstName = citizen.getFirstName();
        this.lastName = citizen.getLastName();
        this.email = citizen.getEmail();
        this.age = citizen.getAge();
        this.cityName = citizen.getCity().getName();
        this.governorateName = citizen.getCity().getGovernorate().getName();
    }
}
