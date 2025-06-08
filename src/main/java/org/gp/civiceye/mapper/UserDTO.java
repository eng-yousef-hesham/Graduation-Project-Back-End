package org.gp.civiceye.mapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gp.civiceye.repository.entity.GovernorateAdmin;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long Id;
    private String nationalId;
    private String fullName;
    private String firstName;
    private String lastName;
    private String email;
    private String governorateName;
    private String cityName;
    private String department;
    private List<String> level;
    private int type;
    private Long cityId;
    private Long governorateId;


}
