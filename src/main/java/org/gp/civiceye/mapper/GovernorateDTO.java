package org.gp.civiceye.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.gp.civiceye.dto.CityDTO;
import org.gp.civiceye.repository.entity.Governorate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GovernorateDTO {
    private Integer governorateId;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isActive;
    private List<CityDTO> cities;

    public GovernorateDTO(Optional<Governorate> governorate) {
        this.governorateId = governorate.get().getGovernorateId();
        this.name = governorate.get().getName();
        this.createdAt = governorate.get().getCreatedAt();
        this.updatedAt = governorate.get().getUpdatedAt();
        this.isActive = governorate.get().getIsActive();
        this.cities = governorate.get().getCities().stream().map(CityDTO::new).toList();
    }


}
