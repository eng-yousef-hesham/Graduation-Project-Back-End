package org.gp.civiceye.service.impl;

import org.gp.civiceye.service.ChartGenerationService;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class ChartGenerationServiceImpl implements ChartGenerationService {

    @Override
    public byte[] generateBarChart(List<Map<String, Object>> data, int width, int height) {
        try {
            JFreeChart chart = createBarChart(data);
            return chartToByteArray(chart, width, height);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate bar chart", e);
        }
    }

    @Override
    public byte[] generatePieChart(List<Map<String, Object>> data, int width, int height) {
        try {
            JFreeChart chart = createPieChart(data);
            return chartToByteArray(chart, width, height);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate pie chart", e);
        }
    }

    private JFreeChart createBarChart(List<Map<String, Object>> data) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map<String, Object> item : data) {
            String dept = cleanDepartmentName(item.get("department").toString());
            Integer count = Integer.parseInt(item.get("reportCount").toString());
            dataset.addValue(count, "Reports", dept);
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "",
                "Department",
                "Number of Reports",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );

        customizeBarChart(chart);
        return chart;
    }

    private JFreeChart createPieChart(List<Map<String, Object>> data) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (Map<String, Object> item : data) {
            String dept = cleanDepartmentName(item.get("department").toString());
            Integer count = Integer.parseInt(item.get("reportCount").toString());
            dataset.setValue(dept, count);
        }

        JFreeChart chart = ChartFactory.createPieChart(
                "",
                dataset,
                true,
                true,
                false
        );

        customizePieChart(chart);
        return chart;
    }

    private void customizeBarChart(JFreeChart chart) {
        CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(52, 152, 219));
        renderer.setDrawBarOutline(false);
        renderer.setItemMargin(0.1);

        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinesVisible(false);
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(new Color(230, 230, 230));
        plot.setOutlineVisible(false);

        plot.getDomainAxis().setTickLabelFont(new Font("SansSerif", Font.PLAIN, 10));
        plot.getRangeAxis().setTickLabelFont(new Font("SansSerif", Font.PLAIN, 10));
        plot.getDomainAxis().setLabelFont(new Font("SansSerif", Font.BOLD, 12));
        plot.getRangeAxis().setLabelFont(new Font("SansSerif", Font.BOLD, 12));

        plot.getDomainAxis().setAxisLineVisible(false);
        plot.getRangeAxis().setAxisLineVisible(false);
        plot.getDomainAxis().setTickMarksVisible(false);
        plot.getRangeAxis().setTickMarksVisible(false);

        chart.setBackgroundPaint(Color.WHITE);
        chart.setBorderVisible(false);
    }

    private void customizePieChart(JFreeChart chart) {
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setOutlineVisible(false);
        plot.setShadowPaint(null);
        plot.setCircular(true);
        plot.setInteriorGap(0.10);

        plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 14));
        plot.setLabelBackgroundPaint(Color.WHITE);
        plot.setLabelOutlinePaint(Color.GRAY);
        plot.setLabelShadowPaint(null);
        plot.setSimpleLabels(false);

        plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
                "{0}\n({1}, {2})",
                java.text.NumberFormat.getIntegerInstance(),
                java.text.NumberFormat.getPercentInstance()
        ));

        for (Object key : plot.getDataset().getKeys()) {
            plot.setExplodePercent((Comparable<?>) key, 0.05);
        }

        Color[] colors = {
                new Color(52, 152, 219), new Color(46, 204, 113),
                new Color(241, 196, 15), new Color(231, 76, 60),
                new Color(155, 89, 182), new Color(52, 73, 94),
                new Color(26, 188, 156), new Color(149, 165, 166),
                new Color(230, 126, 34), new Color(142, 68, 173)
        };

        int colorIndex = 0;
        for (Object key : plot.getDataset().getKeys()) {
            plot.setSectionPaint((Comparable<?>) key, colors[colorIndex % colors.length]);
            colorIndex++;
        }

        if (chart.getLegend() != null) {
            chart.getLegend().setItemFont(new Font("SansSerif", Font.PLAIN, 12));
            chart.getLegend().setBackgroundPaint(Color.WHITE);
        }

        chart.setBackgroundPaint(Color.WHITE);
        chart.setBorderVisible(false);
    }

    private byte[] chartToByteArray(JFreeChart chart, int width, int height) throws IOException {
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);
        chart.draw(g2d, new Rectangle(0, 0, width, height));
        g2d.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", baos);
        return baos.toByteArray();
    }

    private String cleanDepartmentName(String department) {
        if (department == null) return "Unknown";

        department = department
                .replace("_", " ")
                .replace("Authority", "Auth.")
                .replace("AUTHORITY", "Auth.")
                .replace("Department", "Dept.")
                .replace("DEPARTMENT", "Dept.")
                .trim();

        // Handle long names with manual line breaks
        if (department.contains("Waste Management")) {
            department = department.replace("Waste Management", "Waste\nManagement");
        }
        if (department.contains("Administrative Complaints")) {
            department = department.replace("Administrative Complaints", "Administrative\nComplaints");
        }
        if (department.contains("Customer Service")) {
            department = department.replace("Customer Service", "Customer\nService");
        }

        if (department.length() > 20 && !department.contains("\n")) {
            department = department.replace(" ", "\n"); // Fallback wrap
        }

        return department;
    }

    public byte[] generateHorizontalBarChart(List<Map<String, Object>> data, int width, int height) {
        try {
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

            for (Map<String, Object> item : data) {
                String dept = cleanDepartmentName(item.get("department").toString());
                Integer count = Integer.parseInt(item.get("reportCount").toString());
                dataset.addValue(count, "Reports", dept);
            }

            JFreeChart chart = ChartFactory.createBarChart(
                    "",
                    "Number of Reports",
                    "Department",
                    dataset,
                    PlotOrientation.HORIZONTAL,
                    false,
                    true,
                    false
            );

            customizeHorizontalBarChart(chart);
            return chartToByteArray(chart, width, height);

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate horizontal bar chart", e);
        }
    }

    private void customizeHorizontalBarChart(JFreeChart chart) {
        CategoryPlot plot = chart.getCategoryPlot();

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(52, 152, 219));
        renderer.setDrawBarOutline(false);
        renderer.setItemMargin(0.1);

        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinesVisible(true);
        plot.setRangeGridlinesVisible(false);
        plot.setDomainGridlinePaint(new Color(230, 230, 230));
        plot.setOutlineVisible(false);

        plot.getDomainAxis().setTickLabelFont(new Font("SansSerif", Font.PLAIN, 9));
        plot.getRangeAxis().setTickLabelFont(new Font("SansSerif", Font.PLAIN, 10));
        plot.getDomainAxis().setLabelFont(new Font("SansSerif", Font.BOLD, 12));
        plot.getRangeAxis().setLabelFont(new Font("SansSerif", Font.BOLD, 12));

        plot.getDomainAxis().setAxisLineVisible(false);
        plot.getRangeAxis().setAxisLineVisible(false);
        plot.getDomainAxis().setTickMarksVisible(false);
        plot.getRangeAxis().setTickMarksVisible(false);

        chart.setBackgroundPaint(Color.WHITE);
        chart.setBorderVisible(false);
    }

    public byte[] generateDonutChart(List<Map<String, Object>> data, int width, int height) {
        try {
            JFreeChart chart = createPieChart(data); // use standard pie chart if RingPlot not available
            PiePlot plot = (PiePlot) chart.getPlot();
            plot.setInteriorGap(0.30); // simulate donut look
            return chartToByteArray(chart, width, height);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate donut chart", e);
        }
    }
}
