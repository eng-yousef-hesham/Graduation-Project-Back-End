package org.gp.civiceye.mapper;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AdminDeleteDTO {
    @NotNull
    private Long adminId;
}