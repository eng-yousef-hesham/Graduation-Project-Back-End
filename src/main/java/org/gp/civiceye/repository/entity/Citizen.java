package org.gp.civiceye.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "citizen")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Citizen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "Citizen_ID")
    private Long citizenId;

    @Column(unique = true, nullable = false, name = "National_ID")
    private String nationalId;

    @Column(nullable = false, name = "First_Name")
    private String firstName;

    @Column(nullable = false, name = "Last_Name")
    private String lastName;

    @Column(unique = true, nullable = false, name = "Email")
    private String email;

    @Column(nullable = false, name = "Password_Hash")
    private String passwordHash;

    @Column(name = "Age")
    private Integer age;


    @Column(name = "Created_At", updatable = false, insertable = false)
    private LocalDateTime createdAt;

    @Column(name = "Is_Active")
    private Boolean isActive;

    @OneToMany(mappedBy = "citizen", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Report> reports = new ArrayList<>();


}

