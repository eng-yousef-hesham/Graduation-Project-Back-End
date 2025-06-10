package org.gp.civiceye.repository;

import lombok.Generated;
import org.gp.civiceye.repository.entity.Governorate;
import org.gp.civiceye.repository.entity.GovernorateAdmin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface GovernorateAdminRepository extends JpaRepository<GovernorateAdmin, Long> {
    Optional<GovernorateAdmin> findByEmail(String email);
    Page <GovernorateAdmin> findByGovernorate(Governorate governorate, Pageable pageable);

    @Query("SELECT ga.governorate FROM GovernorateAdmin ga WHERE ga.email = :email")
    Optional<Governorate> findGovernorateByEmail(String email);
}
