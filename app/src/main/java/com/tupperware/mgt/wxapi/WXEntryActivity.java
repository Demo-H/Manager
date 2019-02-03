package com.tupperware.mgt.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.dhunter.system.eventCenter.EventConst;
import com.dhunter.system.eventCenter.EventManager;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tupperware.mgt.base.BaseApplication;


public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
//    private IWXAPI mApi;
    public int WX_LOGIN = 1;
    private Intent mIntent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseApplication.mWxApi.handleIntent(getIntent(), this);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        BaseApplication.mWxApi.handleIntent(intent, this);
    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {

    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    @Override
    public void onResp(BaseResp resp) {
        mIntent=new Intent();
        if (resp.getType() == WX_LOGIN) {
            //登录回调
            mIntent.setAction(WxUtils.WXENTRYACTIVITYLOGIN);
            mIntent.putExtra(WxUtils.WXWXENTRYACTIVITYRESPONECODE,resp.errCode);
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    SendAuth.Resp sendResp = (SendAuth.Resp) resp;
                    if (sendResp != null) {
                        String code = sendResp.code;
                        mIntent.putExtra(WxUtils.WXENTRYACTIVITYLOGINCODE,code);
                        EventManager.getInstance().postEvent(EventConst.BindCAccount.EVENT_SOURCE_NAME,EventConst.BindCAccount.GET_LOGIN_WECHAT_SUCCESS,code);
                        finish();
                        return;
                    }
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED://用户拒绝授权
                    EventManager.getInstance().postEvent(EventConst.BindCAccount.EVENT_SOURCE_NAME,EventConst.BindCAccount.GET_LOGIN_WECHAT_USER_REFUSED);
                    finish();
                    return;
                    //Toast.makeText(WXEntryActivity.this, "用户拒绝授权", Toast.LENGTH_LONG).show();
//                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL://用户取消
                   // Toast.makeText(WXEntryActivity.this, "用户取消登录", Toast.LENGTH_LONG).show();
                    EventManager.getInstance().postEvent(EventConst.BindCAccount.EVENT_SOURCE_NAME,EventConst.BindCAccount.GET_LOGIN_WECHAT_USER_CANCEL);
                    finish();
                    return;
//                    break;
                default:
                    break;
            }
        }
        sendBroadcast(mIntent);
        finish();
    }

}