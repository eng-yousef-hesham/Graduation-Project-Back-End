package org.gp.civiceye.service;

import org.jfree.chart.JFreeChart;
import java.util.List;
import java.util.Map;

public interface ChartGenerationService {
    byte[] generateBarChart(List<Map<String, Object>> data, int width, int height);
    byte[] generatePieChart(List<Map<String, Object>> data, int width, int height);
}
