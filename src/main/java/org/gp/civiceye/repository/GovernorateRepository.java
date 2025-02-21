package org.gp.civiceye.repository;



import org.gp.civiceye.repository.entity.Governorate;
import org.springframework.data.jpa.repository.JpaRepository;




import java.util.List;
import java.util.Optional;


public interface GovernorateRepository extends JpaRepository<Governorate, Integer> {
//    List<Governorate> findByIsActiveTrue();
    Optional<Governorate> findByGovernorateId(Integer id);
}