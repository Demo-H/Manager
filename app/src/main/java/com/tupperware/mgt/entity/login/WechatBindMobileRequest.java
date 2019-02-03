package com.tupperware.mgt.entity.login;

/**
 * Created by dhunter on 2018/11/29.
 */

public class WechatBindMobileRequest {
    private String code; //短信验证码
    private String mobile;
    private String platform;
    private EmployeeInfo fmsEmployeeWx;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public EmployeeInfo getFmsEmployeeWx() {
        return fmsEmployeeWx;
    }

    public void setFmsEmployeeWx(EmployeeInfo fmsEmployeeWx) {
        this.fmsEmployeeWx = fmsEmployeeWx;
    }
}
