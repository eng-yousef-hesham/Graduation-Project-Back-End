package org.gp.civiceye.mapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gp.civiceye.repository.entity.Citizen;
import org.gp.civiceye.repository.entity.CityAdmin;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityAdminDTO {
    private Long adminId;
    private String nationalId;
    private String fullName;
    private String firstName;
    private String lastName;
    private String email;
    private String cityName;
    private String governorateName;
    private String level = "City Admin";

    public CityAdminDTO(CityAdmin cityAdmin) {
        this.adminId = cityAdmin.getAdminId();
        this.nationalId = cityAdmin.getNationalId();
        this.fullName = cityAdmin.getFirstName() + " " + cityAdmin.getLastName();
        this.firstName = cityAdmin.getFirstName();
        this.lastName = cityAdmin.getLastName();
        this.email = cityAdmin.getEmail();
        this.cityName = cityAdmin.getCity().getName();
        this.governorateName = cityAdmin.getCity().getGovernorate().getName();
    }


}
