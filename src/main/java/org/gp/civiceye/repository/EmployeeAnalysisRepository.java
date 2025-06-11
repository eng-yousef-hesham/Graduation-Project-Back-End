package org.gp.civiceye.repository;

import org.gp.civiceye.repository.entity.Department;
import org.gp.civiceye.repository.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeAnalysisRepository extends JpaRepository<Employee, Long> {
    @Query("SELECT AVG(e.rating) FROM Employee e where e.city.governorate.governorateId = :govId")
    Double findAverageRatingPerGovernorate(@Param("govId") Long govId);

    @Query("SELECT AVG(e.rating) FROM Employee e where e.city.cityId = :cityId")
    Double findAverageRatingPerCity(@Param("cityId") Long cityId);

    @Query("SELECT AVG(e.rating) FROM Employee e")
    Double findAverageRating();


}
