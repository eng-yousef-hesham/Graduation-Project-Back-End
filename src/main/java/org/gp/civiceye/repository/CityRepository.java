package org.gp.civiceye.repository;


import org.gp.civiceye.repository.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Integer> {

      //    List<City> findByGovernorateId(Integer governorateId);
//    List<City> findByIsActiveTrue();
      City findByCityId(Integer cityId);
}