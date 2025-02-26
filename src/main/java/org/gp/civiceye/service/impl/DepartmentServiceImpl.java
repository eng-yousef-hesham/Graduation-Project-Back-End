package org.gp.civiceye.service.impl;

import org.gp.civiceye.repository.entity.Department;
import org.gp.civiceye.service.DepartmentService;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Override
    public Department[] getAllDepartments() {

        return Department.values();
    }
}
