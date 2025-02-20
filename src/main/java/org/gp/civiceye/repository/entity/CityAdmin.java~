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
//@PrimaryKeyJoinColumn(name = "Admin_ID")
public class CityAdmin  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Admin_ID")
    private Integer AdminId;

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

    @Column(name = "Created_At")
    private LocalDateTime createdAt;

    @Column(name = "Updated_At")
    private LocalDateTime updatedAt;

    @Column(name = "Last_Login")
    private LocalDateTime lastLogin;

    @Column(name = "Is_Active")
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "City_ID", nullable = false)
    private City city;

//    @OneToMany(mappedBy = "changedByCityAdmin")
//    private List<StatusHistory> StatusHistory;
}
