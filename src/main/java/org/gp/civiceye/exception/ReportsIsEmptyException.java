package org.gp.civiceye.exception;

public class ReportsIsEmptyException  extends CivicEyeException {

    private static final String ERROR_CODE = "REPORTS_NOT_FOUND";
    private static final int HTTP_STATUS = 404;

    public ReportsIsEmptyException(String message) {
        super(message, ERROR_CODE, HTTP_STATUS);
    }

    public ReportsIsEmptyException(String message, Throwable cause) {
        super(message, cause, ERROR_CODE, HTTP_STATUS);
    }
    public ReportsIsEmptyException() {
        super("There IS no reports" , ERROR_CODE, HTTP_STATUS);
    }
}
