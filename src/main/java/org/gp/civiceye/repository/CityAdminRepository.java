package org.gp.civiceye.repository;

import org.gp.civiceye.repository.entity.CityAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CityAdminRepository extends JpaRepository<CityAdmin, Long> {

//  public CityAdmin findByAdminId(Long adminId);

}
