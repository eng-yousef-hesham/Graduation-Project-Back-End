package org.gp.civiceye.repository;

import org.gp.civiceye.repository.entity.Department;
import org.gp.civiceye.repository.entity.Report;
import org.gp.civiceye.repository.entity.ReportStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ReportAnalysisRepository extends JpaRepository<Report, Long> {

    // Count Reports Per Department
    @Query("SELECT r.department, COUNT(r) FROM Report r GROUP BY r.department ORDER BY COUNT(r) DESC")
    List<Object[]> countReportsByDepartment();


    // Count Reports Assigned to Each Employee
    @Query("SELECT r.assignedEmployee.empId, r.assignedEmployee.firstName, r.assignedEmployee.lastName, COUNT(r) FROM Report r WHERE r.assignedEmployee IS NOT NULL " +
            "GROUP BY r.assignedEmployee.empId, r.assignedEmployee.firstName, r.assignedEmployee.lastName ORDER BY COUNT(r) DESC")
    List<Object[]> countReportsPerEmployee();

    @Query("SELECT COUNT(r) FROM Report r")
    Long findCountOfAllReports();
    // Count Reports Per City
    @Query("SELECT  COUNT(r) FROM Report r WHERE r.city.cityId = :CityId")
    Long countReportsPerCity(@Param("CityId") Long CityId);

    @Query("SELECT r.city.cityId, r.city.name, COUNT(r) " +
            "FROM Report r " +
            "WHERE r.city.governorate.governorateId = :govId " +
            "GROUP BY r.city.cityId, r.city.name " +
            "ORDER BY COUNT(r) DESC")
    List<Object[]> countReportsPerCityByGovernorate(@Param("govId") Long governorateId);


    @Query("SELECT COUNT(r) " +
            "FROM Report r " +
            "WHERE r.city.governorate.governorateId = :govId")
    Long countReportsInGovernorate(@Param("govId") Long governorateId);

    @Query("SELECT COUNT(r) FROM Report r WHERE r.currentStatus = 'In_Progress' and r.city.cityId = :CityId")
    Long countReportsPerCityInProgress(@Param("CityId") Long CityId);

    @Query("SELECT COUNT(r) FROM Report r WHERE r.currentStatus = 'In_Progress' and r.city.governorate.governorateId = :govId")
    Long countReportsPerGovernorateInProgress(@Param("govId") Long govId);

    @Query("SELECT COUNT(r) FROM Report r WHERE r.currentStatus = 'Resolved' and r.city.cityId = :CityId")
    Long countReportsPerCityResolved(@Param("CityId") Long CityId);

    @Query("SELECT COUNT(r) FROM Report r WHERE r.currentStatus = 'Resolved' and r.city.governorate.governorateId = :govId")
    Long countReportsPerGovernorateResolved(@Param("govId") Long govId);

    List<Report> findTop4ByOrderByCreatedAtDesc();

    @Query("SELECT r FROM Report r WHERE r.city.governorate.governorateId = :govId ORDER BY r.createdAt DESC limit 4")
    List<Report> findTop4ByGovernorateId(Long govId);

    @Query("SELECT r FROM Report r WHERE r.city.cityId = :cityId ORDER BY r.createdAt DESC limit 4")
    List<Report> findTop4ByCityId(Long cityId);


    @Query("SELECT r.department, COUNT(r) FROM Report r " +
            "GROUP BY r.department " +
            "ORDER BY COUNT(r) DESC")
    List<Object[]> findCountOfReportsPerDepartment();

    @Query("SELECT r.department, COUNT(r) FROM Report r " +
            "WHERE r.city.governorate.governorateId = :govId " +
            "GROUP BY r.department " +
            "ORDER BY COUNT(r) DESC")
    List<Object[]> findCountOfReportsPerDepartmentPerGovernorate(@Param("govId") Long govId);

    @Query("SELECT r.department, COUNT(r) FROM Report r " +
            "WHERE r.city.cityId = :cityId " +
            "GROUP BY r.department " +
            "ORDER BY COUNT(r) DESC")
    List<Object[]> findCountOfReportsPerDepartmentPerCity(@Param("cityId") Long cityId);


    @Query("SELECT r.assignedEmployee.firstName, r.assignedEmployee.lastName, " +
            "CAST(sum(r.updatedAt - r.createdAt) AS long) FROM Report r WHERE r.currentStatus = 'Resolved' " +
            "GROUP BY r.assignedEmployee " +
            "ORDER BY CAST(sum(r.updatedAt - r.createdAt) AS long) asc LIMIT 8")
    List<Object[]> find8FastestEmployeeToSolveReports();

    @Query("SELECT r.assignedEmployee.firstName, r.assignedEmployee.lastName, " +
            "CAST(sum(r.updatedAt - r.createdAt) AS long) FROM Report r WHERE r.currentStatus = 'Resolved' AND " +
            "r.city.governorate.governorateId = :govId " +
            "GROUP BY r.assignedEmployee " +
            "ORDER BY CAST(sum(r.updatedAt - r.createdAt) AS long) asc LIMIT 8")
    List<Object[]> find8FastestEmployeeToSolveReportsPerGovernorate(@Param("govId") Long govId);

    @Query("SELECT r.assignedEmployee.firstName, r.assignedEmployee.lastName, " +
            "CAST(sum(r.updatedAt - r.createdAt) AS long) FROM Report r WHERE r.currentStatus = 'Resolved' AND " +
            "r.city.cityId = :cityId " +
            "GROUP BY r.assignedEmployee " +
            "ORDER BY CAST(sum(r.updatedAt - r.createdAt) AS long) asc LIMIT 8")
    List<Object[]> find8FastestEmployeeToSolveReportsPerCity(@Param("cityId") Long cityId);

}
