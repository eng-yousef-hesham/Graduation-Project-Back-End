package org.gp.civiceye.exception;

public class InvalidLoginRequestException extends CivicEyeException{
    private static final String ERROR_CODE = "WRONG_CREDENTIALS";
    private static final int HTTP_STATUS = 401;

    public InvalidLoginRequestException(String message) {
        super(message, ERROR_CODE, HTTP_STATUS);
    }
}
