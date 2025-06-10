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

    Optional<List<Employee>> findByCity(City city);

    Optional<Employee> findByEmailOrNationalId(String email, String nationalId);

    Page<Employee> findByDepartment(Department department, Pageable pageable);

    Page<Employee> findByDepartmentAndCity(Department department, City city, Pageable pageable);

    Page<Employee> findByDepartmentAndCity_Governorate(Department department, Governorate governorate, Pageable pageable);

    Page<Employee> findByCity(City city, Pageable pageable);

    Page<Employee> findByCity_Governorate(Governorate governorate, Pageable pageable);
}