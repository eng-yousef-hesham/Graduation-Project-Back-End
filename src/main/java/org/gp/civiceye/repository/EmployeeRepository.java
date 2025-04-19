package org.gp.civiceye.repository;

import org.gp.civiceye.repository.entity.Citizen;
import org.gp.civiceye.repository.entity.City;
import org.gp.civiceye.repository.entity.Department;
import org.gp.civiceye.repository.entity.Employee;
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
}