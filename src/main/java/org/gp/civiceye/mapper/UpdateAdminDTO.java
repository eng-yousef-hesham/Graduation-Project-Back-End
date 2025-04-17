package org.gp.civiceye.mapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAdminDTO {
    private Long adminId;          // ID of the admin to update
    private Integer type;          // Admin type (1998 = city, 1999 = governorate, 2000 = master)
    private String firstName;
    private String lastName;
    private String email;
    private String nationalId;
    private String hashPassword;   // New password (optional)
    private Long cityId;           // For city admin (optional)
    private Long governorateId;    // For governorate admin (optional)
}