package org.gp.civiceye.service.impl.employee;

public class UpdateEmployeeResult {
    private boolean success;
    private String message;



    public UpdateEmployeeResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
}
