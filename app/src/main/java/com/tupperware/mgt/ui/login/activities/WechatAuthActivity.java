package com.tupperware.mgt.ui.login.activities;

import android.content.Intent;

import com.dhunter.system.eventCenter.Event;
import com.dhunter.system.eventCenter.EventConst;
import com.dhunter.system.eventCenter.EventManager;
import com.dhunter.system.eventCenter.IObserver;
import com.dhunter.system.eventCenter.ThreadMode;
import com.tupperware.mgt.R;
import com.tupperware.mgt.base.BaseActivity;
import com.tupperware.mgt.config.Constant;
import com.tupperware.mgt.entity.login.EmployeeInfo;
import com.tupperware.mgt.http.LoginDataManager;
import com.tupperware.mgt.ui.main.MainActivity;
import com.tupperware.mgt.ui.login.component.DaggerWechatAuthActivityComponent;
import com.tupperware.mgt.ui.login.contract.WechatAuthContract;
import com.tupperware.mgt.ui.login.module.WechatAuthPresenterModule;
import com.tupperware.mgt.ui.login.presenter.WechatAuthPresenter;
import com.tupperware.mgt.utils.ActivityManager;
import com.tupperware.mgt.utils.ToastUtil;
import com.tupperware.mgt.wxapi.WxLoginUtils;
import com.tupperware.mgt.wxapi.callback.WxLoginCallBack;

import javax.inject.Inject;

/**
 * Created by dhunter on 2018/11/29.
 */

public class WechatAuthActivity extends BaseActivity implements WechatAuthContract.View, IObserver.mainThread,WxLoginCallBack {

    private WxLoginUtils mWxLogin;
    @Inject
    WechatAuthPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wechat_auth;
    }

    @Override
    protected void initLayout() {
        DaggerWechatAuthActivityComponent.builder()
                .appComponent(getAppComponent())
                .wechatAuthPresenterModule(new WechatAuthPresenterModule(this, LoginDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        EventManager.getInstance().addObserver(this, EventConst.BindCAccount.EVENT_SOURCE_NAME, ThreadMode.MainThread,
                EventConst.BindCAccount.GET_LOGIN_WECHAT_SUCCESS,
                EventConst.BindCAccount.GET_LOGIN_WECHAT_USER_CANCEL,
                EventConst.BindCAccount.GET_LOGIN_WECHAT_USER_REFUSED);

    }

    @Override
    protected void requestData() {
        startWeChatLogin();
    }

    @Override
    public void setWeChatAuthSuccess(EmployeeInfo fullPersonalInfo) {
        Intent intent = new Intent(this, WechatBindTelActivity.class);
        intent.putExtra(Constant.WECHAT_INFO, fullPersonalInfo);
        startActivity(intent);
        finish();
    }

    @Override
    public void loginSuccess() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        ActivityManager.getInstance().exit();
        finish();
    }

    @Override
    public void setWeChatAuthFail() {
        finish();
    }

    private void startWeChatLogin() {
        mWxLogin = new WxLoginUtils(getApplicationContext());
        mWxLogin.wxLogin();
    }

    @Override
    public void onWxLoginSuccess(String code) {
        mPresenter.WeChatAuth(code);
    }

    @Override
    public void onWxLoginRefuse() {
        ToastUtil.showS("被拒绝微信授权");
        finish();
    }

    @Override
    public void onEventMainThread(Event event) {
        String sourceName  = event.source.getName();
        if(EventConst.BindCAccount.EVENT_SOURCE_NAME.equals(sourceName)){
            int what = event.what;
            if(what == EventConst.BindCAccount.GET_LOGIN_WECHAT_SUCCESS){
                Object[] params = (Object[])event.params;
                onWxLoginSuccess((String)params[0]);
            }else if(what == EventConst.BindCAccount.GET_LOGIN_WECHAT_USER_REFUSED){
                onWxLoginRefuse();
            }else if(what == EventConst.BindCAccount.GET_LOGIN_WECHAT_USER_CANCEL){
                onWxLoginCancel();
            }
        }
    }

    @Override
    public void onWxLoginFaile(int errorCode) {
        ToastUtil.showS("微信授权失败");
        finish();
    }

    @Override
    public void onWxLoginCancel() {
        ToastUtil.showS("取消微信授权");
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventManager.getInstance().removeOberser(this, EventConst.BindCAccount.EVENT_SOURCE_NAME);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
