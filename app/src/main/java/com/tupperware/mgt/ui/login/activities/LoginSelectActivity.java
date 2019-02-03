package com.tupperware.mgt.ui.login.activities;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.dhunter.common.config.GlobalConfig;
import com.tupperware.mgt.R;
import com.tupperware.mgt.base.BaseActivity;
import com.tupperware.mgt.config.Constant;
import com.tupperware.mgt.entity.login.EmployeeInfo;
import com.tupperware.mgt.http.LoginDataManager;
import com.tupperware.mgt.ui.login.component.DaggerLoginSelectActivityComponent;
import com.tupperware.mgt.ui.login.contract.LoginContract;
import com.tupperware.mgt.ui.login.module.LoginPresenterModule;
import com.tupperware.mgt.ui.login.presenter.LoginPresenter;
import com.tupperware.mgt.ui.main.MainActivity;
import com.tupperware.mgt.utils.ActivityManager;
import com.tupperware.mgt.utils.ObjectUtil;
import com.tupperware.mgt.view.GridMenu;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/11/16.
 */

public class LoginSelectActivity extends BaseActivity  implements LoginContract.View{

    @BindView(R.id.psw_login)
    GridMenu mPswGridMenus;
    @BindView(R.id.smsCode_login)
    GridMenu mCodeGridMenus;
    @BindView(R.id.weChat_login)
    GridMenu mWeCharGridMenus;

    private long mLastReqTime = 0L;

    @Inject
    LoginPresenter loginPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_select;
    }

    @Override
    protected void initLayout() {
        DaggerLoginSelectActivityComponent.builder()
                .appComponent(getAppComponent())
                .loginPresenterModule(new LoginPresenterModule(this, LoginDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);

        ActivityManager.getInstance().addActivity(this);
        mPswGridMenus.setAttr(R.mipmap.login_password_btn, getResources().getString(R.string.psw_login));
        mCodeGridMenus.setAttr(R.mipmap.login_code_btn, getResources().getString(R.string.sms_code_login));
        mWeCharGridMenus.setAttr(R.mipmap.login_wechat_btn, getResources().getString(R.string.wechat_login));
    }

    @Override
    protected void requestData() {
        String infoJson = mDataManager.getSPData(GlobalConfig.LOGIN_EMPLOYEE_INFO);
        EmployeeInfo info = ObjectUtil.fromJson(infoJson, EmployeeInfo.class);
        if (info != null){
            showDialog();
            loginPresenter.loginByAccount(info.getpUid(), info.getPasswd(),true);
        }
    }

    @OnClick({R.id.psw_login, R.id.smsCode_login, R.id.weChat_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.psw_login:
                jumptoActivity(this, LoginActivity.class);
                break;
            case R.id.smsCode_login:
                jumptoActivity(this, SmsCodeLoginActivity.class);
                break;
            case R.id.weChat_login:
                //防多次点击
                if(System.currentTimeMillis() - mLastReqTime > Constant.FAST_CLICK_DELAY_TIME) {
                    showDialog();
                    Intent intent = new Intent(LoginSelectActivity.this, WechatAuthActivity.class);
                    startActivityForResult(intent, Constant.REQUEST_CODE);
                    mLastReqTime = System.currentTimeMillis();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        hideDialog();
    }

    private void jumptoActivity(Context context, Class<?> _cls) {
        Intent intent = new Intent();
        intent.setClass(context, _cls);
        context.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityManager.getInstance().exit();
    }

    @Override
    public void setLoginResult() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        ActivityManager.getInstance().exit();
    }

    @Override
    public void refreshPersonalInfo(EmployeeInfo info) {

    }

    @Override
    public void setHeaderHide() {

    }
}
