package org.gp.civiceye.repository;

import org.gp.civiceye.repository.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);

    Optional<List<Employee>> findByCityAndDepartment(City city, Department department);

    Page<Employee> findByCity_CityId(Long cityId, Pageable pageable);

    Optional<Employee> findByEmailOrNationalId(String email, String nationalId);

    Page<Employee> findByDepartment(Department department, Pageable pageable);

    Page<Employee> findByDepartmentAndCity_CityId(Department department, Long cityId, Pageable pageable);

    Page<Employee> findByDepartmentAndCity_Governorate_GovernorateId(Department department, Long governorateId, Pageable pageable);

    Page<Employee> findByCity(City city, Pageable pageable);

    Page<Employee> findByCity_Governorate_GovernorateId(Long governorateId, Pageable pageable);
}