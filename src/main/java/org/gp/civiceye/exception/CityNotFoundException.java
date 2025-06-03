package org.gp.civiceye.exception;

public class CityNotFoundException extends CivicEyeException {
    private static final String ERROR_CODE = "CITY_NOT_FOUND";
    private static final int HTTP_STATUS = 404;

    public CityNotFoundException(String message) {
        super(message, "CITY_NOT_FOUND", 404);
    }

    public CityNotFoundException(String message, Throwable cause) {
        super(message, cause, ERROR_CODE, HTTP_STATUS);
    }

    public CityNotFoundException(Long cityId) {
        super("City not found with ID: " + cityId, ERROR_CODE, HTTP_STATUS);
    }
}
