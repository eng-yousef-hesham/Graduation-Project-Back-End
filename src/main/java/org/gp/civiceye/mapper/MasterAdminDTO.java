package org.gp.civiceye.mapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gp.civiceye.repository.entity.MasterAdmin;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MasterAdminDTO {
    private Long adminId;
    private String nationalId;
    private String fullName;
    private String firstName;
    private String lastName;
    private String email;
    private String level = "Master Admin";


    public MasterAdminDTO(MasterAdmin masterAdmin) {
        this.adminId = masterAdmin.getAdminId();
        this.nationalId = masterAdmin.getNationalId();
        this.fullName = masterAdmin.getFirstName() + " " + masterAdmin.getLastName();
        this.firstName = masterAdmin.getFirstName();
        this.lastName = masterAdmin.getLastName();
        this.email = masterAdmin.getEmail();
    }
}
