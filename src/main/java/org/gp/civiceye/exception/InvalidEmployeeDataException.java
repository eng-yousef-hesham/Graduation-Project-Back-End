package org.gp.civiceye.exception;

public class InvalidEmployeeDataException extends CivicEyeException {

    private static final String ERROR_CODE = "INVALID_EMPLOYEE_DATA";
    private static final int HTTP_STATUS = 400;

    public InvalidEmployeeDataException(String message) {
        super(message, ERROR_CODE, HTTP_STATUS);
    }

    public InvalidEmployeeDataException(String message, Throwable cause) {
        super(message, cause, ERROR_CODE, HTTP_STATUS);
    }

    public InvalidEmployeeDataException(String field, String value) {
        super("Invalid employee data - " + field + ": " + value, ERROR_CODE, HTTP_STATUS);
    }

    public InvalidEmployeeDataException() {
        super("Invalid employee data provided", ERROR_CODE, HTTP_STATUS);
    }
}