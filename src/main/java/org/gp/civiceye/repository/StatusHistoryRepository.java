package org.gp.civiceye.repository;

import org.gp.civiceye.repository.entity.Report;
import org.gp.civiceye.repository.entity.StatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusHistoryRepository extends JpaRepository<StatusHistory, Long> {
    Optional<StatusHistory> findTopByReportOrderByStartTimeDesc(Report report);


}