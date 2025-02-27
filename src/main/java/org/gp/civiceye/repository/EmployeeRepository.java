package org.gp.civiceye.repository;

import org.gp.civiceye.repository.entity.Citizen;
import org.gp.civiceye.repository.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Citizen> findByEmail(String email);
}