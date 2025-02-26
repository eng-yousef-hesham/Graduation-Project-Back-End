package org.gp.civiceye.service.impl;

import org.gp.civiceye.mapper.ReportDTO;
import org.gp.civiceye.repository.EmployeeRepository;
import org.gp.civiceye.repository.ReportRepository;
import org.gp.civiceye.repository.entity.Employee;
import org.gp.civiceye.service.ReportService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ReportServiceImpl implements ReportService {

    ReportRepository reportRepository;


    public ReportServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }


    @Override
    public List<ReportDTO> GetAllReports() {
            return reportRepository.findAll().stream()
                    .map(ReportDTO::new)
                    .collect(Collectors.toList());
    }
}
