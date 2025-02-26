package org.gp.civiceye.repository;

import org.gp.civiceye.mapper.ReportDTO;
import org.gp.civiceye.repository.entity.Employee;
import org.gp.civiceye.repository.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    Collection<Object> findByAssignedEmployee(Employee assignedEmployee);

    List<ReportDTO> findAllByAssignedEmployee(Employee assignedEmployee);
}