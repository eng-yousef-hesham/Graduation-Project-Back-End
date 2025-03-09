package org.gp.civiceye.repository;

import org.gp.civiceye.repository.entity.CityAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CityAdminRepository extends JpaRepository<CityAdmin, Long> {
    Optional<CityAdmin> findByEmail(String email);

//  public CityAdmin findByAdminId(Long adminId);


}
