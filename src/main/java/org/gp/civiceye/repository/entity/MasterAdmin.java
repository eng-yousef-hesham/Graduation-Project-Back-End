package org.gp.civiceye.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Master_Admin")
public class MasterAdmin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Master_ID")
    private Long userId;

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

    @CreationTimestamp
    @Column(name = "Created_At" ,updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "Updated_At")
    private LocalDateTime updatedAt;

    @Column(name = "Last_Login")
    private LocalDateTime lastLogin;

    @Column(name = "Is_Active", columnDefinition = "boolean default true")
    private Boolean isActive;

    // No additional fields for Master Admin
}
