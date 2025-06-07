package org.gp.civiceye.repository;

import org.gp.civiceye.repository.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportAnalysisRepository extends JpaRepository<Report, Long> {

    // Count Reports Per Department
    @Query("SELECT r.department, COUNT(r) FROM Report r GROUP BY r.department ORDER BY COUNT(r) DESC")
    List<Object[]> countReportsByDepartment();


    // Count Reports Assigned to Each Employee
    @Query("SELECT r.assignedEmployee.empId, r.assignedEmployee.firstName, r.assignedEmployee.lastName, COUNT(r) FROM Report r WHERE r.assignedEmployee IS NOT NULL " +
            "GROUP BY r.assignedEmployee.empId, r.assignedEmployee.firstName, r.assignedEmployee.lastName ORDER BY COUNT(r) DESC")
    List<Object[]> countReportsPerEmployee();

    // Count Reports Per City
    @Query("SELECT r.city.cityId, r.city.name, COUNT(r) FROM Report r GROUP BY r.city.cityId, r.city.name ORDER BY COUNT(r) DESC")
    List<Object[]> countReportsPerCity();

    @Query("SELECT r.city.cityId, r.city.name, COUNT(r) " +
            "FROM Report r " +
            "WHERE r.city.governorate.governorateId = :govId " +
            "GROUP BY r.city.cityId, r.city.name " +
            "ORDER BY COUNT(r) DESC")
    List<Object[]> countReportsPerCityByGovernorate(@Param("govId") Long governorateId);

}
