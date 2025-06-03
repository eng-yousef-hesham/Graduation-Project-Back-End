package org.gp.civiceye.exception;

public class AdminNotFoundException extends CivicEyeException {

    private static final String ERROR_CODE = "ADMIN_NOT_FOUND";
    private static final int HTTP_STATUS = 404;

    public AdminNotFoundException(String message) {
        super(message, ERROR_CODE, HTTP_STATUS);
    }

    public AdminNotFoundException(Long adminId) {
        super("Admin not found with ID: " + adminId, ERROR_CODE, HTTP_STATUS);
    }
}
