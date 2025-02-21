package org.gp.civiceye.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMP_ID")
    private Integer empId;

    @Column(name = "National_ID", nullable = false, unique = true, length = 14)
    private String nationalId;

    @Column(name = "First_Name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "Last_Name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "Email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "Password_Hash", nullable = false, length = 60)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(name = "Department", nullable = false)
    private Department department;

    @ManyToOne
    @JoinColumn(name = "City_ID")
    private City city;

    @Column(name = "Created_At", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "Is_Active", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean isActive;

    @OneToMany(mappedBy = "assignedEmployee")
    private List<Report> assignedReports = new ArrayList<>();

}
