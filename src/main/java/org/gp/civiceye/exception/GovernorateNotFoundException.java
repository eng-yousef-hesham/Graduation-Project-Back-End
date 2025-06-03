package org.gp.civiceye.exception;

public class GovernorateNotFoundException extends CivicEyeException {
    private static final String ERROR_CODE = "GOVERNORATE_NOT_FOUND";
    private static final int HTTP_STATUS = 404;

    public GovernorateNotFoundException(String message) {
        super(message, "GOVERNORATE_NOT_FOUND", 404);
    }

    public GovernorateNotFoundException(String message, Throwable cause) {
        super(message, cause, ERROR_CODE, HTTP_STATUS);
    }

    public GovernorateNotFoundException(Long governorateId) {
        super("Governorate not found with ID: " + governorateId, ERROR_CODE, HTTP_STATUS);
    }
}
