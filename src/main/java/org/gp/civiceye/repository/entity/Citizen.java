package org.gp.civiceye.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name="citizen")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Citizen {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long citizenId;

    @Column(nullable = false)
    private String nationalId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private char[] passwordHash = new char[60];

    private Integer age;

    private Integer cityId;

    private LocalDateTime createdAt;

    private  LocalDateTime updatedAt;

    private LocalDateTime lastLogin;

    private boolean isActive = true;

    @OneToMany(mappedBy = "changedByCitizen")
    private List<StatusHistory> statusHistory;


    @OneToMany(mappedBy = "citizen")
    private List<Report> citizen;

    @ManyToOne
    @JoinColumn(name = "City_ID")
    private City city;

}

