package org.gp.civiceye.exception;

public class UnAuthorizedActionException extends CivicEyeException {
    private static final String ERROR_CODE = "UNAUTHORIZED_ACTION";
    private static final int HTTP_STATUS = 401;

    public UnAuthorizedActionException() {
        super("Unauthorized action", ERROR_CODE, HTTP_STATUS);
    }
}
