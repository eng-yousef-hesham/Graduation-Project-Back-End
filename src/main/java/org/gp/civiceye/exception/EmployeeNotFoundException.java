package org.gp.civiceye.exception;

public class EmployeeNotFoundException extends CivicEyeException {

    private static final String ERROR_CODE = "EMPLOYEE_NOT_FOUND";
    private static final int HTTP_STATUS = 404;

    public EmployeeNotFoundException(String message) {
        super(message, ERROR_CODE, HTTP_STATUS);
    }

    public EmployeeNotFoundException(String message, Throwable cause) {
        super(message, cause, ERROR_CODE, HTTP_STATUS);
    }

    public EmployeeNotFoundException(Long employeeId) {
        super("Employee not found with ID: " + employeeId, ERROR_CODE, HTTP_STATUS);
    }
}