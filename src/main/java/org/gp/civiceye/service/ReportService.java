package org.gp.civiceye.service;

import org.gp.civiceye.mapper.ReportDTO;
import org.gp.civiceye.repository.entity.Employee;

import java.util.List;

public interface ReportService {
    public List<ReportDTO> GetAllReports();
}
