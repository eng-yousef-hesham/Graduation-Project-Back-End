package org.gp.civiceye.service.impl;

import org.gp.civiceye.repository.entity.ReportStatus;
import org.gp.civiceye.service.ReportStatusService;
import org.springframework.stereotype.Service;

@Service
public class ReportStatusServiceImpl implements ReportStatusService {

    @Override
    public ReportStatus[] getAllReportStatus() {
        return ReportStatus.values();
    }
}
