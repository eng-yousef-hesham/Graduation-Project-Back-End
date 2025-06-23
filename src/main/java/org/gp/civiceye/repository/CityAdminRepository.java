package org.gp.civiceye.repository;

import org.gp.civiceye.repository.entity.City;
import org.gp.civiceye.repository.entity.CityAdmin;
import org.gp.civiceye.repository.entity.Governorate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CityAdminRepository extends JpaRepository<CityAdmin, Long> {
    Optional<CityAdmin> findByEmail(String email);
    Page<CityAdmin> findByCity_CityId(Long cityId, Pageable pageable);
    Page<CityAdmin> findByCity_Governorate_GovernorateId(Long governorateId, Pageable pageable);
}
