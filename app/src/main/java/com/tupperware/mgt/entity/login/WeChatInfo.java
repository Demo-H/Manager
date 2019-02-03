package com.tupperware.mgt.entity.login;

import java.io.Serializable;

/**
 * Created by dhunter on 2018/11/29.
 */

public class WeChatInfo implements Serializable {
    private String createTime; //  绑定时间 ,
    private String pMobile; //  用户手机号 ,
    private String pWxCity; //  城市 ,
    private String pWxCountry; //  国家 ,
    private String pWxHeadimg; //  微信头像 ,
    private String pWxNickname; //  微信昵称 ,
    private String pWxOpenid; //  微信唯一id ,
    private String pWxProvince; //  用户省份 ,
    private String pWxSex; //  微信性别（1.男 2.女 0.未知）
    private String pWxUnionid;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getpMobile() {
        return pMobile;
    }

    public void setpMobile(String pMobile) {
        this.pMobile = pMobile;
    }

    public String getpWxCity() {
        return pWxCity;
    }

    public void setpWxCity(String pWxCity) {
        this.pWxCity = pWxCity;
    }

    public String getpWxCountry() {
        return pWxCountry;
    }

    public void setpWxCountry(String pWxCountry) {
        this.pWxCountry = pWxCountry;
    }

    public String getpWxHeadimg() {
        return pWxHeadimg;
    }

    public void setpWxHeadimg(String pWxHeadimg) {
        this.pWxHeadimg = pWxHeadimg;
    }

    public String getpWxNickname() {
        return pWxNickname;
    }

    public void setpWxNickname(String pWxNickname) {
        this.pWxNickname = pWxNickname;
    }

    public String getpWxOpenid() {
        return pWxOpenid;
    }

    public void setpWxOpenid(String pWxOpenid) {
        this.pWxOpenid = pWxOpenid;
    }

    public String getpWxProvince() {
        return pWxProvince;
    }

    public void setpWxProvince(String pWxProvince) {
        this.pWxProvince = pWxProvince;
    }

    public String getpWxSex() {
        return pWxSex;
    }

    public void setpWxSex(String pWxSex) {
        this.pWxSex = pWxSex;
    }
}
