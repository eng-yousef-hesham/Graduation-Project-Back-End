package org.gp.civiceye.repository;

import aj.org.objectweb.asm.commons.Remapper;
import org.gp.civiceye.repository.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    Collection<Object> findByAssignedEmployee(Employee assignedEmployee);

    List<Report> findAllByAssignedEmployee_EmpId(Long employeeId);

    List<Report> findAllByCitizen_CitizenId(Long userId);

    Page<Report> findByCurrentStatus(ReportStatus currentStatus, Pageable pageable);

    Integer countByCity_Governorate(Governorate governorate);

    List<Report> findAllByAssignedEmployee(Employee employee);
    Page<Report> findByDepartment(Department department, Pageable pageable);
    Page<Report> findByCurrentStatusAndDepartment( ReportStatus status, Department department, Pageable pageable);

}