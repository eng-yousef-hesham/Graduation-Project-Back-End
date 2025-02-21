package org.gp.civiceye.repository;


import org.gp.civiceye.repository.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

      //    List<City> findByGovernorateId(Integer governorateId);
//    List<City> findByIsActiveTrue();
//      City findByCityId(Long cityId);
}