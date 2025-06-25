package org.gp.civiceye.repository;

import aj.org.objectweb.asm.commons.Remapper;
import org.gp.civiceye.repository.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    Collection<Object> findByAssignedEmployee(Employee assignedEmployee);

    List<Report> findAllByAssignedEmployee_EmpId(Long employeeId);
    List<Report> findAllByAssignedEmployee_Email(String email);

    List<Report> findAllByCitizen_CitizenId(Long userId);
    List<Report> findAllByCitizen_Email(String email);

    Page<Report> findByCurrentStatus(ReportStatus currentStatus, Pageable pageable);

    Page<Report> findByCurrentStatusAndCity(ReportStatus currentStatus, City city, Pageable pageable);

    Page<Report> findByCurrentStatusAndCity_Governorate(ReportStatus currentStatus, Governorate governorate, Pageable pageable);

    Page<Report> findByCity(City city, Pageable pageable);

    Page<Report> findByCity_Governorate(Governorate governorate, Pageable pageable);

    Integer countByCity_Governorate(Governorate governorate);

    List<Report> findAllByAssignedEmployee(Employee employee);

    Page<Report> findByDepartment(Department department, Pageable pageable);

    Page<Report> findByDepartmentAndCity_Governorate(Department department, Governorate governorate, Pageable pageable);

    Page<Report> findByDepartmentAndCity(Department department, City city, Pageable pageable);

    Page<Report> findByCurrentStatusAndDepartment(ReportStatus status, Department department, Pageable pageable);

    Page<Report> findByCurrentStatusAndDepartmentAndCity(ReportStatus status, Department department, City city, Pageable pageable);

    Page<Report> findByCurrentStatusAndDepartmentAndCity_Governorate(ReportStatus status, Department department, Governorate governorate, Pageable pageable);

    int countByCity_Governorate_GovernorateId(Long govId);
    int countByCurrentStatusAndAssignedEmployee_EmpId (ReportStatus status, Long employeeId);
    @Query("SELECT r FROM Report r WHERE r.currentStatus NOT IN :statuses")
    List<Report> findByCurrentStatusNotIn(@Param("statuses") List<ReportStatus> statuses);
}