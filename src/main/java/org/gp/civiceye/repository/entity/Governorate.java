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
@Table(name = "Governorate")
public class Governorate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Governorate_ID")
    private Long governorateId;

    @Column(name = "Name", unique = true, nullable = false)
    private String name;

    @CreationTimestamp
    @Column(name = "Created_At", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "Is_Active", columnDefinition = "boolean default true")
    private Boolean isActive = true;

    @OneToMany(mappedBy = "governorate", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<City> cities = new ArrayList<>();



}