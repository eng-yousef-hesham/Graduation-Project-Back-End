package org.gp.civiceye.exception;

public class EmployeeDeletionException extends CivicEyeException {

    private static final String ERROR_CODE = "EMPLOYEE_DELETION_FAILED";
    private static final int HTTP_STATUS = 500;

    public EmployeeDeletionException(String message) {
        super(message, ERROR_CODE, HTTP_STATUS);
    }

    public EmployeeDeletionException(String message, Throwable cause) {
        super(message, cause, ERROR_CODE, HTTP_STATUS);
    }

    public EmployeeDeletionException(Long employeeId, Throwable cause) {
        super("Failed to delete employee with ID: " + employeeId, cause, ERROR_CODE, HTTP_STATUS);
    }

    public EmployeeDeletionException(Throwable cause) {
        super("Failed to delete employee", cause, ERROR_CODE, HTTP_STATUS);
    }
}