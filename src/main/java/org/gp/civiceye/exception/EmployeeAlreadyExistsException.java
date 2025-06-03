package org.gp.civiceye.exception;

public class EmployeeAlreadyExistsException extends CivicEyeException {

    private static final String ERROR_CODE = "EMPLOYEE_ALREADY_EXISTS";
    private static final int HTTP_STATUS = 409;

    public EmployeeAlreadyExistsException(String message) {
        super(message, ERROR_CODE, HTTP_STATUS);
    }

    public EmployeeAlreadyExistsException(String message, Throwable cause) {
        super(message, cause, ERROR_CODE, HTTP_STATUS);
    }

    public EmployeeAlreadyExistsException(String email, String nationalId) {
        super("Employee already exists with email: " + email + " or national ID: " + nationalId, ERROR_CODE, HTTP_STATUS);
    }

    public EmployeeAlreadyExistsException() {
        super("Employee already exists with the provided email or national ID", ERROR_CODE, HTTP_STATUS);
    }
}