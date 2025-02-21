package org.gp.civiceye.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "City", uniqueConstraints = @UniqueConstraint(columnNames = {"Name", "Governorate_ID"}))
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "City_ID")
    private Long cityId;

    @Column(name = "Name", nullable = false, length = 100)
    private String name;

    @CreationTimestamp
    @Column(name = "Created_At")
    private LocalDateTime createdAt;

    @Column(name = "Is_Active",columnDefinition = "boolean default true")
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "Governorate_ID", nullable = false)
    private Governorate governorate;

    @OneToMany(mappedBy = "city", fetch = FetchType.LAZY)
    private List<CityAdmin> cityAdmins=new ArrayList<>();

    @OneToMany(mappedBy = "city", fetch = FetchType.LAZY)
    private List<Report> reports=new ArrayList<>();

    @OneToMany(mappedBy = "city", fetch = FetchType.LAZY)
    private List<Employee> employees =new ArrayList<>();
}

