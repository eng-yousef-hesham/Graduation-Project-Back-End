package org.gp.civiceye.mapper;

import jakarta.validation.constraints.NotNull;

public class AdminDeleteDTO {
    @NotNull
    private Long adminId;

    @NotNull
    private Integer adminType;

    // Getters and setters
    public Long getAdminId() { return adminId; }
    public void setAdminId(Long adminId) { this.adminId = adminId; }

    public Integer getAdminType() { return adminType; }
    public void setAdminType(Integer adminType) { this.adminType = adminType; }
}