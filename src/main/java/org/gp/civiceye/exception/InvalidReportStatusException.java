package org.gp.civiceye.exception;

import org.gp.civiceye.repository.entity.ReportStatus;

public class InvalidReportStatusException extends CivicEyeException {

    private static final String ERROR_CODE = "INVALID_REPORT_STATUS";
    private static final int HTTP_STATUS = 400;

    public InvalidReportStatusException(String message) {
        super(message, ERROR_CODE, HTTP_STATUS);
    }

    public InvalidReportStatusException(String message, Throwable cause) {
        super(message, cause, ERROR_CODE, HTTP_STATUS);
    }

    public InvalidReportStatusException(ReportStatus currentStatus, ReportStatus expectedStatus) {
        super(String.format("Report status is %s, but expected %s", currentStatus, expectedStatus), ERROR_CODE, HTTP_STATUS);
    }

    public InvalidReportStatusException() {
        super("Report is not resolved yet", ERROR_CODE, HTTP_STATUS);
    }
}