package com.tupperware.mgt.wxapi.callback;

/**
 * Created by admin on 2018/11/15.
 */

public interface WxLoginCallBack {

    void onWxLoginSuccess(String code);


    void onWxLoginRefuse();

    void onWxLoginFaile(int errorCode);

    void onWxLoginCancel();
}
