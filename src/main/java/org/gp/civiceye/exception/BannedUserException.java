package org.gp.civiceye.exception;

public class BannedUserException extends CivicEyeException {
    private static final String ERROR_CODE = "BANNED_USER";
    private static final int HTTP_STATUS = 403;

    public BannedUserException(String message) {
        super(message, ERROR_CODE, HTTP_STATUS);
    }

}
