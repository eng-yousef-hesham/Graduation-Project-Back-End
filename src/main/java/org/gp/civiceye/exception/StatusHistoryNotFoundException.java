package org.gp.civiceye.exception;

public class StatusHistoryNotFoundException extends CivicEyeException {

    private static final String ERROR_CODE = "STATUS_HISTORY_NOT_FOUND";
    private static final int HTTP_STATUS = 404;

    public StatusHistoryNotFoundException(String message) {
        super(message, ERROR_CODE, HTTP_STATUS);
    }

    public StatusHistoryNotFoundException(String message, Throwable cause) {
        super(message, cause, ERROR_CODE, HTTP_STATUS);
    }

    public StatusHistoryNotFoundException() {
        super("No status history found", ERROR_CODE, HTTP_STATUS);
    }
}