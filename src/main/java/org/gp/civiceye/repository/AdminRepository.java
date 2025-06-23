package org.gp.civiceye.repository;

import org.gp.civiceye.repository.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    void deleteByAdminId(Long adminId);
    void deleteAdminByAdminId(Long adminId);
    void deleteById(Long adminId);
}