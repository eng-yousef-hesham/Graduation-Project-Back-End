package org.gp.civiceye.service.impl.admin;

public class DeleteAdminResult {
    private boolean success;
    private String message;
    private AdminType adminType;

    public DeleteAdminResult(boolean success, String message, AdminType adminType) {
        this.success = success;
        this.message = message;
        this.adminType = adminType;
    }

    public DeleteAdminResult(boolean success, String message) {
        this.success = success;
        this.message = message;
        this.adminType = null;
    }


    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public AdminType getAdminType() { return adminType; }

}
