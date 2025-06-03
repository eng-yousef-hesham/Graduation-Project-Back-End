package org.gp.civiceye.exception;

public class EmployeeCreationException extends CivicEyeException {

    private static final String ERROR_CODE = "EMPLOYEE_CREATION_FAILED";
    private static final int HTTP_STATUS = 500;

    public EmployeeCreationException(String message) {
        super(message, ERROR_CODE, HTTP_STATUS);
    }

    public EmployeeCreationException(String message, Throwable cause) {
        super(message, cause, ERROR_CODE, HTTP_STATUS);
    }

    public EmployeeCreationException(Throwable cause) {
        super("Failed to create employee", cause, ERROR_CODE, HTTP_STATUS);
    }

    public EmployeeCreationException() {
        super("Failed to create employee", ERROR_CODE, HTTP_STATUS);
    }
}