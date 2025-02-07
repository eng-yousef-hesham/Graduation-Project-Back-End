package org.gp.civiceye.repository.entity;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Governorate")
public class Governorate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Governorate_ID")
    private Integer governorateId;

    @Column(name = "Name", nullable = false, unique = true)
    private String name;

    @Column(name = "Created_At")
    private LocalDateTime createdAt;

    @Column(name = "Updated_At")
    private LocalDateTime updatedAt;

    @Column(name = "Is_Active")
    private Boolean isActive;

    @OneToMany(mappedBy = "governorate")
    private List<City> cities;
}