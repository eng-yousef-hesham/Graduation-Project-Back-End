package org.gp.civiceye.repository;

import org.gp.civiceye.repository.entity.GovernrateAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GovernorateAdminRepository extends JpaRepository<GovernrateAdmin, Long> {
}
