package org.gp.civiceye.repository;

import org.gp.civiceye.repository.entity.GovernorateAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface GovernorateAdminRepository extends JpaRepository<GovernorateAdmin, Long> {
    Optional<GovernorateAdmin> findByEmail(String email);
}
