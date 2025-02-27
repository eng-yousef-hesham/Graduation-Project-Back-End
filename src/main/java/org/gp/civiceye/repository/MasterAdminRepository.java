package org.gp.civiceye.repository;

import org.gp.civiceye.repository.entity.MasterAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MasterAdminRepository extends JpaRepository<MasterAdmin, Long> {
}