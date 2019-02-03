package com.dhunter.common.network;

/**
 * Created by umt041 on 2019/1/18.
 */
public class ResultException extends RuntimeException  {

    public static final int RESULT_FAILED = 0;

    private int errorCode;
    private boolean isSuccess;

    private String message;

    public ResultException(String message,int code) {
        errorCode = code;
        this.message = message;
    }


    public ResultException(Throwable cause) {
        super(cause);
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
