package org.gp.civiceye.repository;

import org.gp.civiceye.repository.entity.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface CitizenRepository extends JpaRepository<Citizen, Long> {


    Optional<Citizen> findByEmail(String email);




//    List<Citizen> findByCityId(Integer cityId);
//    @Query("SELECT c FROM Citizen c WHERE c.age >= :minAge")
//    List<Citizen> findByMinimumAge(@Param("minAge") Integer minAge);
}
