package org.gp.civiceye.exception;

public class IllegalOperationException extends CivicEyeException {
    private static final String ERROR_CODE = "ILLEGAL_OPERATION";
    private static final int HTTP_STATUS = 400;
    public IllegalOperationException(String message) {
        super(message, ERROR_CODE, HTTP_STATUS);
    }
}
