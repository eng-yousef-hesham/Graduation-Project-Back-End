package org.gp.civiceye.mapper.citizen;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gp.civiceye.repository.entity.Citizen;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCitizenDTO {
    private String nationalId;
    private String firstName;
    private String lastName;
    private String HashPassword;
    private String email;
    private Integer age;

}
