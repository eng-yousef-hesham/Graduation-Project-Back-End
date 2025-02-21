package org.gp.civiceye.dto;

import lombok.*;
import org.gp.civiceye.repository.entity.City;
import org.gp.civiceye.repository.entity.Governorate;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CityDTO {
    private Long cityId;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isActive;
    private Integer governorateId;

    public CityDTO(City city) {
        this.cityId = city.getCityId();
        this.name = city.getName();
        this.createdAt = city.getCreatedAt();
//        this.updatedAt = city.getUpdatedAt();
        this.isActive = city.getIsActive();
//        this.governorateId = city.getGovernorate().getGovernorateId();
    }



}
