package com.tupperware.mgt.entity;

/**
 * Created by dhunter on 2018/11/23.
 */

public abstract class BaseData {
    public boolean success;
    public String resultCode;
    public String message;


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}