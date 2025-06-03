package org.gp.civiceye.exception;

public class EmployeeUpdateException extends CivicEyeException {

    private static final String ERROR_CODE = "EMPLOYEE_UPDATE_FAILED";
    private static final int HTTP_STATUS = 500;

    public EmployeeUpdateException(String message) {
        super(message, ERROR_CODE, HTTP_STATUS);
    }

    public EmployeeUpdateException(String message, Throwable cause) {
        super(message, cause, ERROR_CODE, HTTP_STATUS);
    }

    public EmployeeUpdateException(Long employeeId, Throwable cause) {
        super("Failed to update employee with ID: " + employeeId, cause, ERROR_CODE, HTTP_STATUS);
    }

    public EmployeeUpdateException(Throwable cause) {
        super("Failed to update employee", cause, ERROR_CODE, HTTP_STATUS);
    }
}