package org.gp.civiceye.exception;

public class CitizenAlreadyExistsException extends CivicEyeException {

    private static final String ERROR_CODE = "CITIZEN_ALREADY_EXISTS";
    private static final int HTTP_STATUS = 409;

    public CitizenAlreadyExistsException(String message) {
        super(message, ERROR_CODE, HTTP_STATUS);
    }

    public CitizenAlreadyExistsException(String message, Throwable cause) {
        super(message, cause, ERROR_CODE, HTTP_STATUS);
    }

    public CitizenAlreadyExistsException(String email, String nationalId) {
        super("Citizen already exists with email: " + email + " or national ID: " + nationalId, ERROR_CODE, HTTP_STATUS);
    }

    public CitizenAlreadyExistsException() {
        super("Citizen already exists with the provided email or national ID", ERROR_CODE, HTTP_STATUS);
    }
}