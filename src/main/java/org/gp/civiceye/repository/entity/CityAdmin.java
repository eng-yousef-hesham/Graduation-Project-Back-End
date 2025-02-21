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
@Table(name = "City_Admin")
public class CityAdmin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Admin_ID")
    private Integer adminId;

    @Column(name = "National_ID", nullable = false, unique = true)
    private String nationalId;

    @Column(name = "First_Name", nullable = false)
    private String firstName;

    @Column(name = "Last_Name", nullable = false)
    private String lastName;

    @Column(name = "Email", nullable = false, unique = true)
    private String email;

    @Column(name = "Password_Hash", nullable = false)
    private String passwordHash;

    @ManyToOne
    @JoinColumn(name = "City_ID", nullable = false)
    private City city;

    @Column(name = "Created_At", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "Last_Login")
    private LocalDateTime lastLogin;

    @Column(name = "Is_Active", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean isActive;



}

