package org.gp.civiceye.repository;

import org.gp.civiceye.repository.entity.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;


public interface CitizenRepository extends JpaRepository<Citizen, Integer> {
//    List<Citizen> findByCityId(Integer cityId);
//
//    @Query("SELECT c FROM Citizen c WHERE c.age >= :minAge")
//    List<Citizen> findByMinimumAge(@Param("minAge") Integer minAge);
}
