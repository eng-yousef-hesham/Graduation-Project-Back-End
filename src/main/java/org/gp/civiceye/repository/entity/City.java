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
@Table(name = "City")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "City_ID")
    private Integer cityId;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Created_At")
    private LocalDateTime createdAt;

    @Column(name = "Updated_At")
    private LocalDateTime updatedAt;

    @Column(name = "Is_Active")
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "Governorate_ID", nullable = false)
    private Governorate governorate;

    @OneToMany(mappedBy = "city")
    private List<CityAdmin> employees;

    @OneToMany(mappedBy = "city")
    private List<Citizen> citizens;

    @OneToMany(mappedBy = "city")
    private List<Report> reports;

    @OneToMany(mappedBy = "city")
    private List<Employee> Employees;
}

