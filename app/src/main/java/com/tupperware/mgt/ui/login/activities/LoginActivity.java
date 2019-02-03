package com.tupperware.mgt.ui.login.activities;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dhunter.common.config.GlobalConfig;
import com.dhunter.common.utils.LeakUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tupperware.mgt.R;
import com.tupperware.mgt.base.BaseActivity;
import com.tupperware.mgt.entity.login.EmployeeInfo;
import com.tupperware.mgt.http.LoginDataManager;
import com.tupperware.mgt.ui.datawindow.activity.KpiTargetActivity;
import com.tupperware.mgt.ui.main.MainActivity;
import com.tupperware.mgt.ui.login.component.DaggerLoginActivityComponent;
import com.tupperware.mgt.ui.login.contract.LoginContract;
import com.tupperware.mgt.ui.login.module.LoginPresenterModule;
import com.tupperware.mgt.ui.login.presenter.LoginPresenter;
import com.tupperware.mgt.utils.ActivityManager;
import com.tupperware.mgt.utils.PersonalHeadUtil;
import com.tupperware.mgt.utils.ToastUtil;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dhunter on 2018/11/16.
 */

public class LoginActivity extends BaseActivity implements LoginContract.View{
    @BindView(R.id.logo)
    TextView mLogo;
    @BindView(R.id.personal_info)
    LinearLayout mPersonalInfo;
    @BindView(R.id.head_img)
    SimpleDraweeView mHeadImag;
    @BindView(R.id.name)
    TextView mNickName;
    @BindView(R.id.user_post)
    TextView mUerPost;
    @BindView(R.id.user_name)
    EditText mUserAccount;
    @BindView(R.id.password)
    EditText mPsw;
    @BindView(R.id.forget_psw)
    TextView mForgetPsw;
    @BindView(R.id.other_login)
    TextView mOtherLogin;
    @BindView(R.id.login)
    Button mLogin;
    @BindView(R.id.user_name_clear)
    ImageView mUserNameClear;
    @BindView(R.id.password_clear)
    ImageView mPwdClear;

    private String employeeId;
    private String pswString;

    @Inject
    LoginPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initLayout() {
        DaggerLoginActivityComponent.builder()
                .appComponent(getAppComponent())
                .loginPresenterModule(new LoginPresenterModule(this, LoginDataManager.getInstance(mDataManager)))
                .build()
                .inject(this);
        ActivityManager.getInstance().addActivity(this);
        addWatcherListener();
        String lastAccount = mDataManager.getSPData(GlobalConfig.LOGIN_ACCOUNT);
        if(lastAccount!= null && !lastAccount.isEmpty()) {
            mUserAccount.setText(lastAccount);
        }

    }

    @Override
    protected void requestData() {

    }

    @Override
    public void setLoginResult() {
//        if(checkisNeedResetPsw()) {
//            Intent intent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
//            startActivity(intent);
//        } else {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            ActivityManager.getInstance().exit();
//        }
    }

    @OnClick({R.id.forget_psw, R.id.other_login, R.id.login, R.id.user_name_clear, R.id.password_clear})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.forget_psw:
                jumptoActivity(this, ForgetPswActivity.class);
                break;
            case R.id.other_login:
                jumptoActivity(this, LoginSelectActivity.class);
                break;
            case R.id.login:
                tryLogin();
                break;
            case R.id.user_name_clear:
                mUserAccount.setText("");
                break;
            case R.id.password_clear:
                mPsw.setText("");
                break;
        }
    }

    private void addWatcherListener() {
        mUserAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkPersonalInfo(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                mPsw.setText("");
            }
        });
        mPsw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() > 0 ){
                    mPwdClear.setVisibility(View.VISIBLE);
                } else {
                    mPwdClear.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void tryLogin() {
        employeeId = mUserAccount.getText().toString().trim();
        pswString = mPsw.getText().toString().trim();
        if(employeeId.isEmpty()) {
            ToastUtil.showSelfS(getResources().getString(R.string.not_input_user_account));
        } else if(pswString.isEmpty()) {
            ToastUtil.showSelfS(getResources().getString(R.string.not_input_password));
        } else {
            showDialog();
            mPresenter.loginByAccount(employeeId, pswString,false);
        }
    }

    private void checkPersonalInfo(CharSequence s) {
        if(s.length() > 0 ){
            mUserNameClear.setVisibility(View.VISIBLE);
        } else {
            mUserNameClear.setVisibility(View.GONE);
        }
        if(s.length() >= 3) {
            mPresenter.getPersonalInfobyJobId(s.toString());
        } else {
            mLogo.setVisibility(View.VISIBLE);
            mPersonalInfo.setVisibility(View.GONE);
        }
    }

    @Override
    public void setHeaderHide() {
        if(mLogo != null && mLogo.getVisibility() == View.GONE) {
            mLogo.setVisibility(View.VISIBLE);
        }
        if(mPersonalInfo != null && mPersonalInfo.getVisibility() == View.VISIBLE) {
            mPersonalInfo.setVisibility(View.GONE);
        }
    }

    @Override
    public void refreshPersonalInfo(EmployeeInfo info) {
        if (mLogo != null && mLogo.getVisibility() == View.VISIBLE) {
            mLogo.setVisibility(View.GONE);
        }
        if (mPersonalInfo != null && mPersonalInfo.getVisibility() == View.GONE) {
            mPersonalInfo.setVisibility(View.VISIBLE);
        }
//        mHeadImag.setImageURI("");
        mNickName.setText(info.getpName());
        mUerPost.setText(PersonalHeadUtil.getPost(info));
    }

    private boolean checkisNeedResetPsw() {
        String lastAccount = mDataManager.getSPData(GlobalConfig.LOGIN_ACCOUNT);
        if(lastAccount != null && lastAccount.equals("111111")) {
            return true;
        } else {
            return false;
        }
    }

    private void jumptoActivity(Context context, Class<?> _cls) {
        Intent intent = new Intent();
        intent.setClass(context, _cls);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LeakUtils.fixInputMethodManagerLeak(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityManager.getInstance().removeActivity(this);
    }
}
