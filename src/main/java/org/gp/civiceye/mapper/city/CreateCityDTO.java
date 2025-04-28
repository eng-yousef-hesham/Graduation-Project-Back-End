package org.gp.civiceye.mapper.city;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gp.civiceye.repository.entity.City;
import org.gp.civiceye.repository.entity.Governorate;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCityDTO {
    private Long cityId;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isActive;
    private Long governorateId;

    public CreateCityDTO(City city) {
        this.cityId = city.getCityId();
        this.name = city.getName();
        this.createdAt = city.getCreatedAt();
        this.isActive = city.getIsActive();
        this.governorateId = city.getGovernorate().getGovernorateId();
    }




}
