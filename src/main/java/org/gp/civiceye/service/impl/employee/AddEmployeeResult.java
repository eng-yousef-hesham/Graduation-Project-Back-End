package org.gp.civiceye.service.impl.employee;


public class AddEmployeeResult {
    private boolean success;
    private String message;



    public AddEmployeeResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }

}
