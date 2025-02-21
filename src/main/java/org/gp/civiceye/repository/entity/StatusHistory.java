package org.gp.civiceye.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Status_History")
public class StatusHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Status_ID")
    private Integer statusId;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false)
    private ReportStatus status;

    @Column(name = "Start_Time")
    private LocalDateTime startTime;

    @Column(name = "End_Time" )
    private LocalDateTime endTime;

    @ManyToOne
    @JoinColumn(name = "Report_ID", nullable = false)
    private Report report;

    @ManyToOne
    @JoinColumn(name = "changed_By", nullable = false)
    private Employee changedByEmployee;

    @Column(name = "Notes")
    private String notes;
}
