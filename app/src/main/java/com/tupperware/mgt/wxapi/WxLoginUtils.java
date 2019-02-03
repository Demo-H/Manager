package com.tupperware.mgt.wxapi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tupperware.mgt.base.BaseApplication;
import com.tupperware.mgt.utils.ToastUtil;
import com.tupperware.mgt.wxapi.callback.WxLoginCallBack;

/**
 * Created by admin on 2018/9/22.
 * 在创建该类后如果是使用分享要先调用onWxShareCreate方法，
 * 如果使用微信登录要先调用onWxLoginCreate方法
 * 在使用完之后要在Activity的onDestroy方法中对应调用onDestroyWxShare或
 * onDestroyWxLogin方法
 */

public class WxLoginUtils {

    private Context mContext;
    private WxLoginCallBack mWxLoginCallBack;//微信登录回调
    private BroadcastReceiver mLoginReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            int callBackCode = intent.getIntExtra(WxUtils.WXWXENTRYACTIVITYRESPONECODE, -1);
            switch (callBackCode) {
                case BaseResp.ErrCode.ERR_OK:
                    mWxLoginCallBack.onWxLoginSuccess(intent.getStringExtra(WxUtils.WXENTRYACTIVITYLOGINCODE));
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED://用户拒绝授权
                    mWxLoginCallBack.onWxLoginRefuse();
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL://用户取消
                    mWxLoginCallBack.onWxLoginCancel();
                    break;
                default:
                    mWxLoginCallBack.onWxLoginFaile(callBackCode);
                    break;
            }
            onDestroyWxLogin();
        }
    };


    public WxLoginUtils(Context context) {
        mContext = context;
    }


    //微信登录
    public void onWxLoginCreate(WxLoginCallBack callBack) {
        mWxLoginCallBack = callBack;
        IntentFilter filter = new IntentFilter();
        filter.addAction(WxUtils.WXENTRYACTIVITYLOGIN);
        mContext.registerReceiver(mLoginReceiver, filter);

    }


    //注销登录广播，在Activity的onDestroy里面调用
    public void onDestroyWxLogin() {
        mContext.unregisterReceiver(mLoginReceiver);

    }

    /*
    * 微信登录
    * */
    public void wxLogin() {
        if (!BaseApplication.mWxApi.isWXAppInstalled()) {
            ToastUtil.showS("您还未安装微信");
            return;
        }
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "manager_wx_login";
        BaseApplication.mWxApi.sendReq(req);
    }

}
