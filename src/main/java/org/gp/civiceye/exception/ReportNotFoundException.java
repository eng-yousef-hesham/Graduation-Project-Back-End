package org.gp.civiceye.exception;

public class ReportNotFoundException extends CivicEyeException {

    private static final String ERROR_CODE = "REPORT_NOT_FOUND";
    private static final int HTTP_STATUS = 404;

    public ReportNotFoundException(String message) {
        super(message, ERROR_CODE, HTTP_STATUS);
    }

    public ReportNotFoundException(String message, Throwable cause) {
        super(message, cause, ERROR_CODE, HTTP_STATUS);
    }

    public ReportNotFoundException(Long reportId) {
        super("Report not found with ID: " + reportId, ERROR_CODE, HTTP_STATUS);
    }
}