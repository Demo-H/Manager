package com.tupperware.mgt.entity.login;

/**
 * Created by dhunter on 2018/11/29.
 */

public class WeChatRequst {
    private String code;
    private String platform;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
}
