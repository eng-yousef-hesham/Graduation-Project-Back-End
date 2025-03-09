package org.gp.civiceye.mapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAdminDTO {
    private String nationalId;
    private String firstName;
    private String lastName;
    private String email;
    private long cityId;
    private long governorateId;
    private String HashPassword;
    private int type;

}
