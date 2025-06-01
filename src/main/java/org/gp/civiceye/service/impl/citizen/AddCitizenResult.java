package org.gp.civiceye.service.impl.citizen;

import org.gp.civiceye.service.impl.admin.AdminType;

public class AddCitizenResult {
    private boolean success;
    private String message;

    public AddCitizenResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
}
