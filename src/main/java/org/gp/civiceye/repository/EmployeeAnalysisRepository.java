package org.gp.civiceye.repository;

import org.gp.civiceye.repository.entity.Department;
import org.gp.civiceye.repository.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeAnalysisRepository extends JpaRepository<Employee, Long> {
    @Query("SELECT AVG(e.rating) FROM Employee e where e.city.governorate.governorateId = :govId")
    Double findAverageRatingPerGovernorate(@Param("govId") Long govId);

    @Query("SELECT AVG(e.rating) FROM Employee e where e.city.cityId = :cityId")
    Double findAverageRatingPerCity(@Param("cityId") Long cityId);

    @Query("SELECT AVG(e.rating) FROM Employee e")
    Double findAverageRating();

    List<Employee> findTop8ByOrderByRatingDesc();

    @Query("SELECT e FROM Employee e " +
            "WHERE e.city.governorate.governorateId = :govId " +
            "ORDER BY e.rating DESC LIMIT 8")
    List<Employee> findTop8RatedEmployeesPerGovernorate(@Param("govId") Long govId);

    @Query("SELECT e FROM Employee e " +
            "WHERE e.city.cityId = :cityId " +
            "ORDER BY e.rating DESC LIMIT 8")
    List<Employee> findTop8RatedEmployeesPerCity(@Param("cityId") Long cityId);




}
