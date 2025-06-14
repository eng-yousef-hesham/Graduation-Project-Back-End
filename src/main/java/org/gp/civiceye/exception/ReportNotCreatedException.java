package org.gp.civiceye.exception;

public class ReportNotCreatedException extends CivicEyeException {

  private static final String ERROR_CODE = "REPORT_NOT_FOUND";
  private static final int HTTP_STATUS = 404;

  public ReportNotCreatedException(String message) {
    super(message, ERROR_CODE, HTTP_STATUS);
  }

  public ReportNotCreatedException(String message, Throwable cause) {
    super(message, cause, ERROR_CODE, HTTP_STATUS);
  }

  public ReportNotCreatedException() {
    super("Report does not created", ERROR_CODE, HTTP_STATUS );
  }
}