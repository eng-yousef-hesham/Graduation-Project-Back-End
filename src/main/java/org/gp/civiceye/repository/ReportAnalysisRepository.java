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

    @Query("SELECT r.department, COUNT(r) " +
            "FROM Report r " +
            "WHERE r.city.cityId = :cityId " +
            "GROUP BY r.department " +
            "ORDER BY COUNT(r) DESC")
    List<Object[]> findCountOfReportsPerDepartmentPerCity(@Param("cityId") Long cityId);


    @Query(value = "SELECT e.first_name, e.last_name, " +
            "AVG(EXTRACT(EPOCH FROM (r.updated_at - r.created_at)) / 3600)::float8 AS avg_hours " +
            "FROM report r " +
            "JOIN employee e ON r.assigned_emp_id = e.emp_id " +
            "JOIN city c ON r.city_id = c.city_id " +
            "WHERE r.current_status = 'Resolved' " +
            "GROUP BY e.emp_id, e.first_name, e.last_name " +
            "ORDER BY avg_hours ASC " +
            "LIMIT 8", nativeQuery = true)
    List<Object[]> find8FastestEmployeeToSolveReports();

    @Query(value = "SELECT e.first_name, e.last_name, " +
            "AVG(EXTRACT(EPOCH FROM (r.updated_at - r.created_at)) / 3600)::float8 AS avg_hours " +
            "FROM report r " +
            "JOIN employee e ON r.assigned_emp_id = e.emp_id " +
            "JOIN city c ON r.city_id = c.city_id " +
            "WHERE r.current_status = 'Resolved' AND c.governorate_id = :govId " +
            "GROUP BY e.emp_id, e.first_name, e.last_name " +
            "ORDER BY avg_hours ASC " +
            "LIMIT 8", nativeQuery = true)
    List<Object[]> find8FastestEmployeeToSolveReportsPerGovernorate(@Param("govId") Long govId);

    @Query(value = "SELECT e.first_name, e.last_name, " +
            "AVG(EXTRACT(EPOCH FROM (r.updated_at - r.created_at)) / 3600)::float8 AS avg_hours " +
            "FROM report r " +
            "JOIN employee e ON r.assigned_emp_id = e.emp_id " +
            "JOIN city c ON r.city_id = c.city_id " +
            "WHERE r.current_status = 'Resolved' AND c.city_id = :cityId " +
            "GROUP BY e.emp_id, e.first_name, e.last_name " +
            "ORDER BY avg_hours ASC " +
            "LIMIT 8", nativeQuery = true)
    List<Object[]> find8FastestEmployeeToSolveReportsPerCity(@Param("cityId") Long cityId);


    @Query(value = "SELECT c.city_id, c.name, " +
            "AVG(EXTRACT(EPOCH FROM (r.updated_at - r.created_at)) / 3600)::float8 AS avg_hours " +
            "FROM report r " +
            "JOIN city c ON r.city_id = c.city_id " +
            "WHERE r.current_status = 'Resolved' " +
            "GROUP BY c.city_id, c.name " +
            "ORDER BY avg_hours ASC", nativeQuery = true)
    List<Object[]> findAverageTimeToSolveReportInCities();

    @Query(value = "SELECT c.city_id, c.name, " +
            "AVG(EXTRACT(EPOCH FROM (r.updated_at - r.created_at)) / 3600)::float8 AS avg_hours " +
            "FROM report r " +
            "JOIN city c ON r.city_id = c.city_id " +
            "WHERE r.current_status = 'Resolved' AND c.city_id = :cityId " +
            "GROUP BY c.city_id, c.name " +
            "ORDER BY avg_hours ASC", nativeQuery = true)
    List<Object[]> findAverageTimeToSolveReportInCity(Long cityId);

    @Query(value = "SELECT c.city_id, c.name, " +
            "AVG(EXTRACT(EPOCH FROM (r.updated_at - r.created_at)) / 3600)::float8 AS avg_hours " +
            "FROM report r " +
            "JOIN city c ON r.city_id = c.city_id " +
            "WHERE r.current_status = 'Resolved' AND c.governorate_id = :govId " +
            "GROUP BY c.city_id, c.name " +
            "ORDER BY avg_hours ASC", nativeQuery = true)
    List<Object[]> findAverageTimeToSolveReportInCitiesPerGovernorate(Long govId);

    @Query(value = "SELECT DATE_TRUNC('day', r.created_at) AS day,\n" +
            "COUNT(*) AS count\n" +
            "FROM report r\n" +
            "where created_at >= now() - INTERVAL '2 month'\n" +
            "GROUP BY DATE_TRUNC('day', r.created_at)\n" +
            "ORDER BY day ASC;", nativeQuery = true)
    List<Object[]> findCountOfReportsPerDay();

    @Query(value = "SELECT DATE_TRUNC('day', r.created_at) AS day, " +
            "COUNT(*) AS count " +
            "FROM report r " +
            "JOIN city c ON r.city_id = c.city_id " +
            "JOIN governorate g ON c.governorate_id = g.governorate_id " +
            "WHERE r.created_at > now() - INTERVAL '2 month' AND g.governorate_id= :govId " +
            "GROUP BY DATE_TRUNC('day', r.created_at) " +
            "ORDER BY day ASC", nativeQuery = true)
    List<Object[]> findCountOfReportsPerDayPerGovernorate(@Param("govId") Long govId);

    @Query(value = "SELECT DATE_TRUNC('day', r.created_at) AS day, " +
            "COUNT(*) AS count " +
            "FROM report r " +
            "WHERE r.created_at > now() - INTERVAL '2 month' AND r.city_id = :cityId " +
            "GROUP BY DATE_TRUNC('day', r.created_at) " +
            "ORDER BY day ASC", nativeQuery = true)
    List<Object[]> findCountOfReportsPerDayPerCity(@Param("cityId") Long cityId);

}
