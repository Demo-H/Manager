package com.tupperware.mgt.entity.login;

/**
 * Created by dhunter on 2018/11/26.
 */

public class ModifiedRequest {
    private String employeeId;
    private String oldPassword;
    private String newPassword;

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
