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
@Table(name = "Employee")
//@PrimaryKeyJoinColumn(name = "EMP_ID")
public class Employee  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMP_ID")
    private Integer empId;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "Department", nullable = false)
    private Department department;

    @Enumerated(EnumType.STRING)
    @Column(name = "Role", nullable = false)
    private Role role = Role.Staff;

    @ManyToOne
    @JoinColumn(name = "City_ID")
    private City city;

    @OneToMany(mappedBy = "assignedEmployee")
    private List<Report> assignedReports;

    @OneToMany(mappedBy = "changedByEmployee")
    private List<StatusHistory> StatusHistory;


    public enum Role {
        Staff, Head
    }
}
