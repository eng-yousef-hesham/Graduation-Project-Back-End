package org.gp.civiceye.service.analysis;

public interface EmployeeAnalysisService {

    public Long getEmployeesCount();

    public Double getAverageRatingPerGovernorate(Long govId);

    public Double getAverageRatingPerCity(Long cityId);

    public Double getAverageRating();
}
