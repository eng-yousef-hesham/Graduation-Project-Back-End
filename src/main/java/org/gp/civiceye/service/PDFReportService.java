package org.gp.civiceye.service;

import java.util.List;
import java.util.Map;

public interface PDFReportService {
    byte[] generateReportPDF(List<Map<String, Object>> reportData);
}