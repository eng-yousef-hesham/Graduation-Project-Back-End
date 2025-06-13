package org.gp.civiceye.mapper;

public class CityReportCountDTO {
    private String city;
    private Double averageTime;

    public CityReportCountDTO(String city, Double averageTime) {
        this.city = city;
        this.averageTime = averageTime;
    }

    // Getters and setters
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public Double getAverageTime() { return averageTime; }
    public void setAverageTime(Double averageTime) { this.averageTime = averageTime; }
}
