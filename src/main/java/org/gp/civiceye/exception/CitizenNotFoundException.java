package org.gp.civiceye.exception;

public class CitizenNotFoundException extends CivicEyeException {
    private static final String ERROR_CODE = "CITIZEN_NOT_FOUND";
    private static final int HTTP_STATUS = 404;

    public CitizenNotFoundException(String message) {
        super(message, "CITIZEN_NOT_FOUND", 404);
    }
    public CitizenNotFoundException(String message, Throwable cause) {
        super(message, cause, ERROR_CODE, HTTP_STATUS);
    }

    public CitizenNotFoundException(Long citizenId) {
        super("Citizen not found with ID: " + citizenId, ERROR_CODE, HTTP_STATUS);
    }
}
