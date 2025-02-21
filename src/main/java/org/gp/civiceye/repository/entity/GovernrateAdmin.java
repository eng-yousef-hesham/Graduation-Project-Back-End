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
@Table(name = "Governrate_Admin")
public class GovernrateAdmin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Admin_ID")
    private Long adminId;

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
    @JoinColumn(name = "Governorate_ID", nullable = false)
    private Governorate governorate;

    @CreationTimestamp
    @Column(name = "Created_At")
    private LocalDateTime createdAt;

    @Column(name = "Last_Login")
    private LocalDateTime lastLogin;


    @Column(name = "Is_Active",columnDefinition = "boolean default true")
    private Boolean isActive;
}
