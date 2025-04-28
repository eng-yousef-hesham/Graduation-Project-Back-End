package org.gp.civiceye.mapper.governorate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.gp.civiceye.repository.entity.Governorate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GovernorateDTO {
    private Long governorateId;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isActive;
//    private List<CityDTO> cities;

    public GovernorateDTO(Governorate governorate) {
        this.governorateId = governorate.getGovernorateId();
        this.name = governorate.getName();
        this.createdAt = governorate.getCreatedAt();
        this.isActive = governorate.getIsActive();
//        this.cities = governorate.getCities().stream().map(CityDTO::new).toList();
    }


}
