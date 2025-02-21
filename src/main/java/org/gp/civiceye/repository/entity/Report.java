package org.gp.civiceye.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Report")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Report_ID")
    private Long reportId;

    @Column(name = "Title", nullable = false)
    private String title;

    @Column(name = "Description", nullable = false)
    private String description;

    @Column(name = "Latitude")
    private Float latitude;

    @Column(name = "Longitude")
    private Float longitude;

    @Column(name = "Contact_Info", nullable = false)
    private String contactInfo;

    @Enumerated(EnumType.STRING)
    @Column(name = "Department", nullable = false)
    private Department department;

    @Column(name = "Created_At")
    private LocalDateTime createdAt;

    @Column(name = "Updated_At")
    private LocalDateTime updatedAt;

    @Column(name = "Expected_Resolution_Date")
    private LocalDate expectedResolutionDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "Current_Status", nullable = false)
    @Builder.Default
    private ReportStatus currentStatus = ReportStatus.Submitted;

    @ManyToOne
    @JoinColumn(name = "Citizen_ID", nullable = false)
    private Citizen citizen;

    @ManyToOne
    @JoinColumn(name = "City_ID", nullable = false)
    private City city;

    @ManyToOne
    @JoinColumn(name = "Assigned_EMP_ID")
    private Employee assignedEmployee;

    @OneToMany(mappedBy = "report", cascade = CascadeType.ALL)
    private List<StatusHistory> statusHistoryList = new ArrayList<>();

}