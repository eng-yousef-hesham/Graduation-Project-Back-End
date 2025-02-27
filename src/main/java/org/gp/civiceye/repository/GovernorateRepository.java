package org.gp.civiceye.repository;



import org.gp.civiceye.repository.entity.Governorate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface GovernorateRepository extends JpaRepository<Governorate, Long> {
//    List<Governorate> findByIsActiveTrue();
//    Optional<Governorate> findByGovernorateId(Long id);
}