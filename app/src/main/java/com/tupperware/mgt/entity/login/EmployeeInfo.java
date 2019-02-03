package com.tupperware.mgt.entity.login;

import java.io.Serializable;

/**
 * 员工信息
 * Created by dhunter on 2018/11/23.
 */

public class EmployeeInfo implements Serializable {

    private String pName;
    private String pUid;
    private String passwd;
    private String pPosition;
    private int isDisable; //禁用标志 1:禁用 0:正常 ,
    private String pAddress; //地址
    private String pCityno; //城市
    private String pOrganization; // 所属机构
    private String pProvince; //省办
    private String pRegion; //区域
    private String pRole; //角色

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

    public String getpWxUnionid() {
        return pWxUnionid;
    }

    public void setpWxUnionid(String pWxUnionid) {
        this.pWxUnionid = pWxUnionid;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpUid() {
        return pUid;
    }

    public void setpUid(String pUid) {
        this.pUid = pUid;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getpPosition() {
        return pPosition;
    }

    public void setpPosition(String pPosition) {
        this.pPosition = pPosition;
    }

    public int getIsDisable() {
        return isDisable;
    }

    public void setIsDisable(int isDisable) {
        this.isDisable = isDisable;
    }

    public String getpAddress() {
        return pAddress;
    }

    public void setpAddress(String pAddress) {
        this.pAddress = pAddress;
    }

    public String getpCityno() {
        return pCityno;
    }

    public void setpCityno(String pCityno) {
        this.pCityno = pCityno;
    }

    public String getpOrganization() {
        return pOrganization;
    }

    public void setpOrganization(String pOrganization) {
        this.pOrganization = pOrganization;
    }

    public String getpProvince() {
        return pProvince;
    }

    public void setpProvince(String pProvince) {
        this.pProvince = pProvince;
    }

    public String getpRegion() {
        return pRegion;
    }

    public void setpRegion(String pRegion) {
        this.pRegion = pRegion;
    }

    public String getpRole() {
        return pRole;
    }

    public void setpRole(String pRole) {
        this.pRole = pRole;
    }
}
