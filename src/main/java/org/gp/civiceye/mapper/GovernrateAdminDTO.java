package org.gp.civiceye.mapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gp.civiceye.repository.entity.GovernrateAdmin;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GovernrateAdminDTO {
    private Long adminId;
    private String nationalId;
    private String fullName;
    private String firstName;
    private String lastName;
    private String email;
    private String governorateName;
    private String level = "Governrate Admin";

    public GovernrateAdminDTO(GovernrateAdmin governrateAdmin) {
        this.adminId = governrateAdmin.getAdminId();
        this.nationalId = governrateAdmin.getNationalId();
        this.fullName = governrateAdmin.getFirstName() + " " + governrateAdmin.getLastName();
        this.firstName = governrateAdmin.getFirstName();
        this.lastName = governrateAdmin.getLastName();
        this.email = governrateAdmin.getEmail();
        this.governorateName = governrateAdmin.getGovernorate().getName();
    }
}
