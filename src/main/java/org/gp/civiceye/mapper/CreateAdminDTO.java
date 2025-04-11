package org.gp.civiceye.mapper;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAdminDTO {
    private String nationalId;

    @NotBlank(message = "Firstname is required")
    @Size(min = 2, max = 20, message = "First must be between 2 and 20 characters")
    private String firstName;

    @NotBlank(message = "Lastname is required")
    @Size(min = 2, max = 20, message = "Lastname must be between 2 and 20 characters")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    private long cityId;
    private long governorateId;
    private String HashPassword;
    private int type;

}
