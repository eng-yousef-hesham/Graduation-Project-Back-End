package org.gp.civiceye.exception;

public class InvalidAdminTypeException extends CivicEyeException {
    private static final String ERROR_CODE = "INVALID_ADMIN_TYPE";
    private static final int HTTP_STATUS = 400;
    public InvalidAdminTypeException(String message) {
        super(message, ERROR_CODE, HTTP_STATUS);
    }
    public InvalidAdminTypeException() {
        super("Invalid admin type provided", ERROR_CODE, HTTP_STATUS);
    }
}
